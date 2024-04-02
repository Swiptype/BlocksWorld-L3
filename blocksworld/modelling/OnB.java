package blocksworld.modelling;

import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe représente une variable qui indique la variable située en dessous d'un bloc.
 * 
 * Le nom de la variable est le numéro du bloc.
 * 
 * Exemple : Pour représenter le fait que le bloc 1 est en dessous du bloc 2, on peut créer une instance
 * de la classe OnB avec le nom "1".
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class OnB extends BWVariable {

    /** Constructor */
    public OnB(String name, Set<Object> domain) {
        super(name, domain);
    }

    public OnB(Integer nameInt, Set<Object> domain) {
        this(Integer.toString(nameInt), domain);
    }

    public OnB(String name) {
        this(name, new HashSet<>());
    }

    public OnB(Integer nameInt) {
        this(Integer.toString(nameInt), new HashSet<>());
    }
    
    /** Methode */

    /**
     * Vérifie si cette instance de OnB est égale à un autre objet.
     *
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OnB)) {
            return false;
        }
        OnB otherOnB = (OnB) obj;
        return this.name.equals(otherOnB.getName());
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la variable OnB.
     *
     * @return Une chaîne de caractères représentant la variable OnB.
     */
    @Override
    public String toString() {
        return "OnB [name=" + this.name + "]";
    }

}
