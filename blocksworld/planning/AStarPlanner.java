package blocksworld.planning;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.heuristic.Heuristic;
import blocksworld.modelling.Variable;

/**
 * Implémentation d'un planificateur A*.
 */
public class AStarPlanner implements Planner {

    /** Variable */
    private Map<Variable, Object> iniState;
    private Set<Action> setOfActions;
    private Goal goal;
    private Heuristic heuristic;

    private Sonde sonde;
    
    /** Constructeur */
    public AStarPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal, Heuristic heuristic, Sonde sonde) {
        this.iniState = iniState;
        this.setOfActions = setOfActions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.sonde = sonde;
    }
    public AStarPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal, Heuristic heuristic) {
        this(iniState, setOfActions, goal, heuristic, null);
    }

    /**
     * Planifie une séquence d'actions pour atteindre le but à partir de l'état initial.
     *
     * @return Liste d'actions planifiées ou null si aucune solution n'est trouvée.
     */
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<Map<Variable, Object>, Float>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
        LinkedList<Map<Variable, Object>> open  = new LinkedList<Map<Variable, Object>>();
        Map<Map<Variable, Object>, Float> value = new HashMap<Map<Variable, Object>, Float>();


        father.put(this.iniState, null);
        distance.put(this.iniState, (float) 0);
        open.add(this.iniState);
        value.put(this.iniState, heuristic.estimate(this.iniState));

        Map<Variable, Object> instanciation, next;

        while (!open.isEmpty()) {
            if (this.sonde != null) this.sonde.add();
            instanciation = this.argmin(open, value);
            if (this.goal.isSatisfiedBy(instanciation)) {
                return this.get_bfs_plan(father, plan, instanciation);
            } else {
                open.remove(instanciation);
                for (Action action : this.setOfActions) {
                    if (action.isApplicable(instanciation)) {
                        next = action.successor(instanciation);
                        if (!distance.containsKey(next)) {
                            distance.put(next, Float.POSITIVE_INFINITY);
                        }
                        if (distance.get(next) > distance.get(instanciation) + action.getCost()) {
                            distance.put(next, distance.get(instanciation) + action.getCost());
                            value.put(next, distance.get(next) + this.heuristic.estimate(next));
                            father.put(next, instanciation);
                            plan.put(next, action);
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Trouve l'élément avec la plus petite valeur associée dans la liste.
     *
     * @param listState  Liste d'instances d'état.
     * @param listFloat  Liste de valeurs associées.
     * @return Instance d'état avec la plus petite valeur.
     */
    public Map<Variable, Object> argmin(List<Map<Variable, Object>> listState, Map< Map<Variable, Object>, Float > listFloat) {
        Float min = Float.POSITIVE_INFINITY;
        Map<Variable,Object> tmpState = new HashMap<Variable,Object>();
        for (Map<Variable,Object> state : listState) {
            if (listFloat.get(state) < min) {
                min = listFloat.get(state);
                tmpState = state;
            }
        }
        return tmpState;
    }

    /**
     * Retourne le plan trouvé lors de la recherche en largeur d'abord.
     *
     * @param father    Les relations parent-enfant.
     * @param plan      Les actions associées aux états.
     * @param goalState L'état objectif.
     * @return Liste d'actions formant le plan.
     */
    public List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goalState) {
        LinkedList<Action> bfs_plan = new LinkedList<Action>();
        while( father.get(goalState) != null ) {
            bfs_plan.addFirst(plan.get(goalState));
            goalState = father.get(goalState);
        }
        return bfs_plan;
    }

    /** Accesseur */
    /**
     * Obtient l'état initial.
     *
     * @return L'état initial.
     */
    public Map<Variable, Object> getInitialState() {
        return this.iniState;
    }

    /**
     * Obtient l'ensemble d'actions disponibles.
     *
     * @return L'ensemble d'actions.
     */
    public Set<Action> getActions() {
        return this.setOfActions;
    }

    /**
     * Obtient le but à atteindre.
     *
     * @return Le but à atteindre.
     */
    public Goal getGoal() {
        return this.goal;
    }

    /**
     * Obtient l'heuristique utilisée.
     *
     * @return L'heuristique.
     */
    public Heuristic getHeuristic() {
        return this.heuristic;
    }

}
