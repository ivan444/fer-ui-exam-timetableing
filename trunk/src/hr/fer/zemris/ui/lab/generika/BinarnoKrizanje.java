package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.Population;

public class BinarnoKrizanje extends Krizanje {

	public BinarnoKrizanje(int populationSize, int numberOfGenes) {
		super(populationSize, numberOfGenes);
	}

	/**
	 * Ova metoda modelira operator crossover genetskog algoritma. Crossover se
	 * odvija nad kromosomima dvaju roditelja.
	 * 
	 * @param i
	 * @param inPop
	 * @param outPop
	 * @return mala beba
	 */
	@Override
	public Individual cross(int i, Population inPop, Population outPop) {
		Individual mom = spinTheWheel(inPop);
		Individual dad = spinTheWheel(inPop);
		
		Individual result = inPop.getIndividual(i);
		
		int breakpoint = randomGenerator.nextInt(numberOfGenes);

		for (int n = 0; n < numberOfGenes; n++) {
			if (n < breakpoint) {
				result.setTerm(n, dad.getTerm(n));
			} else {
				result.setTerm(n, mom.getTerm(n));
			}
		}

		outPop.setIndividaul(result, i);
		return result;
	}

}
