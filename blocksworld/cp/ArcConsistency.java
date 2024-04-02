package blocksworld.cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

/**
 * La classe <br>ArcConsistency</br> met à disposition des méthodes qui peuvent enlever des valeurs de domain qui sont inutiles
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class ArcConsistency {
    
    /** Variable */
    protected Set<Constraint> constraints;

    /** Constructeur */
    /**
     * Le contructeur <br>ArcConsistency(Set<Constraint>)</br> prend en parametre toutes les contraintes(unaire ou binaire)
     * @param Set<Constraint> constraints, l'ensemble des contraintes.
     * @throws IllegalArgumentException si les contraintes ne sont pas toutes unaires ou binaires.
     */
    public ArcConsistency(Set<Constraint> constraints) {
        this.constraints = constraints;
        for (Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() != 1 && scope.size() != 2) {
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }
        }
    }

    /**
     * La méthode enforceNodeConsistency(Map<Variable, Set<Object>>) supprime les valeurs v des domaines pour lesquelles il existe 
     * une contrainte unaire non satisfaite par v et retourne False si au moins un domaine a été vidé.
     * @param Map<Variable,Set<Object>> inst, correspond à un dictionnaire de Variable vers leur Domaine
     * @return boolean, retourne True si aucun domaine a été vidé.
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> inst) {
        //Permettra de savoir si un domaine a été vidé
        boolean domaines_valide = true; 
        
        for (Map.Entry<Variable, Set<Object>> entry : inst.entrySet()) {

            //Pour chaque entrée de l'instance, on recupere leurs clés et leurs valeurs.
            Variable variable = entry.getKey();
            Set<Object> domaine = entry.getValue();

            //On crée un ensemble où l'on mettra toutes les valeurs qui ne peuvent pas être utilisées
            Set<Object> valNonViable = new HashSet<Object>();

            for (Object val : domaine) {
                
                //On crée une instance temporaire à laquelle on teste toutes les contraintes
                Map<Variable, Object> tmpInst = new HashMap<Variable,Object>();
                tmpInst.put(variable, val);

                for (Constraint constraint : this.constraints) {

                    //On vérifie si l'instance est satisfait par la contrainte
                    if (constraint.getScope().size()==1 && constraint.getScope().contains(variable) && !constraint.isSatisfiedBy(tmpInst)) {
                        //Si une contrainte ne passe pas alors on dit que la valeur n'est pas viable et ne peut pas être utilisée
                        valNonViable.add(val);
                    }
                }
            }
            domaine.removeAll(valNonViable);
            //Si le domaine n'a plus de valeur (ou plutot vide), il n'est donc plus viable et valide.
            if (domaine.isEmpty()) {
                domaines_valide = false;
            }
        }
        return domaines_valide;
    }

    /**
     * La methode revise(Variable, Set<Object>, Variable, Set<Object>) 
     * supprime toutes les valeurs var1 de dom1 pour lesquels il n’existe 
     * aucune valeur var2 de dom2 supportant var1 pour toutes les contraintes portant 
     * sur var1 et var2. La méthode retourne True si au moins une valeur a été supprimée
     * de dom1.
     * @param Variable var1 
     * @param Set<Object> dom1 
     * @param Variable var2
     * @param Set<Object> dom2 
     * @return boolean, True si au moins une valeur a été supprimée
     * de dom1.
     */
    public boolean revise(Variable var1, Set<Object> dom1, Variable var2, Set<Object> dom2) {
        boolean del = false;
        for (Object val1 : new HashSet<Object>(dom1)) {
            boolean viable = false;
            for (Object val2 : dom2) {
                boolean toutSatisfait = true;
                for (Constraint constraint : this.constraints) {
                    Map<Variable, Object> tmpInst = new HashMap<Variable,Object>();
                    tmpInst.put(var1,val1);
                    tmpInst.put(var2,val2);
                    if (constraint.getScope().contains(var1) && constraint.getScope().contains(var2) && !constraint.isSatisfiedBy(tmpInst)) {
                        toutSatisfait = false;
                        break;
                    }
                }
                if (toutSatisfait) {
                    viable = true;
                    break;
                }
            }
            if (!viable) {
                dom1.remove(val1);
                del = true;
            }
        }
        return del;
    }


    /*
     * Si la liste de valeurs de l'argument est vide, on renvoie False.
     * 
     * On créer une variable change definie à True.
     * On rentre dans la boucle while ou on redefinie cette variable a False.
     * Cependant, si les deux conditions sont respectées, change est redefinie
     * à True et la boucle while est relancée.
     * 
     * On créer deux sets de clés pour l'ensemble de domaines.
     * 
     * La premier condition consiste a verifier que les clés de l'ensemble de domaines
     * sont bien différentes. 
     * La deuxieme condition (si la premier est respectée) est un appel de la 
     * méthode 'revise' sur les clés et les positions dans l'ensemble
     * 
     * Lorsqu'on sort de la boucle while, on regarde si les clés de l'ensemble 
     * de domaines sont vides. Si c'est le cas on renvoie False. True sinon.
     */

    /**
     * La méthode ac1 filtre tous les domaines en place en utilisant ac1,
     * jusqu’à stabilité.
     * @param Map<Variable,Set<Object>> eD, correspond à un dictionnaire de Variable vers leur Domaine
     * @return boolean, True si aucun domain a été vidé.
     */
    public boolean ac1(Map<Variable, Set<Object>> eD) {
        if (!this.enforceNodeConsistency(eD)) {
            return false;
        }
        boolean change = true;
        while(change) {
            change = false;
            for (Variable var1 : eD.keySet()) {
                for (Variable var2 : eD.keySet()) {
                    if (var1 != var2) {
                       if (this.revise(var1, eD.get(var1), var2, eD.get(var2))) {
                            change = true;
                        } 
                    }
                }
            }

        }

        for (Variable var : eD.keySet()) {
            if (eD.get(var).isEmpty()) {
                return false;
            }
        }

        return true;
    }

}
