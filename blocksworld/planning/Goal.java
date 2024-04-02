package blocksworld.planning;

import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Interface représentant un objectif dans la planification.
 */
public interface Goal {

    /**
     * Vérifie si l'objectif est satisfait par une instance donnée.
     *
     * @param instance L'instance à vérifier.
     * @return true si l'objectif est satisfait, false sinon.
     */
    public boolean isSatisfiedBy(Map<Variable, Object> instance);

    /**
     * Obtient l'instance associée à l'objectif.
     *
     * @return L'instance associée à l'objectif.
     */
    public Map<Variable, Object> getGoalInstance();
}
