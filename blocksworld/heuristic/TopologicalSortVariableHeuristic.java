package blocksworld.heuristic;

import java.util.Map;
import java.util.Set;

import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;
import blocksworld.planning.Goal;

/**
 * Cette classe représente une heuristique qui choisit la meilleure variable en fonction de l'ordre topologique,
 * en privilégiant les piles par rapport aux blocs.
 */
public class TopologicalSortVariableHeuristic implements VariableHeuristic{
    
    /** Variable */
    private Goal but;

    /** Constructeur */
    public TopologicalSortVariableHeuristic(Goal but) {
        this.but = but;
    }

    /**
     * La méthode best choisit la meilleure variable en fonction de l'ordre topologique,
     * en privilégiant les piles par rapport aux blocs.
     * 
     * @param variables L'ensemble des variables parmi lesquelles choisir la meilleure.
     * @param mapVarDom La carte associant chaque variable à son domaine.
     * @return La meilleure variable choisie en fonction de l'ordre topologique.
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> mapVarDom) {
        Map<Variable,Object> goalInst = but.getGoalInstance();
        
        int nearToZero = Integer.MAX_VALUE;
        Variable bestVariable = null;
        boolean isPile = false;

        for (Variable variable : variables) {
            if (variable instanceof OnB) {
                if (goalInst.containsKey(variable)) { //On fait cette condition si le goalInstance est seulement une instance partielle
                    int position = (Integer) goalInst.get(variable);

                    //On privéligie les piles au block
                    //Le cas1 où la meilleur variable est actuellement un block et que la variable qu'on découvre est une pile
                    boolean cas1 = !isPile && position < 0; 
                    //Le cas2 où bestVariable -> block et que la valeur de la variable actuelle qui est un block est plus proche de 0
                    boolean cas2 = !isPile && position < nearToZero;
                    //Le cas3 où bestVariable -> pile et que la valeur de la variable actuelle qui est une pile est plus proche de 0
                    boolean cas3 = isPile && position > nearToZero;

                    if (cas1 || cas2 || cas3) {
                        if (!isPile && position < 0) isPile = true;
                        bestVariable = variable;
                        nearToZero = position;
                    }

                } else { //Si on n'a plus de variable onB du Set<Variable> en parmètre dans goalInst, on choisis le reste
                    if (bestVariable == null) bestVariable = variable;
                }
            } 
        }
        return bestVariable;
        
    }

}

/*

Cas 1 : 
Le OnB de la variable est une pile

nearToZero = 3
isPile = false

-> Variable est block (2), on regarde si newBlock < 3, si oui :

nearToZero = 2
isPile = false

-> Variable est une pile (-3), si on avait un block



*/