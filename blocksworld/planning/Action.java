package blocksworld.planning;

import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Interface représentant une action dans un domaine de planification.
 */
public interface Action {
    
    /**
     * Vérifie si l'action est applicable dans une instance d'état donnée.
     *
     * @param instance L'instance d'état représentée sous la forme d'une carte de variables à des objets.
     * @return Vrai si l'action est applicable, faux sinon.
     * @require L'instance d'entrée ne doit pas être nulle.
     */
    public boolean isApplicable(Map<Variable, Object> instance);

    /**
     * Génère l'état successeur après l'application de l'action à l'instance d'état donnée.
     *
     * @param instance L'instance d'état représentée sous la forme d'une carte de variables à des objets.
     * @return Une nouvelle instance d'état représentant le résultat de l'application de l'action.
     * @require L'instance d'entrée ne doit pas être nulle.
     * @ensure L'instance retournée est un état successeur valide.
     */
    public Map<Variable, Object> successor(Map<Variable, Object> instance);

    /**
     * Obtient le coût associé à l'action.
     *
     * @return Le coût de l'action.
     */
    public int getCost();
    
}
