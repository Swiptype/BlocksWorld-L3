package blocksworld.heuristic;

import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * Interface définissant une heuristique pour l'estimation du coût d'une instance.
 */
public interface Heuristic {

    /**
     * Estime le coût de l'instance donnée.
     *
     * @param inst L'instance pour laquelle estimer le coût.
     * @return Le coût estimé.
     */
    public float estimate(Map<Variable, Object> inst);

}
