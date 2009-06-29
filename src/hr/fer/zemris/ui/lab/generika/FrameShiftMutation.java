package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Postupak mutacije dodavanjem gena i posmicanjem svih
 * svih njemu desnih gena za jedno mjesto.
 * 
 * Opis:
 * http://www.genetichealth.com/g101_changes_in_dna.shtml#Anchor2
 * 
 * @author Ivan Krišto
 *
 */
public class FrameShiftMutation extends Mutator {

	public FrameShiftMutation(float mutationFactor, TermBean[] allTerms) {
		super(mutationFactor, allTerms);
	}

	@Override
	public void mutate(Individual individual) {
		TermBean[] terms = individual.getSolutionTerms();
		
		if (Math.random() < mutationFactor) {
			int insertIndex = (int) Math.round(Math.random()*(terms.length-1));
			int swpIndex = (int) Math.round(Math.random()*(allTerms.length-1));
			TermBean toInsert = allTerms[swpIndex];
			TermBean toMove = null;
			for (int i = insertIndex; i < terms.length; i++) {
				toMove = terms[i];
				individual.setTerm(i, toInsert);
				toInsert = toMove;
			}
		}
	}
}
