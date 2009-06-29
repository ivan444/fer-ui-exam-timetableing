package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Osnova za izvođenje mutacije.
 * 
 * @author Ivan Krišto
 * 
 */
public abstract class Mutator {
	protected TermBean[] allTerms;
	protected float mutationFactor;
	
	public Mutator(float mutationFactor, TermBean[] allTerms) {
		this.allTerms = allTerms;
		this.mutationFactor = mutationFactor;
	}
	
	public abstract void mutate(Individual individual);
	
	public void changeMutationFactor(float mutationFactor) {
		this.mutationFactor = mutationFactor;
	}
}
