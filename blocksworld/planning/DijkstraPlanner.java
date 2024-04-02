package blocksworld.planning;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import blocksworld.generate.InstanceTools;
import blocksworld.modelling.Variable;

/**
 * Implémentation d'un planificateur utilisant l'algorithme de Dijkstra.
 */
public class DijkstraPlanner implements Planner{

    /** Variable */
    private Map<Variable, Object> iniState;
    private Set<Action> setOfActions;
    private Goal goal;

    private Sonde sonde;

    /**
     * Constructeur du planificateur Dijkstra.
     *
     * @param iniState     L'état initial.
     * @param setOfActions L'ensemble d'actions disponibles.
     * @param goal         Le but à atteindre.
     * @param sonde        La sonde qui sert à compter le nombre de noeuds
     */
    public DijkstraPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal, Sonde sonde) {
        this.iniState = iniState;
        this.setOfActions = setOfActions;
        this.goal = goal;
        this.sonde = sonde;
    }
    public DijkstraPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal) {
        this(iniState, setOfActions, goal, null);
    }

    /**
     * Planifie une séquence d'actions pour atteindre le but à partir de l'état initial en utilisant l'algorithme de Dijkstra.
     *
     * @return Liste d'actions planifiées ou null si aucune solution n'est trouvée.
     */
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<Map<Variable, Object>, Float>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
        LinkedList<Map<Variable, Object>> goals = new LinkedList<Map<Variable, Object>>();

        LinkedList<Map<Variable, Object>> open  = new LinkedList<Map<Variable, Object>>();

        father.put(this.iniState, null);
        distance.put(this.iniState, (float) 0);
        open.add(this.iniState);

        Map<Variable, Object> instanciation, next;

        while (!open.isEmpty()) {
            if (this.sonde != null) this.sonde.add();
            instanciation = this.argmin(open,distance);
            open.remove(instanciation);
            if (this.goal.isSatisfiedBy(instanciation)) {
                goals.add(instanciation);
            }
            for (Action action : this.setOfActions) {
                if (action.isApplicable(instanciation)) {
                    next = action.successor(instanciation);
                    if (!distance.containsKey(next)) {
                        distance.put(next, Float.POSITIVE_INFINITY);
                    }
                    if (distance.get(next) > distance.get(instanciation) + action.getCost()) {
                        distance.put(next, distance.get(instanciation) + action.getCost());
                        father.put(next, instanciation);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        if (goals.isEmpty()) {
            return null;
        } else {
            return this.getDijkstraPlan(father, plan, goals, distance);
        }


    }

    /**
     * Construit le plan Dijkstra à partir des informations de remontée.
     *
     * @param father   Les relations parent-enfant.
     * @param plan     Les actions associées aux états.
     * @param goals    Les états objectifs.
     * @param distance Les distances accumulées pour chaque état.
     * @return Liste d'actions formant le plan.
     */
    public List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, List<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Float> distance) {
        LinkedList<Action> bfs_plan = new LinkedList<Action>();
        Map<Variable, Object> goal = this.argmin(goals, distance);
        while( father.get(goal) != null ) {
            bfs_plan.addFirst(plan.get(goal));
            goal = father.get(goal);
        }
        return bfs_plan;
    }

    /**
     * Trouve l'état avec la distance minimale dans la liste d'états.
     *
     * @param open     Liste des états à explorer.
     * @param distance Les distances accumulées pour chaque état.
     * @return L'état avec la distance minimale.
     */
    public Map<Variable, Object> argmin(List<Map<Variable, Object>> open, Map< Map<Variable, Object>, Float > distance) {
        Float min = Float.POSITIVE_INFINITY;
        Map<Variable,Object> tmpState = new HashMap<Variable,Object>();
        for (Map<Variable,Object> state : open) {
            if (distance.get(state) < min) {
                min = distance.get(state);
                tmpState = state;
            }
        }
        return tmpState;
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
}
