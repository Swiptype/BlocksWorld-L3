package blocksworld.modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Cette classe permet de dire si les conditions sont satisfaites.
 * Si deux blocks ont des onB differents
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class DifferentOnBConstraint implements Constraint {

    /** Variables */
    private int block1;
    private int block2;

    /** Constructor */
    public DifferentOnBConstraint(int block1, int block2) {
        this.block1 = block1;
        this.block2 = block2;
    }
    
    /**
     * Obtient l'ensemble des variables nécessaires à la contrainte.
     *
     * @return L'ensemble des variables nécessaires à la contrainte.
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(new OnB(Integer.toString(this.block1)));
        scope.add(new OnB(Integer.toString(this.block2)));
        return scope;
    }

    /**
     * Vérifie si la condition de la classe est respectée.
     *
     * @param instantiation L'instantiation à vérifier.
     * @return true si la condition est respectée, false sinon.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
        OnB onB1 = new OnB(Integer.toString(this.block1));
        OnB onB2 = new OnB(Integer.toString(this.block2));

        //On vérifie si l'instance contient les onB.s nécessaires.
        if (!(instantiation.containsKey(onB1) && instantiation.containsKey(onB2))) {
            return false;
        }

        //on vérifie que les deux onB.s soient différents
        if (instantiation.get(onB1).equals(instantiation.get(onB2))) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères de la contrainte.
     *
     * @return Une chaîne de caractères représentant la contrainte.
     */
    @Override
    public String toString() {
        return "DifferentOnBConstraint [block: " + this.block1 + ", block2: " + this.block2 + "]";
    }

}
