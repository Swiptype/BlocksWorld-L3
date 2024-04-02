package blocksworld.heuristic;

import java.util.HashMap;
import java.util.Map;

import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;
import blocksworld.planning.Goal;

/**
 * Implémentation de l'heuristique de la distance de Manhattan pour la planification dans Blocksworld.
 */
public class DistanceManhattanHeuristic implements Heuristic {

    // Goal représentant l'état cible
    protected Goal goal;

    /**
     * Constructeur pour la classe DistanceManhattanHeuristic .
     *
     * @param goal Le goal représentant l'état cible.
     */
    public DistanceManhattanHeuristic(Goal goal) {
        this.goal = goal;
    }

    /**
     * Estime le coût pour atteindre l'état cible à partir de l'état actuel en utilisant la distance de Manhattan.
     *
     * @param inst L'état actuel pour lequel estimer le coût.
     * @return Le coût estimé en utilisant l'heuristique de la distance de Manhattan.
     */
    @Override
    public float estimate(Map<Variable, Object> inst) {
        float distanceManhattan = 0;
        Map<Integer, int[]> posGoal = new HashMap<>();
        Map<Integer, int[]> posInst = new HashMap<>();

        // Calculer les positions des blocs dans l'état cible
        for (Variable variable : this.goal.getGoalInstance().keySet()) {
            if (variable instanceof OnB) {
                boolean stop = false;

                Variable current = variable;
                int y = 0;

                // Calculer la coordonnée y pour le bloc dans l'état cible
                while (!stop) {
                    int valOnB = (Integer) this.goal.getGoalInstance().get(current);
                    if (valOnB < 0) {
                        posInst.put(Integer.valueOf(variable.getName()), new int[]{Math.abs(valOnB)-1, y});
                        stop = true;
                    } else {
                        y += 1;
                        current = new OnB(valOnB);
                    }
                }
                stop = false;
                current = variable;
                y = 0;

                // Calculer la coordonnée y pour le bloc dans l'état actuel
                while (!stop) {
                    int valOnB = (Integer) inst.get(current);
                    if (valOnB < 0) {
                        posInst.put(Integer.valueOf(variable.getName()), new int[]{Math.abs(valOnB)-1, y});
                        stop = true;
                    } else {
                        y += 1;
                        current = new OnB(valOnB);
                    }
                }
            
            }
        }

        // Calculer la distance de Manhattan entre les positions des blocs et accumuler la distance totale
        for (Integer intVariable : posGoal.keySet()) {
            distanceManhattan += DistanceManhattanHeuristic.calcDistManhattan(posGoal.get(intVariable), posInst.get(intVariable));
        }
        return distanceManhattan;
    }

    /**
     * Calcule la distance de Manhattan entre deux ensembles de coordonnées.
     *
     * @param coord1 Les coordonnées du premier point.
     * @param coord2 Les coordonnées du deuxième point.
     * @return La distance de Manhattan entre les deux points.
     */
    private static float calcDistManhattan(int[] coord1, int[] coord2) {
        return Math.abs(coord2[0] - coord1[0]) + Math.abs(coord2[1] - coord1[1]);
    }

}