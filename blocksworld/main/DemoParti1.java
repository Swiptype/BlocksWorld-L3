package blocksworld.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.generate.BWBasicActionGen;
import blocksworld.generate.BWBasicConstraintGenerate;
import blocksworld.generate.BWConstraintGen;
import blocksworld.generate.BWOnBInfGen;
import blocksworld.generate.BWPasGen;
import blocksworld.generate.BWVarGenerate;
import blocksworld.modelling.Constraint;
import blocksworld.modelling.FixedB;
import blocksworld.modelling.FreeP;
import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;

public class DemoParti1 {

    public static void testInstConstraint(Map<Variable,Object> inst, Set<Constraint> constraints, boolean seePassed) {
        Set<Constraint> constraintsFailed = new HashSet<>();
        Boolean fail = false;
        for (Constraint constraint : constraints) {
            if (constraint.isSatisfiedBy(inst)) {
                if (seePassed) {
                   System.out.print(constraint);
                    System.out.println(" has passed !"); 
                }
            } else {
                fail = true;
                constraintsFailed.add(constraint);
            }
        }
        if (fail) {
            for (Constraint constraint : constraintsFailed) {
                System.out.print(constraint);
                System.out.println(" hasn't pass !");
            }
        }
        if (!seePassed && !fail) {
            System.out.println("There is no constraint that has failed !");
        }
    }

    public static void main(String[] args) {

        /* Exercice 5. 
        Dans une classe exécutable, créer un petit exemple, quelques configurations, et afficher pour
        chacune un message indiquant si elle satisfait toutes les contraintes d’une configuration régulière et/ou
        croissante. Faire de même une démonstration des autres contraintes implémentées, s’il y en a.
        */
        
        /*
        B4
        B2  B3
        B0  B1
        ------
        P1  P2
        */

        Set<Object> domainBool = new HashSet<>(Set.of(true,false));
        Set<Object> domainPileBlock = new HashSet<>(Set.of(-2,-1,0,1,2,3,4)); 

        /* Block 0 */
        int block0 = 0;
        Variable fixedB0 = new FixedB(block0, domainBool);
        Variable OnB0 = new OnB(block0, domainPileBlock);

        /* Block 1 */
        int block1 = 1;
        Variable fixedB1 = new FixedB(block1, domainBool);
        Variable OnB1 = new OnB(block1, domainPileBlock);

        /* Block 2 */
        int block2 = 2;
        Variable fixedB2 = new FixedB(block2, domainBool);
        Variable OnB2 = new OnB(block2, domainPileBlock);

        /* Block 3 */
        int block3 = 3;
        Variable fixedB3 = new FixedB(block3, domainBool);
        Variable OnB3 = new OnB(block3, domainPileBlock);

        /* Block 4 */
        int block4 = 4;
        Variable fixedB4 = new FixedB(block4, domainBool);
        Variable OnB4 = new OnB(block4, domainPileBlock);

        /* Pile 1 */
        int pile1 = -1;
        Variable freeP1 = new FreeP(pile1, domainBool);

        /* Pile 2 */
        int pile2 = -2;
        Variable freeP2 = new FreeP(pile2, domainBool);

        Map<Variable,Object> inst = new HashMap<>();

        inst.put(OnB0, -1); //Je mets le block 0 sur la pile 1
        inst.put(freeP1, false); // Comme j'ai mis le block 0 sur la pile 1, la pile 1 n'est plus libre

        inst.put(OnB1, -2); //Je mets le block 0 sur la pile 1
        inst.put(freeP2, false); // Comme j'ai mis le block 1 sur la pile 2, la pile 2 n'est plus libre

        inst.put(OnB2, 0); //Je mets le block 2 sur le block 0
        inst.put(fixedB0, true); // Comme j'ai mis le block 2 sur le block 0, le block 0 est maintenant fixe

        inst.put(OnB3, 1); //Je mets le block 3 sur le block 1
        inst.put(fixedB1, true); // Comme j'ai mis le block 3 sur le block 1, le block 1 est maintenant fixe

        inst.put(OnB4, 2); //Je mets le block 4 sur le block 2
        inst.put(fixedB2, true); // Comme j'ai mis le block 4 sur le block 2, le block 2 est maintenant fixe

        /* Il reste les blocks qui n'ont rien au dessus d'eux */
        inst.put(fixedB3, false);
        inst.put(fixedB4, false);

        //-----------------------------------------------------------------------------------------------------------

        /* Passons aux contraintes */
        int nbBlock = 5;
        int nbPile = 2;

        /* Exercice 1 
        Créer une classe que l’on pourra instancier en spécifiant un nombre de blocs et un nombre
        de piles, et à laquelle on pourra demander l’ensemble de variables correspondant.   
        */
        if (true) {
            BWVarGenerate bwVarGenerate = new BWVarGenerate(nbBlock, nbPile);
            System.out.println(bwVarGenerate.getVariables()); 
        }

        /* Exercice 2 
        Implémenter le monde des blocs avec la modélisation ci-dessus, en créant une nouvelle
        classe que l’on pourra instancier avec un nombre de blocs et un nombre de piles, et à laquelle on pourra
        demander l’ensemble de toutes les contraintes spécifiées ci-dessus.
        */

        /* On va maintenant générer toutes les contraintes basiques et on va les tester */
        BWConstraintGen generateurConstraintBasic = new BWBasicConstraintGenerate(nbBlock, nbPile);
        Set<Constraint> constraintsBasic = generateurConstraintBasic.getConstraintsForInstance(inst);
        testInstConstraint(inst, constraintsBasic, true);

        /* Exercice 4. 
        Proposer un ensemble de contraintes permettant d’assurer qu’une configuration est croissante. 
        Créer une classe permettant d’obtenir l’ensemble de ces contraintes, sur le même modèle que pour
        l’exercice 3.
         */

        /* On va maintenant générer toutes les contraintes croissantes et on va les tester */
        BWConstraintGen bwOnBInfGen = new BWOnBInfGen(nbBlock, nbPile);
        Set<Constraint> constraintsOnBInf = bwOnBInfGen.getConstraintsForInstance(inst);
        testInstConstraint(inst, constraintsOnBInf, true);

        /* Exercice 3. 
        Proposer un ensemble de contraintes de type Implication, permettant d’assurer qu’une
        configuration est régulière. Créer une classe avec une méthode permettant d’obtenir l’ensemble de ces
        contraintes pour un monde des blocs donné, tel que défini par une instance de la classe de l’exercice 2.
        */

        /* On va maintenant générer toutes les contraintes avec un pas et on va les tester */
        BWConstraintGen bwPasGen = new BWPasGen(nbBlock, nbPile);
        Set<Constraint> constraintsPas = bwPasGen.getConstraintsForInstance(inst);
        testInstConstraint(inst, constraintsPas, true);

        BWBasicActionGen basicActionGen = new BWBasicActionGen(nbBlock, nbPile);
        basicActionGen.getActions();

        
        

    }
    
}
