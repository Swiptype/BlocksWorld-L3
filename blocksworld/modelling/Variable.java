package blocksworld.modelling;

import java.util.Set;

/**
 * Cette interface représente des éléments dont les domaines peuvent changer.
 * Elle définit des méthodes pour récupérer le nom, le domaine, comparer des instances et obtenir un code de hachage.
 * 
 * Les classes implémentant cette interface peuvent être utilisées pour représenter des variables dans un modèle.
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public interface Variable {

    /**
     * Cette méthode permet de récupérer le nom de l'instance.
     *
     * @return Le nom de l'instance.
     */
    public String getName();

    /**
     * Cette méthode permet de récupérer le domaine de l'instance.
     *
     * @return Le domaine de l'instance (l'ensemble des valeurs possibles).
     */
    public Set<Object> getDomain();

    /**
     * Cette méthode permet de comparer l'instance avec un autre objet.
     *
     * @param obj L'objet avec lequel comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    public boolean equals(Object obj);

    /**
     * Cette méthode permet d'obtenir un code de hachage pour l'instance.
     *
     * @return Le code de hachage de l'instance.
     */
    public int hashCode();
}
