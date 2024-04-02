package blocksworld.cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

/**
 * La classe <br>BacktrackSolver</br> permet de recréer des instances à partir d'un ensemble de contraintes et de variables
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BacktrackSolver extends AbstractSolver {
    
    /** Constructeur */
    /**
     * Le constructeur <br>BacktrackSolver</br> prend l'ensemble de variables et l'ensemble de contraintes impliqués
     * @param Set<Variable> variables
     * @param Set<Constraint> constraints
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * La méthode solve() permet de recréer des instances à partir d'un ensemble de contraintes et de variables
     * à l'aide d'autre classe.
     */
    public Map<Variable, Object> solve() {
        //L'instance tempon que l'on remplit
        Map<Variable, Object> inst = new HashMap<Variable, Object>();
        //varNoInst nous permet de parcourir les variables dans un certain ordre
        LinkedList<Variable> varNoInst = new LinkedList<Variable>(this.variables);
        //Cette instance est l'instance qui a pu être recréée à partir descontraintes et des variables 
        Map<Variable, Object> newInst = this.solveEX(inst, varNoInst);
        return newInst;

    }
    
    /**
     * La méthode solveEX(Map<Variable, Object>, LinkedList<Variable>) n'est qu'une méthode annexe qui permet de faire la méthode solve().
     * @param Map<Variable,Object> partInst
     * @param LinkedList<Variable> varNoInst
     * @return Map<Variable, Object> est l'instance qui a été créée.
     */
    public Map<Variable, Object> solveEX(Map<Variable, Object> partInst, LinkedList<Variable> varNoInst) { 
        //Si il n'y a plus de variables à parcourir, il ne reste plus qu'à retourner l'instance créée. 
        if (varNoInst.isEmpty()) {
            return partInst;
        }
        //On récupére une instance à parcourir
        Variable var = varNoInst.getFirst();
        varNoInst.removeFirst();

        //On parcours toutes les valeurs du domaine pour voir si une est viable
        for (Object obj : var.getDomain()) {

            //On le vérifie dans une nouvelle instance
            Map<Variable, Object> newInst = new HashMap<Variable, Object>(partInst);
            newInst.put(var,obj);

            //Si oui, il faut continuer à créer l'instance avec une autre variable
            if (this.isConsistent(newInst)) {
                Map<Variable, Object> newInst2 = this.solveEX(newInst, varNoInst);

                //Si on récupère une instance, on la renvoie
                if (newInst2 != null) {
                    return newInst2;
                }
            }    
        }
        varNoInst.add(var);
        return null;
    }

}
