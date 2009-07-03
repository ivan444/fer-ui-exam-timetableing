package hr.fer.zemris.ui.lab.generika;

import java.util.Arrays;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.Population;

public class TroturnirskoKrizanjeMultiBreak extends Krizanje {
	private int[] breakpoint;
	
	public TroturnirskoKrizanjeMultiBreak(int populationSize, int numberOfGenes) {
		super(populationSize, numberOfGenes);
		
		int breaksNum = randomGenerator.nextInt(10);
		breakpoint = new int[breaksNum];
		for (int j = 0; j < breakpoint.length; j++) {
			breakpoint[j] = randomGenerator.nextInt(numberOfGenes); // Možda smanji breakove
		}
		Arrays.sort(breakpoint);
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
		
		int brps = 0;
		for (int n = 0; n < numberOfGenes; n++) {
			if (n < breakpoint[brps]) {
				D1.setTerm(n, dad.getTerm(n));
			} else {
				if (brps + 1 < breakpoint.length) {
					brps++;
				}
				Individual swp = dad;
				dad = mom;
				mom = swp;
			}
		}

		outPop.setIndividaul(D1, i);
		return D1;
	}

}
