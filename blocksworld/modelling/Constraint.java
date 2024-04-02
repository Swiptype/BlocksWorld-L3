package blocksworld.modelling;

import java.util.Map;
import java.util.Set;

/**
 * Interface représentant une contrainte dans le contexte de la modélisation.
 * La classe Constraint permet d'appliquer certaines contraintes, notamment à des actions.
 */
public interface Constraint {
    
    /**
     * Obtient l'ensemble des variables nécessaires à la contrainte.
     *
     * @return L'ensemble des variables nécessaires à la contrainte.
     */
    public Set<Variable> getScope();

    /**
     * Vérifie si la contrainte est respectée par une instantiation donnée.
     *
     * @param instantiation L'instantiation à vérifier.
     * @return true si la contrainte est respectée, false sinon.
     */
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation);

}
