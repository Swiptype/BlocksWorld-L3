package blocksworld.cp;

import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

/**
 * La classe <br>AbstractSolver</br> permet de factoriser certaines méthodes nécessaires aux solvers.
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public abstract class AbstractSolver implements Solver {
    
    /** Variables */
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    /** Constructeur */
    /**
     * Le constructeur <br>AbstractSolver</br> prend l'ensemble de variables et l'ensemble de contraintes impliqués
     * @param Set<Variable> variables
     * @param Set<Constraint> constraints
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    /* Redefinition de la méthode solve */
    public abstract Map<Variable, Object> solve();

    /**
     * La méthode isConsistent(Map<Variable, Object>) permet de savoir si l'instanciation partielle est satisfait par les différentes contraintes
     * @param Map<Variable, Object> partInstantiation
     * @return boolean, True si toutes les contraintes sont satisfaites
     */
    public boolean isConsistent(Map<Variable, Object> partInstantiation) {
        for (Constraint constraint : constraints) {
            if (partInstantiation.keySet().containsAll(constraint.getScope())) {
                if (!constraint.isSatisfiedBy(partInstantiation)) {
                    return false;
                } 
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
