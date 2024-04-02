package blocksworld.modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Cette classe représente une contrainte qui vérifie si le OnB d'un block est égal à la pile
 * et si le FreeP de la pile est égal à false.
 * 
 * Elle permet de définir les variables nécessaires et de vérifier si une instantiation donnée satisfait la contrainte.
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class PileFreeConstraint implements Constraint {

    /** Variable */
    private int block;
    private int pile;

    /** Constructor */
    public PileFreeConstraint(int block, int pile) {
        this.block = block;
        this.pile = pile;
    }

    /**
     * Cette méthode renvoie l'ensemble des variables nécessaires à la contrainte.
     *
     * @return Set<Variable> L'ensemble des variables nécessaires.
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(new OnB(Integer.toString(this.block)));
        scope.add(new FreeP(Integer.toString(this.pile)));
        return scope;
    }

    /**
     * Cette méthode vérifie si l'instantiation donnée satisfait la contrainte.
     *
     * @param instantiation Map<Variable, Object> L'instantiation à vérifier.
     * @return boolean true si la condition de la classe est respectée, false sinon.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
        OnB onB = new OnB(Integer.toString(this.block));

        //On vérifie que l'instance contient le onB
        if (!instantiation.containsKey(onB)) {
            return false;
        }

        //On vérifie qu'on regarde la bonne valeur (et revoie vrai si ce n'est pas la même, car ça finirait le programme)
        Integer destOnB = (Integer) instantiation.get(onB);
        if (destOnB != this.pile) {
            return true;   
        }

        //On vérifie que l'instance contient le freeP puis on vérifie que la valeur pour FreePile soit un booléen et que cette valeur soit celle voulue
        FreeP freePile = new FreeP(Integer.toString(this.pile));
        if (!instantiation.containsKey(freePile)) {
            return false;
        }
        if (!(instantiation.get(freePile) instanceof Boolean) || (Boolean) instantiation.get(freePile)) {
            return false;
        }

        return true;
    }

    /**
     * Cette méthode renvoie une représentation textuelle de la contrainte.
     *
     * @return String La représentation textuelle de la contrainte.
     */
    @Override
    public String toString() {
        return "PileFreeConstraint [block:" + this.block + ", pile: " + this.pile + "]";
    }
    


}
