package blocksworld.heuristic;

import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Variable;

/**
 * Interface représentant une heuristique de variable pour le choix de la meilleure variable.
 */
public interface VariableHeuristic {

    /**
     * La méthode best sélectionne la meilleure variable parmi un ensemble de variables en fonction de l'heuristique.
     *
     * @param variables  L'ensemble de variables parmi lesquelles choisir.
     * @param mapVarDom  Une carte associant chaque variable à son domaine.
     * @return La meilleure variable choisie selon l'heuristique.
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> mapVarDom);

}