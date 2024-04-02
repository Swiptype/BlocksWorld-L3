package blocksworld.planning;

import java.util.HashMap;
import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Cette classe permet d'appliquer une action sur une instance
 * 
 * @author teurter211 Alexis TEURTERIE
 * @author genesti211 Theo GENESTIER
 */
public class BasicAction implements Action {
    
    /** Variable */
    private Map<Variable, Object> precondition, effect;
    private int cost;

    /** Constructor */
    /**
     * Constructeur de l'action basique avec précondition, effet et coût.
     *
     * @param precondition La précondition de l'action.
     * @param effect       L'effet de l'action.
     * @param cost         Le coût de l'action.
     */
    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object>effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }
    /**
     * Constructeur de l'action basique avec précondition et effet, le coût par défaut est 1.
     *
     * @param precondition La précondition de l'action.
     * @param effect       L'effet de l'action.
     */
    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object>effect) {
        this(precondition, effect, 1);
    }

    /**
     * Cette méthode permet de dire si on peut appliquer une action sur cette instance.
     *
     * @param inst L'instance sur laquelle appliquer l'action.
     * @return Vrai si l'action est applicable, faux sinon.
     */
    public boolean isApplicable(Map<Variable,Object> inst) {
        boolean ok = true;
        for (Variable key : this.precondition.keySet()) {
            if (inst.get(key) != null) {
                ok = ok && inst.get(key).equals(this.precondition.get(key));
            } else {
                ok = false;
            }
        }
        return ok;
        
    }

    /**
     * Cette méthode permet d'appliquer l'action sur cette instance.
     *
     * @param inst L'instance sur laquelle appliquer l'action.
     * @return Nouvelle instance avec l'effet de l'action appliqué.
     */
    public Map<Variable, Object> successor(Map<Variable, Object> inst) {
        HashMap<Variable, Object> newInst = new HashMap<Variable, Object>();
        
        for (Variable key : this.effect.keySet()) {
            newInst.put(key, this.effect.get(key));
        }
        for (Variable key : inst.keySet()) {
            if (!newInst.containsKey(key)) {
                newInst.put(key,inst.get(key));
            }
        }
        return newInst;
    }

    /**
     * Cette méthode permet de récupérer le coût de l'action.
     *
     * @return Le coût de l'action.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Redéfinition de la méthode toString pour afficher une représentation textuelle de l'action.
     *
     * @return Une chaîne de caractères représentant l'action.
     */
    @Override
    public String toString() {
        return "Action [precondition:" + this.precondition +", effect:" + this.effect + "]";
    }
    
}
