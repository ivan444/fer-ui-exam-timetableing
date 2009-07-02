package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.Population;

public class TroturnirskoKrizanje extends Krizanje {

	public TroturnirskoKrizanje(int populationSize, int numberOfGenes) {
		super(populationSize, numberOfGenes);
	}

	/**
	 * Ova metoda modelira troturnirski operator crossover genetskog algoritma. Crossover se
	 * odvija nad kromosomima dvaju roditelja.
	 * 
	 * @param i
	 * @param inPop
	 * @param outPop
	 * @return mala beba
	 */
	@Override
	public Individual cross(int i, Population inPop, Population outPop) {
		
		Individual i1 = spinTheWheel(inPop);
		Individual i2 = spinTheWheel(inPop);
		Individual i3 = spinTheWheel(inPop);
		
		for (int n = 1; n <= 20; n++) {
			if (i1.hashCode() == i2.hashCode()
					|| i1.hashCode() == i3.hashCode()
					|| i2.hashCode() == i3.hashCode()) {
				i1 = spinTheWheel(inPop);
				i2 = spinTheWheel(inPop);
				i3 = spinTheWheel(inPop);
				
			} else {
				break;
			}
		}
		
		float i1fit = i1.getFitness();
		float i2fit = i2.getFitness();
		float i3fit = i3.getFitness();
		
		Individual dad = null;
		Individual mom = null;
		
		if (i1fit < i2fit) {
			mom = i1;
			
			if (i3fit < i2fit) {
				dad = i3;
			} else {
				dad = i2;
			}
		} else {
			mom = i2;
			
			if (i3fit < i1fit) {
				dad = i3;
			} else {
				dad = i1;
			}
		}
		
		Individual D1 = inPop.getIndividual(i);
		
		int breakpoint = randomGenerator.nextInt(numberOfGenes);

		for (int n = 0; n < numberOfGenes; n++) {
			if (n < breakpoint) {
				D1.setTerm(n, dad.getTerm(n));
			} else {
				D1.setTerm(n, mom.getTerm(n));
			}
		}

		outPop.setIndividaul(D1, i);
		return D1;
	}

}
