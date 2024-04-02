package blocksworld.modelling;

import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe nous permet de savoir si un block est fixé ou non
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class FixedB extends BWVariable {
    
    /** Constructor */
    public FixedB(String name, Set<Object> domain) {
        super(name, domain);
    }

    public FixedB(Integer nameInt, Set<Object> domain) {
        this(Integer.toString(nameInt), domain);
    }

    public FixedB(String name) {
        this(name, new HashSet<>());
    }

    public FixedB(Integer nameInt) {
        this(Integer.toString(nameInt), new HashSet<>());
    }

    /* Methode */
    /**
     * Compare cette variable fixe avec un autre objet pour l'égalité.
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
        if (!(obj instanceof FixedB)) {
            return false;
        }
        FixedB otherFixedB = (FixedB) obj;
        return this.name.equals(otherFixedB.getName());
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la variable fixe.
     *
     * @return Une chaîne de caractères représentant la variable fixe.
     */
    @Override
    public String toString() {
        return "FixedB [name=" + this.name + "]";
    }
    
}
