package blocksworld.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import blocksworld.modelling.FixedB;
import blocksworld.modelling.FreeP;
import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;

/**
 * La classe InstanceTools fournit des outils pour manipuler des instances du problème Blocksworld.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class InstanceTools {

    /**
     * Obtient une instance partielle de piles et de blocks à partir d'un générateur de variables Blocksworld.
     *
     * @param bwGen   Le générateur de variables Blocksworld.
     * @param pile    Le numéro de la pile.
     * @param blocks  La liste des blocks dans la pile.
     * @return Une instance partielle de piles et de blocks.
     */
    public static Map<Variable,Object> getPartialInstPileBlockWithBWVarGen(BWVarGenerate bwGen, int pile, List<Integer> blocks) {
        Map<Variable, Object> partielInst = new HashMap<>(); 
        
        if (blocks.isEmpty()) {
            partielInst.put(bwGen.getFreePX(pile), true);
        } else {
            partielInst.put(bwGen.getFreePX(pile), false);
            int precedent = pile;
            for (int i = 0; i < blocks.size(); i++) {
                int block = blocks.get(i);
                partielInst.put(bwGen.getOnBX(block), precedent);
                if (i != blocks.size()-1) {
                    partielInst.put(bwGen.getFixedBX(block), true);
                    precedent = block;
                } else {
                    partielInst.put(bwGen.getFixedBX(block), false);
                }
            }
        }
        return partielInst;
    }

    /**
     * Génère une instance aléatoire du problème Blocksworld.
     *
     * @param random  L'objet Random pour la génération aléatoire.
     * @param nbBlock Le nombre de blocks.
     * @param nbPile  Le nombre de piles.
     * @return Une instance aléatoire du problème Blocksworld.
     */
    public static Map<Variable,Object> getRandomInst(Random random, int nbBlock, int nbPile) {
        Map<Variable, Object> inst = new HashMap<>(); 
        BWVarGenerate bwVarGen = new BWVarGenerate(nbBlock, nbPile);

        Set<Integer> numBlocks = new HashSet<>();
        for (int i = 0; i < nbBlock; i++) numBlocks.add(i);

        int sizeSet = numBlocks.size();

        for (int i = nbPile*-1; i < 0; i++) {
            List<Integer> blocksPile = new ArrayList<>(); 
            int randomSize = random.nextInt(sizeSet);
            if (i == -1) randomSize = sizeSet;
            if (!numBlocks.isEmpty()) {
                for (int j = 0; j < randomSize; j++) {
                    int randomBlock = getByRandomClass(numBlocks, random);
                    numBlocks.remove(randomBlock);
                    blocksPile.add(randomBlock);
                    sizeSet -= 1;
                }
            }
            Map<Variable, Object> partialInst = getPartialInstPileBlockWithBWVarGen(bwVarGen, i, blocksPile); 
            inst.putAll(partialInst);
        }
        return inst;
    }

    /**
     * Obtient un élément aléatoire à partir d'un ensemble.
     *
     * @param <T>    Le type d'élément.
     * @param set    L'ensemble d'éléments.
     * @param random L'objet Random pour la génération aléatoire.
     * @return Un élément aléatoire de l'ensemble.
     */
    public static <T> T getByRandomClass(Set<T> set, Random random) {
        if (set == null || set.isEmpty()) {
            throw new IllegalArgumentException("The Set cannot be empty.");
        }
        int randomIndex = random.nextInt(set.size());
        int i = 0;
        for (T element : set) {
            if (i == randomIndex) {
                return element;
            }
            i++;
        }
        throw new IllegalStateException("Something went wrong while picking a random element.");
    }
    
    /**
     * Obtient une carte symbiotique des relations "On" à partir d'une instance.
     *
     * @param inst L'instance du problème Blocksworld.
     * @return Une carte symbiotique des relations "On".
     */
    public static Map<Integer,Integer> getSymbioMap(Map<Variable,Object> inst) {
        Map<Integer,Integer> synbioMap = new HashMap<>();
        for (Variable variable : inst.keySet()) {
            if (variable instanceof OnB) {
                OnB onB = (OnB) variable;
                int x = Integer.valueOf(onB.getName());
                if (inst.containsKey(onB)) {
                    int y = (int) inst.get(onB);
                    synbioMap.put(y, x);
                }
            }
        }
        return synbioMap;
    }

    /**
     * Obtient le nombre de piles dans une instance.
     *
     * @param inst L'instance du problème Blocksworld.
     * @return Le nombre de piles dans l'instance.
     */
    public static int getNbPile(Map<Variable,Object> inst) {
        int nbPile = 0;
        for (Variable variable : inst.keySet()) {
            if (variable instanceof FreeP) {
                int valPile = Math.abs(Integer.valueOf(variable.getName()));
                if (valPile > nbPile) nbPile = valPile; 
            }
        }
        return nbPile;
    }

    /**
     * Obtient l'ensemble des piles dans une instance.
     *
     * @param inst L'instance du problème Blocksworld.
     * @return L'ensemble des piles dans l'instance.
     */
    public static Set<Integer> getSetPile(Map<Variable,Object> inst) {
        Set<Integer> setPile = new HashSet<>();
        for (Variable variable : inst.keySet()) {
            if (variable instanceof FreeP) {
                setPile.add(Integer.valueOf(variable.getName()));
            }
        }
        return setPile;
    }

    /**
     * Obtient le nombre de blocks dans une instance.
     *
     * @param inst L'instance du problème Blocksworld.
     * @return Le nombre de blocks dans l'instance.
     */
    public static int getNbBlock(Map<Variable,Object> inst) {
        int nbBlock = 0;
        for (Variable variable : inst.keySet()) {
            if (variable instanceof FixedB) {
                nbBlock++;
            }
        }
        return nbBlock;
    }

    /**
     * Affiche une représentation simplifiée de l'instance du problème Blocksworld.
     *
     * @param inst L'instance du problème Blocksworld.
     */
    public static void sysoutSimple(Map<Variable,Object> inst) {
        for (Map.Entry<Variable,Object> entry : inst.entrySet()) {
            Variable var = entry.getKey();
            Object obj = entry.getValue();

            if (var instanceof OnB) {
                OnB onB = (OnB) var;
                System.out.println(onB.getName() + " is on " + obj);
            }

            else if (var instanceof FreeP) {
                FreeP freeP = (FreeP) var;
                System.out.println(freeP.getName() + " is" + ((Boolean) obj ? " free" : "n't free"));
            }

            else if (var instanceof FixedB) {
                FixedB fixedB = (FixedB) var;
                System.out.println(fixedB.getName() + " is" + ((Boolean) obj ? " fixed" : "n't fixed"));
            }
        }
    }

    /**
     * Affiche une représentation détaillée de l'instance du problème Blocksworld.
     *
     * @param inst L'instance du problème Blocksworld.
     */
    public static void sysoutDetail(Map<Variable,Object> inst) {
        System.out.println();
        
        Map<Integer,Integer> synbioMap = InstanceTools.getSymbioMap(inst);
        int nbPile = InstanceTools.getNbPile(inst);

        for (int i = nbPile*-1; i < 0; i++) {
            String textToShow = "P" + Integer.toString(i*-1) + ((Boolean) inst.get(new FreeP(Integer.toString(i))) ? "[free = true] " : "[free = false]") + " | ";
            int current = i;
            while (synbioMap.containsKey(current)) {
                if (current != i) {
                    textToShow += "- ";
                }
                boolean fixedB = (Boolean) inst.get(new FixedB(Integer.toString(synbioMap.get(current))));
                textToShow += "B" + Integer.toString(synbioMap.get(current)) + (fixedB ? "[fixed = true] " : "[fixed = false]") + " ";
                current = synbioMap.get(current);
            }
            System.out.println(textToShow);
        }
        System.out.println();
    }

    /**
     * Affiche une représentation mini-détaillée de l'instance du problème Blocksworld.
     *
     * @param inst L'instance du problème Blocksworld.
     */
    public static void sysoutMiniDetail(Map<Variable,Object> inst) {
        System.out.println();
        
        Map<Integer,Integer> synbioMap = InstanceTools.getSymbioMap(inst);
        Set<Integer> setPile = InstanceTools.getSetPile(inst);

        for (Integer i : setPile) {
            FreeP freeP = new FreeP(Integer.toString(i));
            if (inst.containsKey(freeP)) {
                String textToShow = "P" + Integer.toString(i*-1) + ((Boolean) inst.get(freeP) ? "[true] " : "[false]") + " | ";
                int current = i;
                while (synbioMap.containsKey(current)) {
                    if (current != i) {
                        textToShow += "- ";
                    }
                    boolean fixedB = (Boolean) inst.get(new FixedB(Integer.toString(synbioMap.get(current))));
                    textToShow += "B" + Integer.toString(synbioMap.get(current)) + (fixedB ? "[true] " : "[false]") + " ";
                    current = synbioMap.get(current);
                }
                System.out.println(textToShow);
            }   
        }
        System.out.println();
    }

    /**
     * Affiche une représentation sans détail de l'instance du problème Blocksworld.
     *
     * @param inst L'instance du problème Blocksworld.
     */
    public static void sysoutNoDetail(Map<Variable,Object> inst) {
        System.out.println();

        Map<Integer,Integer> synbioMap = InstanceTools.getSymbioMap(inst);
        int nbPile = InstanceTools.getNbPile(inst);

        for (int i = nbPile*-1; i < 0; i++) {
            String textToShow = "P" + Integer.toString(i*-1) + " | ";
            int current = i;
            while (synbioMap.containsKey(current)) {
                textToShow += "B" + Integer.toString(synbioMap.get(current)) + " ";
                current = synbioMap.get(current);
            }
            System.out.println(textToShow);
        }
        System.out.println();
    }

}
