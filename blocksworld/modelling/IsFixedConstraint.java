package blocksworld.modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Cette classe représente une contrainte qui vérifie si les conditions suivantes sont satisfaites :
 * - Le onB du block1 est égal à block2.
 * - Le fixedB de block2 est true.
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class IsFixedConstraint implements Constraint{

    /** Variable */
    private int block1;
    private int block2;

    /** Constructor */
    public IsFixedConstraint(int block1, int block2) {
        this.block1 = block1;
        this.block2 = block2;
    }

    /**
     * Renvoie l'ensemble des variables nécessaires à la contrainte.
     *
     * @return Un ensemble de variables.
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(new OnB(Integer.toString(this.block1)));
        scope.add(new FixedB(Integer.toString(this.block2)));
        return scope;
    }

    /**
     * Vérifie si la contrainte est satisfaite par une instanciation donnée.
     *
     * @param instantiation Une instanciation de variables.
     * @return true si la contrainte est satisfaite, false sinon.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
        
        //On vérifie que l'instance contient tous les variables nécessaires
        if (!instantiation.keySet().containsAll(this.getScope())) {
            return false;
        }

        OnB onB = new OnB(Integer.toString(this.block1));

        //On vérifie que la valeur de onB dans l'instance soit un Integer
        if (!(instantiation.get(onB) instanceof Integer)) {
            return false;
        }

        Integer numberOnB = (Integer) instantiation.get(onB);
        
        //On vérifie si la valeur de onB dans l'instance soit la valeur attendue
        if (numberOnB != this.block2) {
            return false;
        }

        FixedB fixedB2 = new FixedB(Integer.toString(this.block2));
        //On vérifie que la valeur de fixedB2 soit la valeur attendue
        if (!(instantiation.get(fixedB2) instanceof Boolean) || !((Boolean) instantiation.get(fixedB2))) {
            return false;
        }
        
        return true;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la contrainte IsFixedConstraint.
     *
     * @return Une chaîne de caractères représentant la contrainte.
     */
    @Override
    public String toString() {
        return "IsFixedConstraint [block1:"+ this.block1 +", block2: " + this.block2 + "]";
    }
    
}
