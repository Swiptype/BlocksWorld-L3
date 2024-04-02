package blocksworld.datamining;

import java.util.Comparator;
import java.util.Set;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe abstraite AbstractItemsetMiner implémente l’interface ItemsetMiner,
 * Elle founit une classe représentant une base pour l'extraction et le calcul de la fréquence des différents itemsets.
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner{

    /** Variable */
    protected BooleanDatabase bdd;

    /** Constructeur */
    /**
     * Construit un AbstractItemsetMiner avec une base de données BooleanDatabase.
     *
     * @param bdd La base de données BooleanDtabase à utiliser pour l'extraction des itemsets.
     */
    public AbstractItemsetMiner(BooleanDatabase bdd) {
        this.bdd = bdd;
    }

    /** Accesseur */
    /**
     * Obtient la base de données BooleanDatabase associée à cet extracteur d'itemsets.
     *
     * @return La base de données BooleanDatabase.
     */
    public BooleanDatabase getDataBase() {
        return this.bdd;
    }

    /**
     * Méthode abstraite pour extraire les itemsets avec une fréquence minimale.
     *
     * @param minimFrequency La fréquence minimale des itemsets à extraire.
     * @return L'ensemble des itemsets extraits.
     */
    public abstract Set<Itemset> extract(float minimFrequency);

    /**
     * Méthode pour calculer la fréquence d’un ensemble donné d’items dans la base.
     *
     * @param items L'ensemble d'items dont on souhaite calculer la fréquence.
     * @return La fréquence de l'ensemble d'items dans la base de données.
     */
    public float frequency(Set<BooleanVariable> items) {
        float support = 0;
        for (Set<BooleanVariable> transaction : this.bdd.getTransactions()) {
            if (transaction.containsAll(items)) {
                support+=1;
            }
        }
        return support / (float) this.bdd.getTransactions().size();
    }

    /** Attribut statique COMPARATOR permettant de comparer les items. */
    public static final Comparator<BooleanVariable> COMPARATOR = 
            (var1, var2) -> var1.getName().compareTo(var2.getName());



}
