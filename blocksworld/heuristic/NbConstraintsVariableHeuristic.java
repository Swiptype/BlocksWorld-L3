package blocksworld.heuristic;

import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

/**
 * Cette classe implémente une heuristique basée sur le nombre de contraintes affectant une variable.
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    private Set<Constraint> constraints;
    private boolean favConstraint;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean favConstraint) {
        this.constraints = constraints;
        this.favConstraint = favConstraint;
    }
    
    /**
     * Cette méthode sélectionne la meilleure variable parmi un ensemble, en se basant sur le nombre de contraintes qui affectent chaque variable.
     * 
     * @param variables Un ensemble de variables parmi lesquelles choisir.
     * @param mapVarDom Une carte associant chaque variable à son domaine.
     * @return La meilleure variable sélectionnée selon le critère défini par l'heuristique.
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> mapVarDom) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Variable varMax = null;
        Variable varMin = null;
        for (Variable variable : variables) {
            int cptConstraint = 0;
            for (Constraint constraint : this.constraints) {
                if (constraint.getScope().contains((Object) variable)) {
                    cptConstraint++;
                }
            }
            if (cptConstraint < min) {
                varMin = variable;
                min = cptConstraint;
            }
            if (cptConstraint > max) {
                varMax = variable;
                max = cptConstraint;
            }
        }
        return (this.favConstraint ? varMax : varMin);
    }
    
}
