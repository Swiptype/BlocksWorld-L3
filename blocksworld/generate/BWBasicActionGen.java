package blocksworld.generate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.FixedB;
import blocksworld.modelling.FreeP;
import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;
import blocksworld.planning.Action;
import blocksworld.planning.BasicAction;

/**
 * La classe BWBasicActionGen génère des actions de base pour le problème Blocksworld.
 * Elle crée des ensembles d'actions représentant des déplacements de blocs et des translations de piles.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BWBasicActionGen {
    
    /** Variable */
    protected int nbBlock;
    protected int nbPile;

    /** Constructeur */
    /**
     * Constructeur de la classe BWBasicActionGen.
     * 
     * @param nbBlock Le nombre de blocs dans le problème.
     * @param nbPile  Le nombre de piles dans le problème.
     */
    public BWBasicActionGen(int nbBlock, int nbPile) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;
    }

    /**
     * Génère un ensemble d'actions représentant des déplacements de blocs et des translations de piles.
     * 
     * @return Un ensemble d'actions générées.
     */
    public Set<Action> getActions() {
        Set<Action> actions = new HashSet<>();

        for (int a = 0; a < this.nbBlock; a++) { //Le block a à déplacer
            Variable fixedB_A = new FixedB(a);
            Variable onB_A = new OnB(a);
            for (int b = this.nbPile*-1; b < this.nbBlock; b++) { //Le block a est sous la pile ou le block b
                if (a != b) {
                    for (int c = this.nbPile*-1; c < this.nbBlock; c++) { //Le block a va sous la pile ou le block b
                        if (b != c && a != c) {

                            /* 
                            ACT(PUTA,B,C )
                                > PRE := ¬FIXED_A ∧ (ON_A = B) ∧ ¬FIXED_C
                                > EFF := ¬(ON_A = B) ∧ ON_A = C ∧ ¬FIXED_B ∧ FIXED_C
                            */

                            /* Création de la précondition */
                            Map<Variable,Object> precondition = new HashMap<>();
                            
                            precondition.put(fixedB_A, false); // Le block a est celui que l'on déplace donc il ne doit pas être fixe

                            precondition.put(onB_A, b); //Le block a doit être sur la variable b

                            // La condition varie en fonction de si c est une pile ou un block
                            if (c < 0) {
                                //si c est une pile
                                Variable freeP_C = new FreeP(c); 
                                precondition.put(freeP_C, true); //la pile c doit être libre pour accueillir le block a
                            } else {
                                Variable fixedB_C = new FixedB(c);
                                precondition.put(fixedB_C, false); //le block c ne doit pas être fixe (sinon implique que le block à déjà un block au dessus) pour accueillir le block a
                            }

                            /* Création effet */
                            Map<Variable,Object> effect = new HashMap<>();

                            
                            effect.put(onB_A, c); // on met le block a sur la variable c

                            // La condition varie en fonction de si la variable b est une pile ou un block
                            if (b < 0) {
                                //si b est une pile
                                Variable freeP_B = new FreeP(b); 
                                effect.put(freeP_B, true); //la pile b doit être libre pour accueillir le block a
                            } else {
                                Variable fixedB_B = new FixedB(b);
                                effect.put(fixedB_B, false); //le block b ne doit pas être fixe (sinon implique que le block à déjà un block au dessus) pour accueillir le block a
                            }

                            // La condition varie en fonction de si la variable c est une pile ou un block
                            if (c < 0) {
                                //si la variable c est une pile
                                Variable freeP_C = new FreeP(c); 
                                effect.put(freeP_C, false); //la pile c doit être libre pour accueillir le block a
                            } else {
                                Variable fixedB_C = new FixedB(c);
                                effect.put(fixedB_C, true); //le block c ne doit pas être fixe (sinon implique que le block à déjà un block au dessus) pour accueillir le block a
                            }

                            if(precondition.size() != 3 && effect.size() != 3) {
                                System.out.println("[Block A:" + a  + ", " + (b < 0 ? "Pile B:" : "Block B:") + b +", " + (c < 0 ? "Pile C:" : "Block C:") + c + "]");
                                System.out.println(precondition);
                                System.out.println(effect);
                                System.out.println();
                            } 
                            

                            /* Création ou ajout de l'action */
                            Action puActionABC = new BasicAction(precondition, effect);
                            actions.add(puActionABC);
                        }
                    }
                }
            }
        }
        for (int i = this.nbPile*-1; i < 0; i++) {
            FreeP freePi = new FreeP(i);
            for (int j = this.nbPile*-1; j < 0; j++) {
                for (int a = 0; a < this.nbBlock; a++) {
                    if (i != j) {
                        /*
                        ACT(TRANSLATEP,P′ )
                            I PRE := ¬FREEP ∧ FREEP′
                            I EFF := FREEP ∧ ¬FREEP′ ∧ ∀A∈P t. q. ONA = P : ¬(ONA = P) ∧ ONA = P′
                        */
                        FreeP freePj = new FreeP(i);

                        /* Création de la précondition */
                        Map<Variable,Object> precondition = new HashMap<>();
                        precondition.put(freePi, false);
                        precondition.put(freePj, true);
                        precondition.put(new OnB(a), i);

                        /* Création effet */
                        Map<Variable,Object> effect = new HashMap<>();
                        effect.put(freePi, true);
                        effect.put(freePj, false);
                        effect.put(new OnB(a), j);
                        


                        /* Création ou ajout de l'action */
                        Action puActionABC = new BasicAction(precondition, effect);
                        actions.add(puActionABC);
                    }
                }
            }
        }
        //System.out.println("Nombre Action : " + actions.size());
        return actions;
    }

    /**
     * Génère un ensemble d'actions pour une instance donnée représentant des déplacements de blocs et des translations de piles.
     * 
     * @param inst L'instance spécifique pour laquelle générer les actions.
     * @return Un ensemble d'actions générées pour l'instance donnée.
     */
    public Set<Action> getActionsForInstance(Map<Variable,Object> inst) {
        Set<Action> actions = new HashSet<>();

        Set<Integer> moveableBlocks = new HashSet<>(); //Tous les blocks que l'on peut déplacer
        Set<Integer> destinations = new HashSet<>(); // Tous les endroits où peut aller un block

        for (int i = this.nbPile*-1; i < this.nbBlock; i++) {
            if (i < 0) {
                /* On vérifie si elle est libre, elle pourra être une destination */
                int pile = i;
                
                Variable freeP = new FreeP(pile);
                if (inst.get(freeP) instanceof Boolean && (Boolean) inst.get(freeP)) {
                    destinations.add(pile);
                }

            } else {
                /* On vérifie si le block peut être une destination et un block déplaçable */
                int block = i;

                Variable fixedB = new FixedB(block);
                if (inst.get(fixedB) instanceof Boolean && !(Boolean) inst.get(fixedB)) {
                    moveableBlocks.add(block);
                    destinations.add(block);
                }

            }
        }

        for (Integer a : moveableBlocks) {
            Variable onB_A = new OnB(a);
            int b = (Integer) inst.get(onB_A);
            for (Integer c  : destinations) {
                if (a != c && b != c) {
                    /* 
                    ACT(PUTA,B,C )
                    > PRE := ¬FIXED_A ∧ (ON_A = B) ∧ ¬FIXED_C
                    > EFF := ¬(ON_A = B) ∧ ON_A = C ∧ ¬FIXED_B ∧ FIXED_C
                    */

                    /* Création de la précondition */
                    Map<Variable,Object> precondition = new HashMap<>();
                                    
                    Variable fixedB_A = new FixedB(a);
                    precondition.put(fixedB_A, false); // Le block a est celui que l'on déplace donc il ne doit pas être fixe

                    //Le OnB de a existe déjà
                    precondition.put(onB_A, b); //Le block a doit être sur la variable b

                    // La condition varie en fonction de si c est une pile ou un block
                    if (c < 0) {
                        //si c est une pile
                        Variable freeP_C = new FreeP(c); 
                        precondition.put(freeP_C, true); //la pile c doit être libre pour accueillir le block a
                    } else {
                        Variable fixedB_C = new FixedB(c);
                        precondition.put(fixedB_C, false); //le block c ne doit pas être fixe (sinon implique que le block à déjà un block au dessus) pour accueillir le block a
                    }

                    /* Création effet */
                    Map<Variable,Object> effect = new HashMap<>();

                    //Le OnB de a est déjà créé
                    effect.put(onB_A, c);

                    // La condition varie en fonction de si la variable b est une pile ou un block
                    if (b < 0) {
                        //si b est une pile
                        Variable freeP_B = new FreeP(b); 
                        effect.put(freeP_B, true); //la pile b doit être libre pour accueillir le block a
                    } else {
                        Variable fixedB_B = new FixedB(b);
                        effect.put(fixedB_B, false); //le block b ne doit pas être fixe (sinon implique que le block à déjà un block au dessus) pour accueillir le block a
                    }

                    // La condition varie en fonction de si la variable c est une pile ou un block
                    if (c < 0) {
                        //si la variable c est une pile
                        Variable freeP_C = new FreeP(c); 
                        effect.put(freeP_C, false); //la pile c doit être libre pour accueillir le block a
                    } else {
                        Variable fixedB_C = new FixedB(c);
                        effect.put(fixedB_C, true); //le block c ne doit pas être fixe (sinon implique que le block à déjà un block au dessus) pour accueillir le block a
                    }

                    if (precondition.size() != 3 && effect.size() != 3){
                        System.out.println("[Block A:" + a  + ", " + (b < 0 ? "Pile B:" : "Block B:") + b +", " + (c < 0 ? "Pile C:" : "Block C:") + c + "]");
                        System.out.println(precondition);
                        System.out.println(effect);
                        System.out.println();
                    } 
                    

                    /* Création ou ajout de l'action */
                    Action puActionABC = new BasicAction(precondition, effect);
                    actions.add(puActionABC);
                }
            }
            
        }

        return actions;
    }

    @Override
    public String toString() {
        return "BWBasicActionGen [nbBlock=" + nbBlock + ", nbPile=" + nbPile + "]";
    }
    

}
