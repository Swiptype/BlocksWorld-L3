/**
 * L'interface BWConstraintGen définit les méthodes pour générer des contraintes pour le problème Blocksworld.
 */
package blocksworld.generate;

import java.util.Map;
import java.util.Set;

import blocksworld.modelling.Constraint;
import blocksworld.modelling.Variable;

/**
 * L'interface BWConstraintGen définit les méthodes pour générer des contraintes pour le problème Blocksworld.
 * 
 * @author TEURTERIE Alexis
 * @author GENESTIER Theo
 */
public interface BWConstraintGen {

    /**
     * Obtient l'ensemble des contraintes générées.
     * 
     * @return L'ensemble des contraintes générées.
     */
    public Set<Constraint> getConstraints();

    /**
     * Obtient l'ensemble des contraintes générées pour une instance spécifique.
     * 
     * @param inst L'instance spécifique pour laquelle générer les contraintes.
     * @return L'ensemble des contraintes générées pour l'instance spécifique.
     */
    public Set<Constraint> getConstraintsForInstance(Map<Variable, Object> inst);

}
