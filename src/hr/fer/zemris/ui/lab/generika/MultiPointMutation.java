package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Postupak mutacije zamjenom jednog ili više gena
 * (u originalu, samo jedne baze (ne gena)) drugim.
 * 
 * Opis:
 * http://www.genetichealth.com/g101_changes_in_dna.shtml#Anchor2
 * 
 * @author Ivan Krišto
 * 
 */
public class MultiPointMutation extends Mutator {

	public MultiPointMutation(float mutationFactor, TermBean[] allTerms) {
		super(mutationFactor, allTerms);
		
	}

	@Override
	public void mutate(Individual individual) {
		TermBean[] terms = individual.getSolutionTerms();
		
		for (int i = 0; i < terms.length; i++) {
			if (Math.random() < mutationFactor) {
				int swpIndex = (int) Math.round(Math.random()*(allTerms.length-1));
				individual.setTerm(i, allTerms[swpIndex]);
			}
		}
	}
}
