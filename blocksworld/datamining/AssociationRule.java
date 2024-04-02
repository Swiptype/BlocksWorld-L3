package blocksworld.datamining;

import java.util.Set;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe AssociationRule représente une règle d'association.
 * Elle est utilisée pour stocker des informations sur une règle spécifique, y compris la prémisse,
 * la conclusion, la fréquence et la confiance associées.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */

public class AssociationRule {

    /** Variables */
    private Set<BooleanVariable> premise; 
    private Set<BooleanVariable> conclusion; 
    private float frequency; 
    private float confidence; 

    /** Constructeur */
    /**
     * Construit une AssociationRule avec la prémisse, la conclusion, la fréquence et la confiance.
     *
     * @param premise La prémisse de la règle d'association.
     * @param conclusion La conclusion de la règle d'association.
     * @param frequency La fréquence de la règle d'association.
     * @param confidence La confiance de la règle d'association.
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    /** Accesseurs */
    /**
     * Obtient la prémisse de la règle d'association.
     *
     * @return La prémisse de la règle d'association.
     */
    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    /**
     * Obtient la conclusion de la règle d'association.
     *
     * @return La conclusion de la règle d'association.
     */
    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    /**
     * Obtient la fréquence de la règle d'association.
     *
     * @return La fréquence de la règle d'association.
     */
    public float getFrequency() {
        return frequency;
    }

    /**
     * Obtient la confiance de la règle d'association.
     *
     * @return La confiance de la règle d'association.
     */
    public float getConfidence() {
        return confidence;
    }

    /** Méthode toString */
    /**
     * Retourne une représentation textuelle de la règle d'association.
     *
     * @return Une chaîne de caractères représentant la règle d'association.
     */
    @Override
    public String toString() {
        String toShow = "AssociationRule [premise : [ ";
        for (BooleanVariable booleanVariable : this.premise) {
            toShow += booleanVariable.getName() + " ";
        }
        toShow += "], conclusion : [ ";
        for (BooleanVariable booleanVariable : this.conclusion) {
            toShow += booleanVariable.getName() + " ";
        }

        toShow += 
            "], frequency : " + Float.toString(frequency)
            +", confidence: " + Float.toString(confidence) + " ]";
        return toShow;
    }

}