package blocksworld.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blocksworld.generate.BWBasicActionGen;
import blocksworld.generate.BWVarGenerate;
import blocksworld.generate.InstanceTools;
import blocksworld.heuristic.DistEucliHeuristic;
import blocksworld.heuristic.Heuristic;
import blocksworld.modelling.*;
import blocksworld.planning.*;

public class DemoParti2 {

    public static void sysoutTestAction(Planner planner, Sonde sonde, Map<Variable, Object> initialInst) {
        TimeTaken timer = new TimeTaken();
        timer.start();
        List<Action> actionsPlan = planner.plan();
        System.out.println(actionsPlan);
        Map<Variable, Object> currentInst = new HashMap<>(initialInst);
        for (Action action : actionsPlan) {
            currentInst = action.successor(currentInst);
        }
        InstanceTools.sysoutDetail(currentInst);
        timer.end();
        System.out.println(planner.getClass() + " has taken " + timer.getSecondTaken() + "s ! Il y a eu " + sonde.getCount() + " noeuds ! \n");
    }

    public static void main(String[] args) {
        int nbBlock = 6;
        int nbPile = 5;
        BWVarGenerate bwGen = new BWVarGenerate(nbBlock, nbPile);

        /*     
        P1 | B0 B1 
        P2 | B2 B5
        P3 | B4 B7
        P4 | 
        P5 | B3 B6  */

        Map<Variable, Object> partielInstP1 = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwGen, -1, List.of(0,1));
        Map<Variable, Object> partielInstP2 = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwGen, -2, List.of(2));
        Map<Variable, Object> partielInstP3 = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwGen, -3, List.of());
        Map<Variable, Object> partielInstP4 = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwGen, -4, List.of());
        Map<Variable, Object> partielInstP5 = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwGen, -5, List.of(3,4,5));

        Map<Variable, Object> initialInst = new HashMap<>(); 
        initialInst.putAll(partielInstP1);
        initialInst.putAll(partielInstP2);
        initialInst.putAll(partielInstP3);
        initialInst.putAll(partielInstP4);
        initialInst.putAll(partielInstP5);
        System.out.print("Initial Instance :");
        InstanceTools.sysoutMiniDetail(initialInst);

        /*
        P4 | B1 B2 B3  */

        /* Le but */
        Map<Variable, Object> goalInst = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwGen, -1, List.of(0,1,2,3));
        System.out.println("Goal Instance :");
        System.out.print(goalInst);
        InstanceTools.sysoutMiniDetail(goalInst);
        Goal goal = new BasicGoal(goalInst);

        /* Générez action */
        BWBasicActionGen actionGen = new BWBasicActionGen(nbBlock, nbPile);

        /* La heuristique pour trouver plus rapidement */
        Heuristic euclidHeuristic = new DistEucliHeuristic(goal);

        Sonde sonde = new Sonde();

        /* Trouver le plan d'action */
        Planner aStarPlanner = new AStarPlanner(initialInst, actionGen.getActions(), goal, euclidHeuristic, sonde);
        sysoutTestAction(aStarPlanner, sonde, initialInst);

        sonde.reset();

        Planner bfsPlanner = new BFSPlanner(initialInst, actionGen.getActions(), goal, sonde);
        sysoutTestAction(bfsPlanner, sonde, initialInst);

        sonde.reset();

        /*Planner dfsPlanner = new DFSPlanner(initialInst, actionGen.getActions(), goal, sonde);
        sysoutTestAction(dfsPlanner, sonde, initialInst);

        sonde.reset();*/

        Planner dijkstraPlanner = new DijkstraPlanner(initialInst, actionGen.getActions(), goal, sonde);
        sysoutTestAction(dijkstraPlanner, sonde, initialInst);

        sonde.reset();

        
    }
    
}
