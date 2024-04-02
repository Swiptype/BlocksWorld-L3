package blocksworld.generate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Implication;
import blocksworld.modelling.Variable;

/**
 * La classe BWOnBInfGen génère des contraintes d'implication pour le problème Blocksworld,
 * où un block doit être sous un autre block.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BWOnBInfGen implements BWConstraintGen {

    private int nbBlock, nbPile;

    private BWConstraintGen bwConstGene;

    /**
     * Constructeur de la classe BWOnBInfGen.
     * 
     * @param nbBlock      Le nombre de blocks.
     * @param nbPile       Le nombre de piles.
     * @param bwConstGene  L'objet de génération de contraintes à inclure, peut être nul.
     */
    public BWOnBInfGen(int nbBlock, int nbPile, BWConstraintGen bwConstGene) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;
        this.bwConstGene = bwConstGene;
    }

    /**
     * Constructeur de la classe BWOnBInfGen.
     * 
     * @param nbBlock  Le nombre de blocks.
     * @param nbPile   Le nombre de piles.
     */
    public BWOnBInfGen(int nbBlock, int nbPile) {
        this(nbBlock, nbPile, null);
    }

    /**
     * Obtient l'ensemble des contraintes d'implication générées.
     * 
     * @return L'ensemble des contraintes d'implication générées.
     */
    public Set<Constraint> getConstraints() {
        Set<Constraint> constraints = new HashSet<>();

        if (this.bwConstGene != null) {
            constraints.addAll(bwConstGene.getConstraints());
        }

        for (int i = 0; i < this.nbBlock; i++) {
            for (int j = 0; j < this.nbBlock; j++) {
                if (i != j) {
                    int block1 = i;
                    int block2 = j;
                    Set<Object> domain1 = new HashSet<>(Set.of(i));
                    Set<Object> domain2 = new HashSet<>();
                    for (int k = this.nbPile * -1; k < 0; k++) {
                        domain2.add(k);
                    }
                    for (int k = 0; k < j; k++) {
                        domain2.add(k);
                    }
                    constraints.add(new Implication(block1, domain1, block2, domain2));
                }
            }
        }

        return constraints;
    }

    /**
     * Obtient l'ensemble des contraintes d'implication générées pour une instance spécifique.
     * 
     * @param inst L'instance spécifique pour laquelle générer les contraintes.
     * @return L'ensemble des contraintes d'implication générées pour l'instance spécifique.
     */
    public Set<Constraint> getConstraintsForInstance(Map<Variable, Object> inst) {
        Set<Constraint> constraints = new HashSet<>();

        if (this.bwConstGene != null) {
            constraints.addAll(this.bwConstGene.getConstraintsForInstance(inst));
        }

        Map<Integer, Integer> symbioMap = InstanceTools.getSymbioMap(inst);

        for (int i = 0; i < nbBlock; i++) {
            // On passe tous les blocks pour voir s'il y a un block qui est sous un autre
            if (symbioMap.containsKey(i)) {
                // Si oui, alors on crée la contrainte
                int block1 = symbioMap.get(i);
                int block2 = i;
                Set<Object> domain1 = new HashSet<>(Set.of(i));
                Set<Object> domain2 = new HashSet<>();
                for (int k = this.nbPile * -1; k < 0; k++) {
                    domain2.add(k);
                }
                for (int k = 0; k < i; k++) {
                    domain2.add(k);
                }
                constraints.add(new Implication(block1, domain1, block2, domain2));
            }
        }

        return constraints;
    }
}
