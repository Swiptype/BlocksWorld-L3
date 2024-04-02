package blocksworld.datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe Apriori étend la classe AbstractItemsetMiner.
 * Elle implémente l'algorithme Apriori pour l'extraction des itemsets fréquents.
 */
public class Apriori extends AbstractItemsetMiner {

    /** Constructeur */
    /**
     * Construit un Apriori avec la base de données Boolean spécifiée.
     *
     * @param bdd La base de données Boolean à utiliser pour l'extraction des itemsets.
     */
    public Apriori(BooleanDatabase bdd) {
        super(bdd);
    }

    /* Redefinition de la méthode extract */
    @Override
    public Set<Itemset> extract(float minimFrequency) {
        Set<Itemset> result = new HashSet<Itemset>();
        SortedSet<BooleanVariable> ensItem = new TreeSet<BooleanVariable>(AbstractItemsetMiner.COMPARATOR);
        List<SortedSet<BooleanVariable>> frequentItems = new ArrayList<SortedSet<BooleanVariable>>();

        ensItem.addAll(this.bdd.getItems());

        // Génération des ensembles fréquents de taille 1

        for (BooleanVariable item : ensItem) {
            SortedSet<BooleanVariable> singletons = new TreeSet<BooleanVariable>(AbstractItemsetMiner.COMPARATOR);
            singletons.add(item);
            float itemFrequency = this.frequency(singletons);
            if (minimFrequency <= itemFrequency) {
                frequentItems.add(singletons);
                result.add(new Itemset(singletons, itemFrequency));
            }
        }

        // Génération des ensembles fréquents de taille supérieur à 1

        //Tous les items par lesquels on passe
        List<SortedSet<BooleanVariable>> currentItems = new ArrayList<SortedSet<BooleanVariable>>(frequentItems);

        //Si il n'y en a plus à passer, on arrête
        while (!currentItems.isEmpty()) {
            //On crée une copy pour eviter les problemes de concurency
            List<SortedSet<BooleanVariable>> copy_currentItems = new ArrayList<SortedSet<BooleanVariable>>(currentItems);

            //On génére les items en taille k
            for (SortedSet<BooleanVariable> subSet1 : copy_currentItems) {

                for (SortedSet<BooleanVariable> subSet2 : copy_currentItems) {

                    SortedSet<BooleanVariable> items = combine(subSet1, subSet2);
                    //On véréfie si les sous-ensembles du nouvelles "items" sont fréquents
                    if (items != null && !frequentItems.contains(items) && Apriori.allSubsetsFrequent(items, frequentItems)) {
                        float itemsFrequency = this.frequency(items);
                        if (minimFrequency <= itemsFrequency) {
                            frequentItems.add(items);
                            currentItems.add(items);
                            result.add(new Itemset(items, itemsFrequency));
                        }
                    }

                }
                //On enlève les items en taille k-1
                currentItems.remove(subSet1);
            }
        }

        return result;
    }
    
    /* 
     * La méthode frequentSingletons prend en argument une fréquence, 
     * et retourne l’ensemble de tous les itemsets singletons (avec leur 
     * fréquence) dont la fréquence dans la base est au moins égale à celle donnée
    */
    /*
     * Si la frequence des items de la database est superieure ou égale à 
     * la fréquence donnée, on ajoute dans un HashSet les itemsets singletons 
     * avec leur fréquence.
     * 
     * On renvoie ce HashSet.
     */

    /**
     * Retourne l'ensemble de tous les itemsets singletons (avec leur fréquence)
     * dont la fréquence dans la base est au moins égale à celle donnée.
     *
     * @param minimFrequency La fréquence minimale des itemsets singletons à extraire.
     * @return L'ensemble des itemsets singletons extraits.
     */
    public Set<Itemset> frequentSingletons(float minimFrequency) {
        Set<Itemset> res = new HashSet<>();
        
        for (BooleanVariable item : this.getDataBase().getItems()) {
            float itemFrequency = this.frequency(Set.of(item));
            if (itemFrequency >= minimFrequency) {
                res.add(new Itemset(Set.of(item), itemFrequency));
            }
        }

        return res;
    }

