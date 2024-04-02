package blocksworld.heuristic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Variable;
import blocksworld.planning.Goal;

/**
 * Cette classe représente une heuristique qui ordonne les valeurs du domaine d'une variable en fonction de leur similitude
 * avec la configuration finale définie par l'objectif (Goal).
 */
public class SimilarToGoalValueHeuristic implements ValueHeuristic {

    /** Variable */
    private Goal but;

    /** Constructeur */
    public SimilarToGoalValueHeuristic(Goal but) {
        this.but = but;
    }

    /**
     * La méthode ordering organise les valeurs du domaine de la variable en fonction de leur similitude avec la configuration
     * finale définie par l'objectif (Goal).
     * 
     * @param variable La variable pour laquelle on veut ordonner les valeurs.
     * @param domaine L'ensemble des valeurs du domaine de la variable.
     * @return Une liste de valeurs du domaine organisée en fonction de leur similitude avec l'objectif.
     */
    @Override
    public List<Object> ordering(Variable var, Set<Object> domain) {
        List<Object> sortedDomain = new ArrayList<>();

        Set<Object> unvisited = new HashSet<>(domain);

        List<Object> lastChoice = new ArrayList<>();
        
        for (Map.Entry<Variable,Object> entry : but.getGoalInstance().entrySet()) {
            Variable variable = entry.getKey();
            Object obj = entry.getValue();
            
            if (variable.equals(var)) sortedDomain.add(obj);
            else lastChoice.add(obj);

            if (unvisited.contains(obj)) unvisited.remove(obj);
        }

        sortedDomain.addAll(unvisited);
        sortedDomain.addAll(lastChoice);

        return sortedDomain;
    }
    
}
