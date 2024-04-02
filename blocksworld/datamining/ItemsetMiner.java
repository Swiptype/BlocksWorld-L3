package blocksworld.datamining;

import java.util.Set;


/**
 * L'interface ItemsetMiner déclare des méthodes pour extraire des itemsets à partir d'une base de données.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public interface ItemsetMiner {
    
    /**
     * Obtient la base de données associée à l'extracteur d'itemsets.
     *
     * @return La base de données utilisée pour extraire les itemsets.
     */
    public BooleanDatabase getDataBase();

    /**
     * Extrait un ensemble d'itemsets à partir de la base de données avec une fréquence minimale spécifiée.
     *
     * @param minimFrequency La fréquence minimale des itemsets à extraire.
     * @return Un ensemble d'itemsets extrait de la base de données.
     */
    public Set<Itemset> extract(float minimFrequency);

}
