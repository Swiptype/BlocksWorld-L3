package blocksworld.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

import blocksworld.generate.BWBasicActionGen;
import blocksworld.generate.InstanceTools;
import blocksworld.heuristic.DistanceManhattanHeuristic;
import blocksworld.heuristic.Heuristic;
import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;
import blocksworld.planning.AStarPlanner;
import blocksworld.planning.Action;
import blocksworld.planning.BasicGoal;
import blocksworld.planning.Goal;
import blocksworld.planning.Planner;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;

public class DemoLibrairie {
    
    public static void showInstJFrame(Map<Variable, Object> instanciation, int nbBlock) {
        // Building state
        BWState<Integer> state = makeBWState(instanciation);
        // Displaying
        BWIntegerGUI gui = new BWIntegerGUI(nbBlock);
        JFrame frame = new JFrame("Blocksworld");
        frame.add(gui.getComponent(state));
        frame.pack();
        frame.setVisible(true);
    }

    public static BWState<Integer> makeBWState(Map<Variable,Object> instanciation) {
        int nbBlock = 0;
        for (Variable variable : instanciation.keySet()) {
            if (variable instanceof OnB) nbBlock++;
        }
        // Building state
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbBlock);
        for (int b = 0; b < nbBlock; b++) {
            Variable onB = new OnB(b); // get instance of Variable for "on_b"
            int under = (int) instanciation.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        BWState<Integer> state = builder.getState();
        return state;
    }

    public static void main(String[] args) {
        
        long seed = 10150;
        int nbBlock = 5;
        int nbPile = 3;

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
        List<Action> actionsPlan = planner.plan(); System.out.println(actionsPlan);
        
        //showInstJFrame(initialInst, nbBlock);
        
        BWIntegerGUI gui = new BWIntegerGUI(nbBlock);
        JFrame frame = new JFrame("blocksworld");
        BWState<Integer> bwState = makeBWState(initialInst);
        BWComponent<Integer> component = gui.getComponent(bwState);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);
        // Playing plan
        Map<Variable, Object> state = new HashMap<>(initialInst);
        for (Action a: actionsPlan) {
            try { Thread.sleep(1_000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            state=a.successor(state);
            component.setState(makeBWState(state));
        }
        System.out.println("Simulation of plan: done.");

    }
}
