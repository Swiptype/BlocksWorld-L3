package blocksworld.cp;

import java.util.Map;

import blocksworld.modelling.Variable;

/**
 * L'interface <br>Solver</br> permet de recréer des instances.
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public interface Solver {
    
    /**
     * La méthode solve() permet de recréer des instances avec l'aide d'autre classe.
     */
    public Map<Variable, Object> solve();

}
