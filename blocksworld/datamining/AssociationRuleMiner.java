package blocksworld.datamining;

import java.util.*;

/**
 * L'interface AssociationRuleMiner définit les méthodes nécessaires pour extraire des règles d'association
 * à partir d'une base de données booléenne.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public interface AssociationRuleMiner {

    /**
     * Obtient la base de données booléenne associée à l'extracteur de règles d'association.
     *
     * @return La base de données booléenne.
     */
    public BooleanDatabase getDatabase();

    /**
     * Extrait un ensemble de règles d'association en fonction de la fréquence minimale et de la confiance minimale spécifiées.
     *
     * @param minFrequency La fréquence minimale des règles d'association à extraire.
     * @param minConfidence La confiance minimale des règles d'association à extraire.
     * @return Un ensemble de règles d'association extraites.
     */
    public Set<AssociationRule> extract(float minFrequency, float minConfidence);

}