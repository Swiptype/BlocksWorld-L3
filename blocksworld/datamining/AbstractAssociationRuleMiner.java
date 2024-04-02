package blocksworld.datamining;

import java.util.HashSet;
import java.util.Set;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe abstraite AbstractAssociationRuleMiner implémente l’interface AssociationRuleMiner. 
 * Le constructeur prend en argument une base de données et définit l’accesseur à cette base.
 * Il fournit des fonctions qui peuvent aider au minage d'Association Rule.
 */
public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    
    /** Variable */
    protected BooleanDatabase database;

    /** Constructeur */
    /**
     * Construit une classe AbstractAssociationRuleMiner avec une base de données de BooleanVariable
     * @param database une base de données de BooleanVariable utilisé pour les différentes fonctions
     */
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    /** Accesseur */
    /**
     * Récupère le base de données de cette instance
     * @return BooleanDatabase
     */
    public BooleanDatabase getDatabase() {
        return this.database;
    }

    /**
     * Calcule la frequence de chaque item dans l'ensemble items depuis les informations de frequentItemsets.
     * @param items l'ensemble des items dont on calcule la fréquence
     * @param frequentItemsets l'ensemble des itemsets frequents
     * @return la fréquence des items donnés
     * @throw IllegalArgumentException si un item dans items n'est pas dans frequentItemsets.
     */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> frequentItemsets) {
        for (Itemset itemset : frequentItemsets) {
            if (itemset.getItems().containsAll(items) && items.containsAll(itemset.getItems())) {
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("L'ensemble d'items n'apparaît pas dans les itemsets fréquents.");
    }

    /**
     * Calcule le taux de confiance de l'Association Rule
     * @param premise           La premise de l'Association Rule
     * @param conclusion        La conclusion de l'Association Rule
     * @param frequentItemsets  L'ensemble des frequent itemset
     * @return la confiance de l'Association Rule
     */
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> frequentItemsets) {
        Set<BooleanVariable> combined = new HashSet<>(premise);
        combined.addAll(conclusion);

        float conclusionFrequency = AbstractAssociationRuleMiner.frequency(conclusion, frequentItemsets);

        if (premise.isEmpty()) {
            return conclusionFrequency;
        }
        if (conclusion.isEmpty()) {
            return 1f;
        }

        float premiseFrequency = AbstractAssociationRuleMiner.frequency(premise, frequentItemsets);
        float combinedFrequency = AbstractAssociationRuleMiner.frequency(combined, frequentItemsets);

        if (premiseFrequency > 0) {
            return combinedFrequency / premiseFrequency;
        } else {
            return 0.0f;
        }
    }
}