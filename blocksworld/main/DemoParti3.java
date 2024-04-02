package blocksworld.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import blocksworld.cp.BacktrackSolver;
import blocksworld.cp.HeuristicMACSolver;
import blocksworld.cp.MACSolver;
import blocksworld.cp.Solver;
import blocksworld.generate.*;
import blocksworld.heuristic.*;
import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

public class DemoParti3 {

    public static boolean SHOW_INSTANCE = true;

    public static void sysoutTestSolver(Solver solver) {
        System.out.print("Instance créée par " + solver + " ! ");
        TimeTaken timer = new TimeTaken();
        timer.start();
        Map<Variable, Object> solverInst = solver.solve();
        timer.end();
        System.out.print("Resolved in " + timer.getSecondTaken() + (SHOW_INSTANCE ? "" : "\n"));
        if (SHOW_INSTANCE) {
            if (solverInst != null) {
                InstanceTools.sysoutMiniDetail(solverInst);
            } else {
                System.out.println();
                System.out.println("L'instance est null donc n'a pas trouvé !");
            }
        } 
    }

    public static void sysoutTestAllSolver(Set<Variable> variables, Set<Constraint> constraints, Random random) {
        /* Test du Solver BacktrackSolver */
        Solver btSolver_Basic = new BacktrackSolver(variables, constraints);
        sysoutTestSolver(btSolver_Basic);

        /* Test du Solver MACSolver */
        Solver macSolver_Basic = new MACSolver(variables, constraints);
        sysoutTestSolver(macSolver_Basic);
        
        /* Test du Solver HeuristicMACSolver */
        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(false);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(random);
        Solver heurisMACSolver_Basic = new HeuristicMACSolver(variables, constraints, variableHeuristic, valueHeuristic);
        sysoutTestSolver(heurisMACSolver_Basic);
    }
    
