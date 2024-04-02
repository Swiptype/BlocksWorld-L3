package blocksworld.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import blocksworld.generate.BWBasicActionGen;
import blocksworld.generate.InstanceTools;
import blocksworld.heuristic.*;
import blocksworld.modelling.Variable;
import blocksworld.planning.*;

public class DemoRandInst {
    
    public static void main(String[] args) {
        
        
        long seed = 10150;
        int nbBlock = 7;
        int nbPile = 5;

        /* Création d'une instance */
        Map<Variable, Object> initialInst = InstanceTools.getRandomInst(new Random(seed), nbBlock, nbPile);
        InstanceTools.sysoutMiniDetail(initialInst);

        /* Création du but */
        Map<Variable, Object> goalInst = InstanceTools.getRandomInst(new Random(seed), nbBlock-1, nbPile-1);
        InstanceTools.sysoutMiniDetail(goalInst);
        Goal goal = new BasicGoal(goalInst);

        /* Générateur d'action */
        BWBasicActionGen bwActionGen = new BWBasicActionGen(nbBlock, nbPile);

        /* Heuristic */
        Heuristic heuristic = new DistanceManhattanHeuristic(goal);
        //Heuristic heuristic = new DistEucliHeuristic(goal);

        /* Planner */
        Set<Action> actions = bwActionGen.getActions();
        //Planner planner = new BFSPlanner(initialInst, actions, goal); BFSPlanner.ACTIVATE_SONDE = true;
        //Planner planner = new DFSPlanner(goalInst, actions, goal);
        //Planner planner = new DijkstraPlanner(goalInst, actions, goal);
        Planner planner = new AStarPlanner(initialInst, actions, goal, heuristic);

        

        /* Le plan et le résultat d'action */
        List<Action> actionsPlan = planner.plan(); //System.out.println(actionsPlan);
        Map<Variable, Object> currentInst = new HashMap<>(initialInst);
        for (Action action : actionsPlan) {
            currentInst = action.successor(currentInst);
        }
        InstanceTools.sysoutDetail(currentInst);
        

    }

}
