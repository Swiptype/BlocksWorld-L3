package blocksworld.modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Cette classe permet de dire si les conditions sont satisfaites.
 * Si le onB du block1 a un impacte sur le onB du block2
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class Implication implements Constraint {

    /** Variable */
    private int block1;
    private int block2;
    private Set<Object> domain1;
    private Set<Object> domain2;
    private OnB OnB1;
    private OnB OnB2;

    /** Constructeur */
    public Implication(int block1, Set<Object> domain1, int block2, Set<Object> domain2) {
        this.block1 = block1;
        this.domain1 = domain1;

        this.block2 = block2;
        this.domain2 = domain2;

        this.OnB1 = new OnB(Integer.toString(this.block1), this.domain1);
        this.OnB2 = new OnB(Integer.toString(this.block2), this.domain2);
    }

    /**
     * Renvoie l'ensemble des variables nécessaires à la contrainte.
     *
     * @return Un ensemble de variables.
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(this.OnB1);
        scope.add(this.OnB2);
        return scope;
    }

    /**
     * Vérifie si la contrainte d'implication est satisfaite par une instanciation donnée.
     *
     * @param instantiation Une instanciation de variables.
     * @return true si la contrainte est satisfaite, false sinon.
     * @throws IllegalArgumentException Si l'instanciation ne donne pas une valeur à chaque variable du scope.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
        if (instantiation.containsKey(this.OnB1) && instantiation.containsKey(this.OnB2)) {
            Object valOnBlock = instantiation.get(this.OnB1);
            Object valOnBlockPrime = instantiation.get(this.OnB2);
            if(domain1.contains(valOnBlock)) {
                return domain2.contains(valOnBlockPrime);
            } else {
                return true;
            }
            
        } else {
            throw new IllegalArgumentException("Une affectation qui ne donne pas une valeur à au moins chaque variable du scope de la contrainte");
        }
    }
    
    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la contrainte d'implication.
     *
     * @return Une chaîne de caractères représentant la contrainte d'implication.
     */
    @Override
    public String toString() {
        return "Implication [block1:" + block1 + ", his domain:"+ domain1 +", block2:" + block2 + ", his domain:" + domain2 + "]";
    }

}
