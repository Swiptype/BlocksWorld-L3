package blocksworld.datamining;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe BooleanDatabase représente des bases de données transactionnelles.
 * Elle est utilisée pour stocker des transactions associées à un ensemble d'items.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BooleanDatabase {

    /** Variables */
    protected Set<BooleanVariable> items_key;
    protected List<Set<BooleanVariable>> transactions;
    
    /** Constructeur */
    /**
     * Construit une BooleanDatabase avec l’ensemble des items spécifié et initialise une base vide.
     *
     * @param items_key L’ensemble des items de la base de données.
     */
    public BooleanDatabase(Set<BooleanVariable> items_key) {
        this.items_key = items_key;
        this.transactions = new ArrayList<>();
    }

    /**
     * Ajoute une transaction à la base de données.
     *
     * @param transaction La transaction à ajouter à la base de données.
     */
    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }

    /** Accesseurs */
    /**
     * Obtient l’ensemble des items de la base de données.
     *
     * @return L’ensemble des items de la base de données.
     */
    public Set<BooleanVariable> getItems() {
        return this.items_key;
    }

    /**
     * Obtient la liste des transactions de la base de données.
     *
     * @return La liste des transactions de la base de données.
     */
    public List<Set<BooleanVariable>> getTransactions() {
        return this.transactions;
    }

}
