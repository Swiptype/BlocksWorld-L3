package blocksworld.heuristic;

import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Variable;

/**
 * Implémentation de l'heuristique basée sur la taille du domaine d'une variable.
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic{

    /** Variables */
    private boolean maxDom;

    /** Constructeur */
    public DomainSizeVariableHeuristic(boolean maxDom) {
        this.maxDom = maxDom;
    }

    /**
     * Sélectionne la meilleure variable en fonction de la taille de son domaine.
     *
     * @param variables La liste des variables parmi lesquelles choisir.
     * @param mapVarDom La carte associant chaque variable à son domaine.
     * @return La meilleure variable en fonction de la taille de son domaine.
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> mapVarDom) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Variable varMax = null;
        Variable varMin = null;
        for (Variable variable : variables) {
            int nbDom = variable.getDomain().size();
            if (nbDom < min) {
                varMin = variable;
                min = nbDom;
            }
            if (nbDom > max) {
                varMax = variable;
                max = nbDom;
            }
        }
        return (this.maxDom ? varMax: varMin);
    }
    
}