    public static void main(String[] args) {

        //Avec random, on peut faire du semi-aléatoire
        int seed = 10394; 
        Random random = new Random(seed);

        
        SHOW_INSTANCE = true;
        
        /* Exercice 9. 
        Dans une classe exécutable, créer au moins une instance du monde des blocs (en spécifiant le
        nombre de blocs et le nombre de piles), en utilisant les contraintes demandant une configuration régulière,
        et lancer tous les solveurs de contraintes implémentés en affichant leur temps de calcul et la solution
        trouvée (s’il en existe une).
        */
        /*


        Test des solvers sur des contraintes basiques
        
        
        */
        int nbBlock_Basic = 5; int nbPile_Basic = 3; 
        BWVarGenerate bwVarGen_Basic = new BWVarGenerate(nbBlock_Basic, nbPile_Basic);
        Set<Variable> variables_Basic = bwVarGen_Basic.getVariables();

        Map<Variable, Object> inst = InstanceTools.getRandomInst(random, nbBlock_Basic, nbPile_Basic);
        System.out.print("\nBasic Constraint -> Instance de base :");
        InstanceTools.sysoutMiniDetail(inst);

        BWConstraintGen bwBasicConstGen_Basic = new BWBasicConstraintGenerate(nbBlock_Basic, nbPile_Basic);
        Set<Constraint> constraints_Basic = bwBasicConstGen_Basic.getConstraintsForInstance(inst);

        sysoutTestAllSolver(variables_Basic, constraints_Basic, random);

        /*


        Test des solvers sur des contraintes croissantes


        */
        int nbBlock_Croissant = 6; int nbPile_Croissant = 5;
        BWVarGenerate bwVarGen_Croissant = new BWVarGenerate(nbBlock_Croissant, nbPile_Croissant);

        Map<Variable, Object> partInstP1_Croissant = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croissant, -1, List.of(0,2,4));
        Map<Variable, Object> partInstP2_Croissant = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croissant, -2, List.of());
        Map<Variable, Object> partInstP3_Croissant = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croissant, -3, List.of(1,5));
        Map<Variable, Object> partInstP4_Croissant = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croissant, -4, List.of(3));
        Map<Variable, Object> partInstP5_Croissant = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croissant, -5, List.of());

        Map<Variable, Object> croissantInst = new HashMap<>(); 
        croissantInst.putAll(partInstP1_Croissant);
        croissantInst.putAll(partInstP2_Croissant);
        croissantInst.putAll(partInstP3_Croissant);
        croissantInst.putAll(partInstP4_Croissant);
        croissantInst.putAll(partInstP5_Croissant);
        System.out.print("\nCroissant Constraint -> Instance de base :");
        InstanceTools.sysoutMiniDetail(croissantInst);

        BWConstraintGen bwBasicConstGen_Croissant = new BWBasicConstraintGenerate(nbBlock_Croissant, nbPile_Croissant);
        BWConstraintGen bwPasConstGen_Croissant = new BWOnBInfGen(nbBlock_Croissant, nbPile_Croissant, bwBasicConstGen_Croissant);

        Set<Variable> variables_Croissant = bwVarGen_Croissant.getVariables();
        Set<Constraint> constraints_Croissant = bwPasConstGen_Croissant.getConstraintsForInstance(croissantInst);

        sysoutTestAllSolver(variables_Croissant, constraints_Croissant, random);


        /*


        Test des solvers sur des contraintes de pas


        */
        int nbBlock_Pas = 6; int nbPile_Pas = 5;
        BWVarGenerate bwVarGen_Pas = new BWVarGenerate(nbBlock_Pas, nbPile_Pas);

        Map<Variable, Object> partInstP1_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Pas, -1, List.of(4,2,0));
        Map<Variable, Object> partInstP2_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Pas, -2, List.of());
        Map<Variable, Object> partInstP3_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Pas, -3, List.of(1,5));
        Map<Variable, Object> partInstP4_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Pas, -4, List.of(3));
        Map<Variable, Object> partInstP5_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Pas, -5, List.of());

        Map<Variable, Object> pasInst = new HashMap<>(); 
        pasInst.putAll(partInstP1_Pas);
        pasInst.putAll(partInstP2_Pas);
        pasInst.putAll(partInstP3_Pas);
        pasInst.putAll(partInstP4_Pas);
        pasInst.putAll(partInstP5_Pas);
        System.out.print("`\nPas Constraint -> Instance de base :");
        InstanceTools.sysoutMiniDetail(pasInst);

        BWConstraintGen bwBasicConstGen_Pas = new BWBasicConstraintGenerate(nbBlock_Pas, nbPile_Pas);
        BWConstraintGen bwPasConstGen_Pas = new BWPasGen(nbBlock_Pas, nbPile_Pas);

        Set<Variable> variables_Pas = bwVarGen_Pas.getVariables();
        Set<Constraint> constraints_Pas = bwPasConstGen_Pas.getConstraints();
        constraints_Pas.addAll(bwBasicConstGen_Pas.getConstraintsForInstance(pasInst));

        sysoutTestAllSolver(variables_Pas, constraints_Pas, random);

        /* Exercice 10. 
        Même exercice avec les contraintes demandant une configuration croissante, puis avec
        celles demandant une configuration régulière et croissante, puis avec d’autres combinaisons exploitant les
        contraintes additionnelles implémentées dans la partie 1, s’il y en a.
        */
        /*


        Test des solvers sur des contraintes de pas et de contraintes de croissance


        */

        int nbBlock_Croi_Pas = 6; int nbPile_Croi_Pas = 5;
        BWVarGenerate bwVarGen_Croi_Pas = new BWVarGenerate(nbBlock_Croi_Pas, nbPile_Croi_Pas);

        Map<Variable, Object> partInstP1_Croi_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croi_Pas, -1, List.of(0,2,4));
        Map<Variable, Object> partInstP2_Croi_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croi_Pas, -2, List.of());
        Map<Variable, Object> partInstP3_Croi_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croi_Pas, -3, List.of(1,5));
        Map<Variable, Object> partInstP4_Croi_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croi_Pas, -4, List.of(3));
        Map<Variable, Object> partInstP5_Croi_Pas = InstanceTools.getPartialInstPileBlockWithBWVarGen(bwVarGen_Croi_Pas, -5, List.of());
    
        Map<Variable, Object> croiPasInst = new HashMap<>(); 
        croiPasInst.putAll(partInstP1_Croi_Pas);
        croiPasInst.putAll(partInstP2_Croi_Pas);
        croiPasInst.putAll(partInstP3_Croi_Pas);
        croiPasInst.putAll(partInstP4_Croi_Pas);
        croiPasInst.putAll(partInstP5_Croi_Pas);
        System.out.print("\nPas et Croissance Constraint -> Instance de base :");
        InstanceTools.sysoutMiniDetail(croiPasInst);

        BWConstraintGen bwBasicConstGen_Croi_Pas = new BWBasicConstraintGenerate(nbBlock_Croi_Pas, nbPile_Croi_Pas);
        BWConstraintGen bwPasConstGen_Croi_Pas = new BWPasGen(nbBlock_Croi_Pas, nbPile_Croi_Pas);
        BWConstraintGen bwOnBInFConstGen_Croissant = new BWOnBInfGen(nbBlock_Croissant, nbPile_Croissant, bwPasConstGen_Croi_Pas);

        Set<Variable> variables_Croi_Pas = bwVarGen_Croi_Pas.getVariables();
        Set<Constraint> constraints_Croi_Pas = bwOnBInFConstGen_Croissant.getConstraints();
        constraints_Croi_Pas.addAll(bwBasicConstGen_Croi_Pas.getConstraintsForInstance(croiPasInst));

        sysoutTestAllSolver(variables_Croi_Pas, constraints_Croi_Pas, random);
    }

}
