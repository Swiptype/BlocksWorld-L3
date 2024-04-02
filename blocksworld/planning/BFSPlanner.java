package blocksworld.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Variable;

/**
 * Implémentation d'un planificateur utilisant la recherche en largeur (BFS).
 */
public class BFSPlanner implements Planner {
    
    /** Variable */
    private Map<Variable, Object> iniState;
    private Set<Action> setOfActions;
    private Goal goal;

    private Sonde sonde;

    /**
     * Constructeur du planificateur BFS.
     *
     * @param iniState     L'état initial.
     * @param setOfActions L'ensemble d'actions disponibles.
     * @param goal         Le but à atteindre.
     * @param sonde        La sonde qui sert à compter le nombre de noeuds
     */
    public BFSPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal, Sonde sonde) {
        this.iniState = iniState;
        this.setOfActions = setOfActions;
        this.goal = goal;
        this.sonde = sonde;
    }
    public BFSPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal) {
        this(iniState, setOfActions, goal, null);
    }

    /**
     * Planifie une séquence d'actions pour atteindre le but à partir de l'état initial en utilisant la recherche en largeur.
     *
     * @return Liste d'actions planifiées ou null si aucune solution n'est trouvée.
     */
    public List<Action> plan() {

        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
        //List<Action> plan = new ArrayList<Action>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();

        Map<Variable, Object> instanciation, next;

        Set< Map<Variable, Object> > closed = new HashSet<Map<Variable,Object>>();
        LinkedList< Map<Variable, Object> > open = new LinkedList<Map<Variable, Object>>();

        closed.add(this.iniState);
        open.add(this.iniState);
        father.put(this.iniState, null);

        if (this.goal.isSatisfiedBy(this.iniState)) {
            //return plan;
            return new ArrayList<Action>();       
        }

        if (this.setOfActions.isEmpty()) {
            return null;
        }
        
        while (!open.isEmpty()) {
            if (this.sonde != null) this.sonde.add();
            instanciation = open.poll();            
            closed.add(instanciation);
            for (Action action : this.setOfActions) {
                if (action.isApplicable(instanciation)) {
                    next = action.successor(instanciation);
                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instanciation);
                        //plan.add(action);
                        plan.put(next, action);
                        if (this.goal.isSatisfiedBy(next)) {
                            //return plan;
                            return get_bfs_plan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Construit le plan BFS à partir des informations de remontée.
     *
     * @param father   Les relations parent-enfant.
     * @param plan     Les actions associées aux états.
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

}
