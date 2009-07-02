package hr.fer.zemris.ui.lab.generika;

import java.util.Random;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.Population;

public abstract class Krizanje {
	protected double[] singleFitness;
	protected double[] lenSingle;
	protected Random randomGenerator;
	protected int numberOfGenes;
	
	public Krizanje(int populationSize, int numberOfGenes) {
		this.singleFitness = new double[populationSize];
		this.lenSingle = new double[populationSize];
		this.randomGenerator = new Random();
		this.numberOfGenes = numberOfGenes;
	}
	
	/**
	 * Križanjem mijenja jedinku na i-tom indeksu.
	 * @param i Indeks jedinke u koju se sprema rezultat križanja.
	 * @param inPop Populacija sa originalnim jedinkama (genetski materijal).
	 * @param outPop Populacija u koju postavljamo novu jedinku.
	 * @return Jedinka izmjenjena križanjem.
	 */
	public abstract Individual cross(int i, Population inPop, Population outPop);
	
	protected Individual spinTheWheel(Population childrenOfGod) {

		int size = childrenOfGod.size();

		double fitnessSum = 0;
		double maxFitness = childrenOfGod.getMaxPopulationFitness();

		for (int i = 0; i < size; i++) {
			singleFitness[i] = maxFitness - childrenOfGod.getIndividual(i).getFitness();
			fitnessSum += singleFitness[i];
		}

		for (int i = 0; i < size; i++) {
			lenSingle[i] = singleFitness[i] / fitnessSum;
		}

		double chosen = randomGenerator.nextDouble();
		double accumulateSum = 0;

		for (int i = 0; i < size; i++) {
			accumulateSum = accumulateSum + lenSingle[i];

			if (chosen < accumulateSum) {
				return childrenOfGod.getIndividual(i);
			}
		}

		return childrenOfGod.getIndividual(size - 1);

	}
}
