package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Proširenje postupaka mutacije dinamičkim mijenjanjem faktora mutacije na
 * osnovu dobrote jedinke. Što je dobrota manja to je faktor mutacije veći.
 * 
 * @author Ivan Krišto
 * 
 */
// TODO: Dovrši!
public class DynamicMutator extends Mutator {
	private Mutator orgMutator;

	public DynamicMutator(float mutationFactor, TermBean[] allTerms, Mutator orgMutator) {
		super(mutationFactor, allTerms);
		this.orgMutator = orgMutator; 
	}

	@Override
	public void mutate(Individual individual) {
		// promjeni mut fact
		//orgMutator.changeMutationFactor(...);
		orgMutator.mutate(individual);
	}
}