    /*
     * La méthode statique combine, prend en arguments deux ensembles d’items 
     * et retourne un ensemble d’items obtenu en les combinant à condition que : 
     * - les deux ensembles aient la même taille k,
     * - les deux ensembles aient les mêmes k − 1 premiers items,
     * - les deux ensembles aient des kes items différents.
     * Dans tous les autres cas, la méthode renvoie null.
     */
    /*
     * On verifie que les deux ensembles n'ont pas des tailles differentes 
     * ou que l'un ne contienne pas l'autre et inversement.
     * 
     * On créer des Iterateur pour les ensembles.
     * 
     * On verifie que, pour la taille du premier ensemble, les prochains items
     * de chaque ensemble sont differents l'un de l'autre.
     * 
     * Si ce n'est pas le cas, on ajoute le prochain item du premier ensemble 
     * dans un TreeSet
     * 
     * Si les prochains items de chaque ensemble sont differents l'un de l'autre
     * et que les k-1 premiers items sont différents, on renvoie null.
     * 
     * Si les prochains items de chaque ensemble sont differents l'un de l'autre
     * et que les k-1 premiers items ne sont pas différents, on ajoute les prochains 
     * items de chaque ensemble dans un TreeSet.
     * 
     * Puis on renvoie le TreeSet
     */

    /**
     * Retourne un ensemble d’items obtenu en combinant deux ensembles à condition que :
     * - les deux ensembles aient la même taille k,
     * - les deux ensembles aient les mêmes k − 1 premiers items,
     * - les deux ensembles aient des items différents.
     *
     * @param subSet1 Le premier ensemble d'items.
     * @param subSet2 Le deuxième ensemble d'items.
     * @return Un ensemble d'items résultant de la combinaison, ou null si les conditions ne sont pas remplies.
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> subSet1, SortedSet<BooleanVariable> subSet2) {
        if (subSet1.size() != subSet2.size() || (subSet1.containsAll(subSet2) && subSet2.containsAll(subSet1))) {
            return null;
        }


        int nVar = subSet1.size();

        Iterator<BooleanVariable> iSet1 = subSet1.iterator();
        Iterator<BooleanVariable> iSet2 = subSet2.iterator();

        SortedSet<BooleanVariable> res = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);

        for (int i = 0; i < nVar; i++) {
            BooleanVariable iNext1 = iSet1.next();
            BooleanVariable iNext2 = iSet2.next();
            if (iNext1 != iNext2) {
                if (i != nVar-1) {
                    return null;
                } else {
                    res.add(iNext1);
                    res.add(iNext2);
                }
            } else {
                res.add(iNext1);
            }
        }
        return res;
    }

    /*
     * La méthode statique allSubsetsFrequent prend en arguments un ensemble d’items
     * et une collection d’ensembles d’items.
     * Elle retourne true si tous les sous-ensembles obtenus en supprimant exactement
     * un élément de l’ensemble d’items sont contenus dans la collection.
     */
    /*
     * On retire un item du HashSet qu'on créé.
     * Si la collecttion ne contient pas tout ce qui est dans le HashSet, 
     * on renvoie False.
     * 
     * On ajoute un item au HashSet.
     * 
     * On renvoie True.
     */

    /**
     * Retourne true si tous les sous-ensembles obtenus en supprimant exactement
     * un élément de l’ensemble d’items sont contenus dans la collection.
     *
     * @param items L'ensemble d'items pour lequel on vérifie la fréquence des sous-ensembles.
     * @param frequentItem La collection d'ensembles d'items fréquents.
     * @return True si tous les sous-ensembles sont fréquents, sinon False.
     */
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentItem) {
        Set<BooleanVariable> candidate = new HashSet<>(items);
        for (BooleanVariable item : items) {
            candidate.remove(item);
            if (!frequentItem.contains(candidate)) {
                return false;
            }
            candidate.add(item);
        }
        
        return true;
    }

}

