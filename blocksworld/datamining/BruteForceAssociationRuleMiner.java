package blocksworld.datamining;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe BruteForceAssociationRuleMiner hérite de la classe AbstractAssociationRuleMiner.
 * Elle est utilisée pour extraire des règles d'association en utilisant une approche de force brute.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    /**
     * Construit un BruteForceAssociationRuleMiner avec la base de données spécifiée.
     *
     * @param database La base de données sur laquelle appliquer l'algorithme de force brute.
     */
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }

    /** 
     * Méthode pour obtenir tous les candidats de prémisse.
     *
     * @param items L'ensemble d'items à partir duquel générer les candidats de prémisse.
     * @return Un ensemble de tous les candidats de prémisse possibles.
     */
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> candidatePremises = new HashSet<>();

        //Avoir une liste me permet d'eviter de reparcourir des Set<BooleanVariable>, ce qui était très couteux, je parcours mtn tout une seule fois.
        List<Set<BooleanVariable>> currentEns = new ArrayList<>(Set.of(items)); 

        //Utiliser un sort permet de modifier la taille de currentEns facilement sans probleme de Concurrent 
        for (int i = 0; i < currentEns.size(); i++) {
            Set<BooleanVariable> setItem = currentEns.get(i);

            for (BooleanVariable item : items) {
                Set<BooleanVariable> candidate = new HashSet<>(setItem);
                candidate.remove(item);
                
                if (!candidate.isEmpty() && !candidatePremises.contains(candidate)) {
                    currentEns.add(candidate);
                    candidatePremises.add(candidate);
                }
            }
        }

        return candidatePremises;
    }


    /** 
     * Implémentation de la méthode extract.
     *
     * @param minFrequency La fréquence minimale des règles d'association à extraire.
     * @param minConfidence La confiance minimale des règles d'association à extraire.
     * @return Un ensemble de règles d'association extraites.
     */
    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> associationRules = new HashSet<>();

        Apriori apriori = new Apriori(database);
        Set<Itemset> frequentItems = apriori.extract(minFrequency);

        for (Itemset itemset : frequentItems) {
            
            //Si itemset.getFrequency() < minFrequency, on passe à l'itération suite sans regarder la suite
            if (itemset.getFrequency() < minFrequency) {
                continue;
            }

            Set<BooleanVariable> itemsetItems = itemset.getItems();

            for (Set<BooleanVariable> subSet : BruteForceAssociationRuleMiner.allCandidatePremises(itemsetItems)) {
                float subSetFrequency = AbstractAssociationRuleMiner.frequency(subSet, frequentItems);

                //Si subSetFrequency < minFrequency, on passe à l'itération suite sans regarder la suite
                if (subSetFrequency < minFrequency) {
                    continue;
                }

                Set<BooleanVariable> itemPriveDeSub = new HashSet<>(itemsetItems);
                itemPriveDeSub.removeAll(subSet);

                float ruleConfidence = AbstractAssociationRuleMiner.confidence(subSet, itemPriveDeSub, frequentItems);

                if (ruleConfidence >= minConfidence) {
                    associationRules.add(new AssociationRule(subSet, itemPriveDeSub, apriori.frequency(itemsetItems), ruleConfidence));
                }
            }
        }

        return associationRules;
    }
}

/*

J'ai pu modifié allCandidatePremises pour qu'il ne parcourt pas l'ensembles des items inutilement

J'ai découvert l'utilisation de continue, ce qui m'a pas permi de rendre le code plus lisible.

Le code avant :

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> candidatePremises = new HashSet<>();

        // Générer tous les sous-ensembles de l'ensemble d'items
        Set<Set<BooleanVariable>> currentEns = new HashSet<>(Set.of(items));

        Set<Set<BooleanVariable>> copy_current = new HashSet<>(currentEns);
        while (!currentEns.isEmpty()) {

            for (Set<BooleanVariable> setItem : copy_current) {

                for (BooleanVariable item : items) {

                    Set<BooleanVariable> candidate = new HashSet<>(setItem);
                    candidate.remove(item);
                    if (!candidatePremises.contains(candidate) && !candidate.isEmpty()) {
                        currentEns.add(candidate);
                        candidatePremises.add(candidate);
                    }

                }
                currentEns.remove(setItem);

            }
            copy_current.clear();
            copy_current.addAll(currentEns);
            
        }

        return candidatePremises;

    }

    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> associationRules = new HashSet<>();
        
        Apriori apriori = new Apriori(database);
        Set<Itemset> frequentItems = apriori.extract(minFrequency);

        //System.out.println(frequentItems);

        for (Itemset itemset : frequentItems) {

            if (itemset.getFrequency() >= minFrequency) {

                Set<BooleanVariable> itemsItemset = itemset.getItems();
                
                for (Set<BooleanVariable> subSet : BruteForceAssociationRuleMiner.allCandidatePremises(itemsItemset)) {

                    if (AbstractAssociationRuleMiner.frequency(subSet, frequentItems) >= minFrequency) {
                        
                        Set<BooleanVariable> itemPriveDeSub = new HashSet<>(itemsItemset);
                        itemPriveDeSub.removeAll(subSet);
                        float ruleConfidence = AbstractAssociationRuleMiner.confidence(subSet, itemPriveDeSub, frequentItems);
                        if (ruleConfidence >= minConfidence) {
                            
                            associationRules.add(new AssociationRule(subSet, itemPriveDeSub, apriori.frequency(itemsItemset), ruleConfidence));

                        }

                    }

                }

            }

        }

        //System.out.println(associationRules);
        return associationRules;
    }
*/