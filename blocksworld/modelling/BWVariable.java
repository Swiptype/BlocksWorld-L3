package blocksworld.modelling;

import java.util.Set;

/**
 * Cette classe permet de factoriser certaine variable pour simplifier l'implémentation
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public abstract class BWVariable implements Variable{
    
    /** Variable */
    protected String name;
    protected Set<Object> domain;

    /** Constructor */
    public BWVariable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    /** Methode */

    /**
     * Obtient le nom de la variable.
     *
     * @return Le nom de la variable.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Cette méthode permet de récupérer le domaine de la variable
     * @return le domaine (l'ensemble des valeurs possibles)
     */
    @Override
    public Set<Object> getDomain() {
        return this.domain;
    }

    /**
     * Calcule le code de hachage de la variable.
     *
     * @return Le code de hachage de la variable.
     */
    @Override
    public int hashCode() {
        return 0;
    }
 
    /**
     * Compare cette variable avec un autre objet pour l'égalité.
     *
     * @param obj L'objet à comparer.
     * @return true si les variables sont égales, false sinon.
     */
    @Override
    public abstract boolean equals(Object obj);

}
