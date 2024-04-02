package blocksworld.generate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.DifferentOnBConstraint;
import blocksworld.modelling.IsFixedConstraint;
import blocksworld.modelling.PileFreeConstraint;
import blocksworld.modelling.Variable;

/**
 * La classe BWBasicConstraintGenerate génère des contraintes de base pour le problème Blocksworld.
 * Elle crée des ensembles de contraintes représentant des différences entre les blocs, des contraintes de fixation, et des contraintes de pile libre.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BWBasicConstraintGenerate implements BWConstraintGen{
    
    /**Variable */
    private int nbBlock; 
    private int nbPile;
    private BWConstraintGen bwConstGene;

    /** Constructeur */
    /**
     * Constructeur de la classe BWBasicConstraintGenerate.
     * 
     * @param nbBlock     Le nombre de blocs dans le problème.
     * @param nbPile      Le nombre de piles dans le problème.
     * @param bwConstGene Un générateur de contraintes additionnelles (peut être null).
     */
    public BWBasicConstraintGenerate(int nbBlock, int nbPile, BWConstraintGen bwConstGene) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;
        this.bwConstGene = bwConstGene;
    }

    /**
     * Constructeur de la classe BWBasicConstraintGenerate.
     * 
     * @param nbBlock Le nombre de blocs dans le problème.
     * @param nbPile  Le nombre de piles dans le problème.
     */
    public BWBasicConstraintGenerate(int nbBlock, int nbPile) {
        this(nbBlock, nbPile, null);
    }

    /**
     * Génère un ensemble de contraintes représentant des différences entre les blocs, des contraintes de fixation, et des contraintes de pile libre.
     * 
     * @return Un ensemble de contraintes générées.
     */
    public Set<Constraint> getConstraints() {
        Set<Constraint> constraints = new HashSet<>();

        if (this.bwConstGene != null) {
            constraints.addAll(bwConstGene.getConstraints());
        }

        for (int i = 0; i < this.nbBlock; i++) {
            for (int j = 0; j < this.nbBlock; j++) {
                if (i != j) {
                    constraints.add(new DifferentOnBConstraint(i, j));
                    constraints.add(new IsFixedConstraint(i, j));
                }
            }
            for (int j = this.nbPile*-1+1; j < 0; j++) {
                constraints.add(new PileFreeConstraint(i, j));
            }
        }
        return constraints;
    }

    /**
     * Génère un ensemble de contraintes pour une instance donnée représentant des différences entre les blocs, des contraintes de fixation, et des contraintes de pile libre.
     * 
     * @param inst L'instance spécifique pour laquelle générer les contraintes.
     * @return Un ensemble de contraintes générées pour l'instance donnée.
     */
    public Set<Constraint> getConstraintsForInstance(Map<Variable,Object> inst) {
        Set<Constraint> constraints = new HashSet<>();

        if (this.bwConstGene != null) {
            constraints.addAll(this.bwConstGene.getConstraintsForInstance(inst));
        }

        Map<Integer,Integer> symbioMap = InstanceTools.getSymbioMap(inst);

        for (int i = 0; i < nbBlock; i++) {
            for (int j = 0; j < nbBlock; j++) {
                if (i != j) {
                    constraints.add(new DifferentOnBConstraint(i, j));
                }
            }
            if (symbioMap.containsKey(i)) {
                constraints.add(new IsFixedConstraint(symbioMap.get(i), i));
            }
        }

        for (int j = nbPile*-1+1; j < 0; j++) {
            if (symbioMap.containsKey(j)) {
                constraints.add(new PileFreeConstraint(symbioMap.get(j), j));
            }
        }


        return constraints;
    }

}
