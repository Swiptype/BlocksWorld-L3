package blocksworld.main;

import java.util.List;
import java.util.Random;
import java.util.Set;

import blocksworld.datamining.Apriori;
import blocksworld.datamining.AssociationRule;
import blocksworld.datamining.AssociationRuleMiner;
import blocksworld.datamining.BooleanDatabase;
import blocksworld.datamining.BruteForceAssociationRuleMiner;
import blocksworld.datamining.Itemset;
import blocksworld.datamining.ItemsetMiner;
import blocksworld.generate.BWBoolVarGen;
import blocksworld.modelling.BooleanVariable;
import bwgeneratordemo.Demo;

public class DemoParti4 {

    public static void main(String[] args) {

        /*Exercice 12. 
        Dans une classe exécutable, créer une base de données (de type BooleanDatabase) d’états
        du monde des blocs, puis lancer une extraction de motifs et de règles d’association, et afficher les résultats.
        Indication : En utilisant la librairie fournie, les paramètres suivants fournissent des résultats intéressants : base de données de 10 000 instances, extraction de motifs de fréquence au moins 2/3, et de règles
        d’association de fréquence au moins 2/3 et de confiance au moins 95/100.
        */

        int seed = 130304;
        Random random = new Random(seed);

        TimeTaken timeTaken = new TimeTaken();

        timeTaken.start();
        /*Exercice 11. 
        Écrire une classe qui pourra être instanciée avec un nombre de blocs et un nombre de piles,
        et à laquelle on pourra demander l’ensemble de toutes les variables booléennes (de type BooleanVariable)
        correspondant à ces paramètres, ainsi que l’instance (de type Set<BooleanVariable>) correspondant à
        un état donné (de type List<List<Integer>>). Pour ce dernier point, on pourra instancier les variables
        fixedb et freep avec leur signification intuitive (fixedb à true pour tous les blocs sauf ceux en haut d’une
        pile, et freep à true exactement pour les piles vides).
        */
        BWBoolVarGen bwBoolVarGen = new BWBoolVarGen(5, 5); // La classe

        int nTransaction = 1000;
        BooleanDatabase db = new BooleanDatabase(bwBoolVarGen.getItems());
        for (int i = 0; i < nTransaction; i++) {
            // Drawing a state at random
            List<List<Integer>> state = Demo.getState(random);
            // Converting state to instance
            Set<BooleanVariable> instance = BWBoolVarGen.convertStateToInstance(state);
            // Adding state to database
            db.add(instance);
        }
        timeTaken.end();

        System.out.println("All in database ! End in " + timeTaken.getSecondTaken() + "s");

        timeTaken.start();
        ItemsetMiner itemsetMiner = new Apriori(db);
        float minFrequency = .66f;
        System.out.println("minFrequency : " + minFrequency);
        Set<Itemset> itemsets = itemsetMiner.extract(minFrequency);
        //System.out.println(itemsets);
        timeTaken.end();
        System.out.println("All transactions to Itemset, Done ! End in " + timeTaken.getSecondTaken() + "s Number : " + itemsets.size());
        
        timeTaken.start();
        AssociationRuleMiner associationRuleMiner = new BruteForceAssociationRuleMiner(db);
        float minConfidence = .95f;
        System.out.println("minConfidence : " + minConfidence);
        Set<AssociationRule> associationRules = associationRuleMiner.extract(minFrequency, minConfidence);
        timeTaken.end();
        //System.out.println(associationRules);
        System.out.println("All AssociationRule, Done !  End in " + timeTaken.getSecondTaken() + "s Number : " + associationRules.size());
        
        /*
        nbTransaction : 1000
        Etape 1 : ~ 0.083s
        Etape 2 : ~ 4.968s
        Number : 4095
        Etape 3 : ~ 944.055s (~15min)
        */

        /*
        nbTransaction : 10000
        Etape 1 : ~ 0.33s
        Etape 2 : ~ 57.5s
        Number : 4095
        Etape 3 : ~ 1129.340s
        Number : 348 151
        */
    }
    
}
