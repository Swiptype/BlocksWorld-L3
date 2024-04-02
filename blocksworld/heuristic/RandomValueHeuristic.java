package blocksworld.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import blocksworld.modelling.Variable;

/**
 * Cette classe représente une heuristique qui attribue des valeurs de manière aléatoire à une variable.
 * Elle utilise une instance de la classe Random pour introduire de l'aléatoire dans l'ordonnancement des valeurs.
 */
public class RandomValueHeuristic implements ValueHeuristic {
    
    /* Variables */
    private Random random;
    
    /* Constructeur */
    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    /**
     * La méthode ordering mélange la liste des valeurs du domaine à l'aide de 'shuffle' et la renvoie.
     * 
     * @param variable La variable pour laquelle on veut ordonner les valeurs.
     * @param domaine L'ensemble des valeurs du domaine de la variable.
     * @return Une liste de valeurs du domaine mélangée de manière aléatoire.
     */
    public List<Object> ordering(Variable var, Set<Object> dom) {
        List<Object> list = new ArrayList<>(dom);
        Collections.shuffle(list, random);
        return list;
    }
    
}
