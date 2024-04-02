package blocksworld.generate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Implication;
import blocksworld.modelling.Variable;

/**
 * La classe BWPasGen génère des contraintes d'implication pour le problème Blocksworld,
 * où un block ne peut pas être directement placé sur un autre block.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BWPasGen implements BWConstraintGen {

    private int nbBlock, nbPile;

    private BWConstraintGen bwConstGene;

    /**
     * Constructeur de la classe BWPasGen.
     * 
     * @param nbBlock      Le nombre de blocks.
     * @param nbPile       Le nombre de piles.
     * @param bwConstGene  L'objet de génération de contraintes à inclure, peut être nul.
     */
    public BWPasGen(int nbBlock, int nbPile, BWConstraintGen bwConstGene) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;
        this.bwConstGene = bwConstGene;
    }

    /**
     * Constructeur de la classe BWPasGen.
     * 
     * @param nbBlock  Le nombre de blocks.
     * @param nbPile   Le nombre de piles.
     */
    public BWPasGen(int nbBlock, int nbPile) {
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
                    Set<Object> domain1 = new HashSet<>(Set.of(j));
                    Set<Object> domain2 = new HashSet<>();
                    for (int k = this.nbPile * -1; k < 0; k++) {
                        domain2.add(k);
                    }
                    domain2.add(j - (i - j));
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

        for (int j = 0; j < nbBlock; j++) {
            // On passe tous les blocks pour voir s'il y a un block qui est sous un autre
            if (symbioMap.containsKey(j)) {
                // Si oui, alors on crée la contrainte
                int i = symbioMap.get(j);
                int block1 = i;
                int block2 = j;
                Set<Object> domain1 = new HashSet<>(Set.of(j));
                Set<Object> domain2 = new HashSet<>();
                for (int k = this.nbPile * -1; k < 0; k++) {
                    domain2.add(k);
                }
                domain2.add(j - (i - j));
                constraints.add(new Implication(block1, domain1, block2, domain2));
            }
        }

        return constraints;
    }
}
