package blocksworld.generate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blocksworld.modelling.BooleanVariable;

/**
 * La classe BWBoolVarGen génère des variables booléennes pour le problème Blocksworld.
 * Elle crée des variables booléennes représentant l'état du monde, telles que la fixation des blocs et la relation "on" entre les blocs.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BWBoolVarGen {
    
    /** Variable */
    private int nbBlock;
    private int nbPile;
    private Set<BooleanVariable> items;

    /** Constructeur */
    /**
     * Constructeur de la classe BWBoolVarGen.
     * 
     * @param nbBlock Le nombre de blocs dans le problème.
     * @param nbPile  Le nombre de piles dans le problème.
     */
    public BWBoolVarGen(int nbBlock, int nbPile) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;
        this.items = this.createItems();
    }

    /**
     * Crée un ensemble de variables booléennes représentant l'état du monde.
     * 
     * @return Un ensemble de variables booléennes représentant l'état du monde.
     */
    private Set<BooleanVariable> createItems() {
        int nbBlock = 5; int nbPile = 5;
        Set<BooleanVariable> items = new HashSet<>();
        for (int jPile = nbPile*-1; jPile < 0; jPile++) {

            String textFreeP = "FreeP(" + jPile + ")";
            BooleanVariable freeP = new BooleanVariable(textFreeP);
            items.add(freeP);

        }
        for (int iBlock = 0; iBlock < nbBlock; iBlock++) {

            String textFixedB = "FixedB(" + iBlock + ")";
            BooleanVariable fixedB = new BooleanVariable(textFixedB);
            items.add(fixedB);

            for (int kDestination = nbPile*-1; kDestination < nbBlock; kDestination++) {
                
                String textOnB = "OnB(" + iBlock + "," + kDestination + ")";
                BooleanVariable onB = new BooleanVariable(textOnB);
                items.add(onB);

            }
        }
        return items;
    }

    /**
     * Obtient l'ensemble des variables booléennes représentant l'état du monde.
     * 
     * @return L'ensemble des variables booléennes représentant l'état du monde.
     */
    public Set<BooleanVariable> getItems() {
        return this.items;
    }

    /**
     * Convertit un état de pile spécifique en une instance d'ensemble de variables booléennes.
     * 
     * @param state L'état de pile spécifique à convertir.
     * @return Une instance d'ensemble de variables booléennes représentant l'état de pile.
     */
    public static Set<BooleanVariable> convertStateToInstance(List<List<Integer>> state) {
        Set<BooleanVariable> instance = new HashSet<>();
        int jPile = -1;
        for (List<Integer> pileList : state) {

            int precedent = jPile;

            for (Integer iBlock : pileList) {

                String textFixedB = "FixedB(" + iBlock + ")";
                BooleanVariable fixedB = new BooleanVariable(textFixedB);
                instance.add(fixedB);

                String textOnB = "OnB(" + iBlock + "," + precedent + ")";
                BooleanVariable onB = new BooleanVariable(textOnB);
                instance.add(onB);

                precedent = iBlock;
            }

            String textFreeP = "FreeP(" + jPile + ")";
            BooleanVariable freeP = new BooleanVariable(textFreeP);
            instance.add(freeP);
            jPile--;

        }
        return instance;
    }
    
    /**
     * Obtient le nombre de blocs dans le problème.
     * 
     * @return Le nombre de blocs dans le problème.
     */
    public int getNbBlock() {
        return nbBlock;
    }

    /**
     * Obtient le nombre de piles dans le problème.
     * 
     * @return Le nombre de piles dans le problème.
     */
    public int getNbPile() {
        return nbPile;
    }

    @Override
    public String toString() {
        return "BWBoolVarGen [nbBlock=" + nbBlock + ", nbPile=" + nbPile + "]";
    }
}
