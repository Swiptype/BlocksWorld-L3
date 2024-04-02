package blocksworld.planning;

import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Implémentation basique d'un objectif dans un planificateur.
 */
public class BasicGoal implements Goal {

    /** Variable */
    private Map<Variable, Object> goalInstance;

    /**
     * Constructeur de l'objectif basique.
     *
     * @param goalInstance L'instance d'objectif à atteindre.
     */
    public BasicGoal(Map<Variable, Object> goalInstance)  {
        this.goalInstance = goalInstance;
    }

    /**
     * Vérifie si l'instance donnée satisfait l'objectif.
     *
     * @param inst L'instance à vérifier.
     * @return Vrai si l'objectif est satisfait, faux sinon.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> inst) {
        boolean ok = true;
        for (Variable key : this.goalInstance.keySet()) {
            if (this.goalInstance.get(key) != null) {
                ok = ok && this.goalInstance.get(key).equals(inst.get(key));
            } else {
                ok = false;
            }
        }
        return ok;
    }

    /**
     * Obtient l'instance d'objectif associée à cet objectif.
     *
     * @return L'instance d'objectif.
     */
    @Override
    public Map<Variable, Object> getGoalInstance() {
        return this.goalInstance;
    }
    
}
