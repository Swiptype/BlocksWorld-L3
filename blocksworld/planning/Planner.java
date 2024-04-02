package blocksworld.planning;

import java.util.List;
import java.util.Set;
import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Interface représentant un planificateur dans le contexte de la planification.
 */
public interface Planner {

    /**
     * Planifie une séquence d'actions pour atteindre un objectif à partir de l'état initial.
     *
     * @return Liste d'actions planifiées ou null si aucune solution n'est trouvée.
     */
    public List<Action> plan();

    /**
     * Obtient l'état initial à partir duquel la planification est effectuée.
     *
     * @return L'état initial.
     */
    public Map<Variable, Object> getInitialState();

    /**
     * Obtient l'ensemble d'actions disponibles pour la planification.
     *
     * @return L'ensemble d'actions.
     */
    public Set<Action> getActions();

    /**
     * Obtient l'objectif à atteindre dans le cadre de la planification.
     *
     * @return L'objectif à atteindre.
     */
    public Goal getGoal();
}
