package blocksworld.planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Implémentation d'un planificateur utilisant la recherche en profondeur (DFS).
 */
public class DFSPlanner implements Planner{
    
    /** Variable */
    private Map<Variable, Object> iniState;
    private Set<Action> setOfActions;
    private Goal goal;

    private Sonde sonde;

    /**
     * Constructeur du planificateur DFS.
     *
     * @param iniState     L'état initial.
     * @param setOfActions L'ensemble d'actions disponibles.
     * @param goal         Le but à atteindre.
     * @param sonde        La sonde qui sert à compter le nombre de noeuds
     */
    public DFSPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal, Sonde sonde) {
        this.iniState = iniState;
        this.setOfActions = setOfActions;
        this.goal = goal;
        this.sonde = sonde;
    }
    public DFSPlanner(Map<Variable, Object> iniState, Set<Action> setOfActions, Goal goal) {
        this(iniState, setOfActions, goal, null);
    }

    /**
     * Planifie une séquence d'actions pour atteindre le but à partir de l'état initial en utilisant la recherche en profondeur.
     *
     * @return Liste d'actions planifiées ou null si aucune solution n'est trouvée.
     */
    public List<Action> plan() {
        ArrayList<Action> planVar = new ArrayList<Action>();
        Set<Map<Variable, Object>> closed = new HashSet<Map<Variable,Object>>();
        return this.planEx(this.iniState, planVar, closed);
        
    }

    /**
     * Méthode récursive utilisée par le planificateur DFS pour explorer les états.
     *
     * @param instanciation L'instance d'état actuelle.
     * @param planVar       La liste d'actions accumulées jusqu'à présent.
     * @param closed        L'ensemble des états explorés.
     * @return Liste d'actions planifiées ou null si aucune solution n'est trouvée.
     */
    public List<Action> planEx(Map<Variable, Object> instanciation, ArrayList<Action> planVar, Set<Map<Variable, Object>> closed) {
        if (this.sonde != null) this.sonde.add();

        Map<Variable, Object> next;
        
        if (this.goal.isSatisfiedBy(instanciation)) {
            //System.out.println(planVar.size());
            return planVar;
        }
        else {
            for (Action action : setOfActions) {
                if (action.isApplicable(instanciation)) {
                    next = action.successor(instanciation);
                    if (!closed.contains(next)) {
                        planVar.add(action);
                        closed.add(next);
                        List<Action> subPlan = this.planEx(next, planVar, closed);
                        if (!(subPlan == null)) {
                            return subPlan;
                        }
                    }
                }
            }
            return null;
        }
    }


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
