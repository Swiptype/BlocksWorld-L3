package blocksworld.cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import blocksworld.heuristic.ValueHeuristic;
import blocksworld.heuristic.VariableHeuristic;
import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

/**
 * La classe <br>HeuristicMACSolver</br> permet de recréer des instances à partir d'un ensemble de contraintes 
 * et de variables à l'aide de VariableHeuristic et de ValueHeuristic.
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class HeuristicMACSolver extends AbstractSolver {
    
    /* Variables */
    protected ArcConsistency arcConsistency;
    protected VariableHeuristic varHeuristic;
    protected ValueHeuristic valHeuristic;
    
    /* Constructeur */
    /**
     * Le constructeur <br>HeuristicMACSolver</br> prend un ensemble de variables, un ensemble de contraintes impliqués, une classe VariableHeuristic et une classe ValueHeuristic.
     * @param Set<Variable> variables
     * @param Set<Constraint> constraints
     * @param VariableHeuristic varHeuristic
     * @param ValueHeuristic valHeuristic
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic varHeuristic, ValueHeuristic valHeuristic) {
        super(variables, constraints);
        this.varHeuristic = varHeuristic;
        this.valHeuristic = valHeuristic;
        this.arcConsistency = new ArcConsistency(constraints);
    }

    /**
     * La méthode solve() permet de recréer des instances à partir d'un ensemble de contraintes et de variables
     * à l'aide d'autre classe. VariableHeuristique choisit la variable la plus importante à faire et ValueHeuristic
     */
    public Map<Variable, Object> solve() {
        //L'instance tempon que l'on remplit
        Map<Variable, Object> inst = new HashMap<Variable, Object>();
        
        //varNoInst nous permet de parcourir les variables dans un certain ordre
        LinkedList<Variable> varNoInst = new LinkedList<Variable>(this.variables);
        
        //eD permet de savoir les valeurs de domaine déjà mises dans la nouvelle isntance
        Map<Variable, Set<Object>> eD = new HashMap<Variable, Set<Object>>();
        for (Variable variable : this.variables) {
            eD.put(variable, variable.getDomain());
        }
        
        //Cette instance est l'instance qui a pu être recréée à partir descontraintes et des variables 
        Map<Variable, Object> newInst = this.solveEX(inst, varNoInst, eD);
        return newInst;
    }

    /**
     * La méthode solveEX(Map<Variable, Object>, LinkedList<Variable>) n'est qu'une méthode annexe qui permet de faire la méthode solve().
     * @param Map<Variable,Object> partInst
     * @param LinkedList<Variable> varNoInst
     * @param Set<Object>> eD
     * @return Map<Variable, Object> est l'instance qui a été créée.
     */
    private Map<Variable, Object> solveEX(Map<Variable, Object> partInst, LinkedList<Variable> varNoInst, Map<Variable, Set<Object>> eD) {  
        //Si il n'y a plus de variables à parcourir, il ne reste plus qu'à retourner l'instance créée. 
        if (varNoInst.isEmpty()) {
            return partInst;
        }
        
        //On vérifie si des domaines sont viables
        if (!this.arcConsistency.ac1(eD)) {
            return null;
        }

        //On récupére une instance à parcourir
        Variable var = varNoInst.getFirst();
        varNoInst.removeFirst();

        //On parcours toutes les valeurs du domaine pour voir si une est viable
        for (Object obj : eD.get(var)) {

            //On le vérifie dans une nouvelle instance
            Map<Variable, Object> newInst = new HashMap<Variable, Object>(partInst);
            newInst.put(var,obj);

            //Si oui, il faut continuer à créer l'instance avec une autre variable
            if (this.isConsistent(newInst)) {

                //Si on récupère une instance, on la renvoie
                Map<Variable, Object> newInst2 = this.solveEX(newInst, varNoInst, eD);
                if (newInst2 != null) {
                    return newInst2;
                }
            }    
        }
        varNoInst.add(var);
        return null;
    }
    
}
