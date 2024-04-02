package blocksworld.datamining;

import java.util.Set;

import blocksworld.modelling.BooleanVariable;


/**
 * La classe Itemset représente un ensemble d'items avec une fréquence associée.
 * Elle est utilisée pour stocker des informations sur les itemsets, y compris les items
 * qui le composent et leur fréquence.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class Itemset {

    /** Variables */
    protected Set<BooleanVariable> items;
    protected float frequency;
    
    /** Constructeur */
    /**
     * Construit un Itemset avec l’ensemble d’items spécifié et une fréquence associée.
     *
     * @param items L’ensemble d’items qui composent l'itemset.
     * @param frequency La fréquence associée à l'itemset.
     */
    public Itemset(Set<BooleanVariable> items, float frequency) {
        this.items = items;
        this.frequency = frequency;
    }

    /** Accesseurs */
    /**
     * Obtient l’ensemble d’items qui composent l'itemset.
     *
     * @return L’ensemble d’items de l'itemset.
     */
    public Set<BooleanVariable> getItems() {
        return this.items;
    }

    /**
     * Obtient la fréquence associée à l'itemset.
     *
     * @return La fréquence de l'itemset.
     */
    public float getFrequency() {
        return this.frequency;
    }

    /** Méthode toString */
    /**
     * Retourne une représentation textuelle de l'itemset.
     *
     * @return Une chaîne de caractères représentant l'itemset.
     */
    @Override
    public String toString() {
        String toShow = "Itemset [items : [ ";
        for (BooleanVariable booleanVariable : items) {
            toShow += booleanVariable.getName() + " ";
        }
        toShow += "], frequency : " + Float.toString(frequency) + " ]";
        return toShow;
    }
    
}
