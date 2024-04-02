package blocksworld.modelling;

import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe nous permet de savoir si une pile est libre ou non
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class FreeP extends BWVariable {
    
    /** Constructor */
    public FreeP(String name, Set<Object> domain) {
        super(name, domain);
    }

    public FreeP(Integer nameInt, Set<Object> domain) {
        this(Integer.toString(nameInt), domain);
    }

    public FreeP(String name) {
        this(name, new HashSet<>());
    }

    public FreeP(Integer nameInt) {
        this(Integer.toString(nameInt), new HashSet<>());
    }

    /** Methode */
    /**
     * Compare cette variable libre avec un autre objet pour l'égalité.
     *
     * @param obj L'objet à comparer.
     * @return true si les variables sont égales, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FreeP)) {
            return false;
        }
        FreeP otherFreeP = (FreeP) obj;
        return this.name.equals(otherFreeP.getName());

    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères de la variable libre.
     *
     * @return Une chaîne de caractères représentant la variable libre.
     */
    @Override
    public String toString() {
        return "FreeP [name=" + this.name + "]";
    }

}
