package blocksworld.modelling;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant une variable booléenne dans le contexte de la modélisation.
 */
public class BooleanVariable extends BWVariable{
    
    /**
     * Constructeur de la classe BooleanVariable.
     *
     * @param variableName Le nom de la variable booléenne.
     */
    public BooleanVariable(String variableName) {
        super(variableName, new HashSet<>(Set.of(true, false)));   
    }

    /**
     * Compare cette variable booléenne avec un autre objet pour l'égalité.
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
        if (!(obj instanceof BooleanVariable)) {
            return false;
        }
        BooleanVariable otherBooleanVar = (BooleanVariable) obj;
        return this.name.equals(otherBooleanVar.getName());
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères de la variable booléenne.
     *
     * @return Une chaîne de caractères représentant la variable booléenne.
     */
    @Override
    public String toString() {
        return "BooleanVariable [name=" + this.name + "]";
    }
    
}
