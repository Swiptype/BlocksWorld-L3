package blocksworld.heuristic;

import java.util.List;
import java.util.Set;

import blocksworld.modelling.Variable;

/**
 * Interface représentant une heuristique de valeur pour le choix de l'ordre des valeurs d'une variable.
 */
public interface ValueHeuristic {
    
    /**
     * La méthode ordering attribue un ordre aux valeurs d'une variable en fonction de l'heuristique.
     *
     * @param var    La variable pour laquelle l'ordre des valeurs est déterminé.
     * @param domaine L'ensemble des valeurs possibles pour la variable.
     * @return Une liste ordonnée de valeurs en fonction de l'heuristique.
     */
    public List<Object> ordering(Variable var, Set<Object> domain);

}
