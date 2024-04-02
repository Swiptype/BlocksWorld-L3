package blocksworld.generate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.modelling.FixedB;
import blocksworld.modelling.FreeP;
import blocksworld.modelling.OnB;
import blocksworld.modelling.Variable;

/**
 * La classe BWVarGenerate est utilisée pour générer les variables nécessaires
 * pour modéliser le problème Blocksworld, y compris les variables pour les piles,
 * les blocks fixes et les relations "On" entre les blocks.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public class BWVarGenerate {

    private int nbBlock, nbPile;
    private Map<Integer, FreeP> freePofPile;
    private Map<Integer, OnB> onBofBlock;
    private Map<Integer, FixedB> fixedBofBlock;
    private Set<Variable> variables;

    /**
     * Constructeur de la classe BWVarGenerate.
     * 
     * @param nbBlock Le nombre de blocks.
     * @param nbPile  Le nombre de piles.
     */
    public BWVarGenerate(int nbBlock, int nbPile) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;

        this.freePofPile = new HashMap<>();
        this.fixedBofBlock = new HashMap<>();
        this.onBofBlock = new HashMap<>();

        this.initVariables();
    }

    /**
     * Initialise les variables nécessaires pour le problème Blocksworld.
     */
    private void initVariables() {
        this.variables = new HashSet<>();

        Set<Object> booleanDomain = new HashSet<>(Set.of(true, false));

        Set<Object> domainBlockPile = new HashSet<>();

        for (int i = 0; i < nbBlock; i++) {
            FixedB fixedB = new FixedB(Integer.toString(i), booleanDomain);
            variables.add(fixedB);
            domainBlockPile.add(i);
            this.fixedBofBlock.put(i, fixedB);
        }
        for (int i = nbPile * -1; i < 0; i++) {
            FreeP freeP = new FreeP(Integer.toString(i), booleanDomain);
            variables.add(freeP);
            domainBlockPile.add(i);
            this.freePofPile.put(i, freeP);
        }

        for (int i = 0; i < this.nbBlock; i++) {
            Set<Object> domainMinisCurrent = new HashSet<>(domainBlockPile);
            domainMinisCurrent.remove(i);
            OnB onB = new OnB(Integer.toString(i), domainMinisCurrent);
            variables.add(onB);
            this.onBofBlock.put(i, onB);
        }
    }

    /** Accesseur */
    /**
     * Obtient l'ensemble des variables générées.
     * 
     * @return L'ensemble des variables générées.
     */
    public Set<Variable> getVariables() {
        return this.variables;
    }

    /**
     * Obtient l'objet FreeP associé à une pile spécifique.
     * 
     * @param pile Le numéro de la pile.
     * @return L'objet FreeP associé à la pile spécifiée.
     */
    public FreeP getFreePX(int pile) {
        return this.freePofPile.get(pile);
    }

    /**
     * Obtient l'objet FixedB associé à un block spécifique.
     * 
     * @param block Le numéro du block.
     * @return L'objet FixedB associé au block spécifié.
     */
    public FixedB getFixedBX(int block) {
        return this.fixedBofBlock.get(block);
    }

    /**
     * Obtient l'objet OnB associé à un block spécifique.
     * 
     * @param block Le numéro du block.
     * @return L'objet OnB associé au block spécifié.
     */
    public OnB getOnBX(int block) {
        return this.onBofBlock.get(block);
    }
}
