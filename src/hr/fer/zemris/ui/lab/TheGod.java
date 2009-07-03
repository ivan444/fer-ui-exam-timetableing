package hr.fer.zemris.ui.lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generika.BinarnoKrizanje;
import hr.fer.zemris.ui.lab.generika.FrameShiftMutation;
import hr.fer.zemris.ui.lab.generika.Krizanje;
import hr.fer.zemris.ui.lab.generika.MultiPointMutation;
import hr.fer.zemris.ui.lab.generika.Mutator;
import hr.fer.zemris.ui.lab.generika.TroturnirskoKrizanje;
import hr.fer.zemris.ui.lab.generika.TroturnirskoKrizanjeMultiBreak;

/**
 * Klasa koja sadrzi mehanizme potrebne za simuliranje evolucije genetskim
 * algoritmom. Sadrzi operator krizanja (crossover) i fitness funkciju kojom se
 * evaluira potencijalno rjesenje.
 */
public class TheGod {

	private ExamsData inputData;
	private Mutator mutator;
	private ConflictMatrix conflictMatrix;
	private Krizanje krizanje;
	private Rectifier rectifier;
	private Evaluator evaluator;
	private int populationSize = 50;
	private float mutationFactor;
	private boolean elitizam;
	private int genNum;

	public TheGod(ExamsData eddie, int populationSize, float mutationFactor, int genNum, boolean elitizam) {
		this.inputData = eddie;
		this.conflictMatrix = new ConflictMatrix(eddie.getExams());
		this.evaluator = new Evaluator(eddie, this.conflictMatrix);
		this.mutator = new MultiPointMutation(mutationFactor, eddie.getTerms());
		this.krizanje = new TroturnirskoKrizanje(populationSize, inputData.getExams().length);
		this.rectifier = new Rectifier(eddie);
		this.populationSize = populationSize;
		this.genNum = genNum;
		this.elitizam = true;
		this.mutationFactor = mutationFactor;
	}

	/**
	 * Ova metoda modelira genetski algoritam.
	 * @throws IOException 
	 */
	public void doEvolution() throws IOException {
		Population[] population = new Population[2];
		population[0] = new Population(this.inputData, populationSize);
		population[1] = new Population(this.inputData, populationSize);
		BufferedWriter writer = new BufferedWriter(new FileWriter("graf/graf.p"));
		int currentPop = 0;
		int newPop = 1;
		
		int mutCount = 3;
		Mutator[] mutators = new Mutator[mutCount];
		mutators[0] = new MultiPointMutation(mutationFactor, inputData.getTerms());
		mutators[1] = new MultiPointMutation(mutationFactor*3, inputData.getTerms());
		mutators[2] = new FrameShiftMutation(mutationFactor, inputData.getTerms());
		int mutIndex = 0;
		mutator = mutators[mutIndex];
		
//		int crossCount = 2;
//		Krizanje[] crossers = new Krizanje[crossCount];
//		crossers[0] = new TroturnirskoKrizanje(populationSize, inputData.getExams().length);
//		crossers[1] = new TroturnirskoKrizanjeMultiBreak(populationSize, inputData.getExams().length);
//		int crossIndex = 0;
//		krizanje = crossers[crossIndex];
		
		int k = 0;

		while (k++ < genNum) {
			
			if (k % 50 == 0) {
				mutator = mutators[(mutIndex + 1)%mutCount];
			}
			
//			if ((k % 200 == 0 && crossIndex == 0) || (k % 50 == 0 && crossIndex == 1)) {
//				krizanje = crossers[(crossIndex + 1)%crossCount];
//			}
			
			evaluatePopulation(population[currentPop]);
			writer.write(k + "\t" + population[currentPop].getMinPopulationFitness() + "\t" + population[currentPop].getAvgPopulationFitness() + "\n");
			
			for (int i = 0; i < populationSize; i++) {
				if (elitizam) {
					// Elitizam!
					if (i == population[currentPop].getBestIndividualIndex()) {
						Individual best = population[currentPop].getBestIndividual();
						population[newPop].setIndividaul(best, i);
						continue;
					}
				}
				
				Individual D1 = krizanje.cross(i, population[currentPop], population[newPop]);
				mutator.mutate(D1);
				rectifier.rectifie(D1);
			}

			currentPop = (currentPop + 1) % 2;
			newPop = (newPop + 1) % 2;
		}
		
		writer.close();
		Population p = population[newPop];
		evaluatePopulation(p);
		double minFitness = p.getMinPopulationFitness();
		
		for (int i = 0; i < populationSize; i++) {
			if (Math.abs(minFitness - p.getIndividual(i).getFitness()) < 1e-6) {
				System.out.println(p.individualToString(i));
				break;
			}
		}
	}

	private void evaluatePopulation(Population childrenOfGod) {
		double fitnessSum = 0;
		double maxVal = 0;
		double minVal = Double.MAX_VALUE;
		double avgVal;
		int size = childrenOfGod.size();
		for (int i = 0; i < size; i++) {
			Individual one = childrenOfGod.getIndividual(i);
			double onesFitness = (double) evaluator.evaluateFitness(one);

			one.setFitness((float) onesFitness);

			fitnessSum += onesFitness;

			if (i == 0 || maxVal < onesFitness) {
				maxVal = onesFitness;
			}
			
			if (i == 0 || minVal > onesFitness) {
				minVal = onesFitness;
				childrenOfGod.setBestIndividual(one);
				childrenOfGod.setBestIndividualIndex(i);
			}
			
		}
		
		avgVal = fitnessSum/size;
		childrenOfGod.setPopulationFitness(fitnessSum, maxVal, minVal, avgVal);

	}
}
