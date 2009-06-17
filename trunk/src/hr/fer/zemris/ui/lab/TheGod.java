package hr.fer.zemris.ui.lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Klasa koja sadrzi mehanizme potrebne za simuliranje evolucije genetskim
 * algoritmom. Sadrzi operator krizanja (crossover) i fitness funkciju kojom se
 * evaluira potencijalno rjesenje.
 */
public class TheGod {

	private ExamsData inputData;
	private ConflictMatrix conflictMatrix;
	private Random randomGenerator;
	private double[] singleFitness;
	private double[] lenSingle;
	// ovdje bismo mogli definirati parametre genetskog algoritma
	// evo samo neki za primjer:
	private int populationSize = 100; //Adam & Eve
	final private float mutationFactor = 0.001f; //No mutants allowed!
	private boolean incest = false; //No Fritzl allowed!
	private int numberOfParents = 2; //No orgies allowed!

	private Evaluator evaluator;

	public TheGod(ExamsData eddie) {
		this.inputData = eddie;
		this.conflictMatrix = new ConflictMatrix(eddie.getExams());
		this.randomGenerator = new Random();
		this.evaluator = new Evaluator(eddie, this.conflictMatrix);
		this.singleFitness = new double[populationSize];
		this.lenSingle = new double[populationSize];
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

		int k = 0;

		while (k++ < 1000) {

			evaluatePopulation(population[currentPop]);

//			System.out.println("generacija " + k);
//			System.out.println("f(min) = " + population[currentPop].getMinPopulationFitness());
//			System.out.println("f(max) = " + population[currentPop].getMaxPopulationFitness());
//			System.out.println("f(avg) = " + population[currentPop].getAvgPopulationFitness());
//			System.out.println();
			writer.write(k + "\t" + population[currentPop].getMinPopulationFitness() + "\t" + population[currentPop].getAvgPopulationFitness() + "\n");
			for (int i = 0; i < populationSize; i++) {

				Individual R1 = spinTheWheel(population[currentPop]);
				Individual R2 = spinTheWheel(population[currentPop]);

				Individual D1 = makeBabies(R1, R2, i, population[newPop]);
				D1.mutate(mutationFactor, inputData.getTerms());
			}

			currentPop = (currentPop + 1) % 2;
			newPop = (newPop + 1) % 2;

//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		writer.close();
		Population p = population[newPop];
		evaluatePopulation(p);
		double minFitness = p.getMinPopulationFitness();
		
		for (int i = 0; i < populationSize; i++) {
			if (Math.abs(minFitness - p.getIndividual(i).fitness()) < 1e-6) {
				//best = p.getIndividual(i);
				System.out.println(p.printIndividual(i));
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
			}
			
		}
		
		avgVal = fitnessSum/size;
		childrenOfGod.setPopulationFitness(fitnessSum, maxVal, minVal, avgVal);

	}

	private Individual spinTheWheel(Population childrenOfGod) {

		int size = childrenOfGod.size();

		double fitnessSum = 0;
		double maxFitness = childrenOfGod.getMaxPopulationFitness();

		for (int i = 0; i < size; i++) {
			singleFitness[i] = maxFitness - childrenOfGod.getIndividual(i).fitness();
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

	/**
	 * Ova metoda modelira operator crossover genetskog algoritma. Crossover se
	 * odvija nad kromosomima dvaju roditelja.
	 * 
	 * @param mom prvi roditelj
	 * @param dad drugi roditelj
	 * @return mala beba
	 */
	private Individual makeBabies(Individual mom, Individual dad, int index, Population newPop) {

		// ovaj new treba izbacit! o mogucnostima cemo raspravit
		// TODO: Dodati provjeru hard constrainta?
		Individual D1 = newPop.getIndividual(index);
		
		int numberOfGenes = this.inputData.getExams().length;

		int breakpoint = randomGenerator.nextInt(numberOfGenes);

		for (int i = 0; i < numberOfGenes; i++) {
			if (i < breakpoint) {
				D1.setTerm(i, dad.getTerm(i));
			} else {
				D1.setTerm(i, mom.getTerm(i));
			}
		}

		newPop.setIndividaul(D1, index);
		return D1;
	}

	/**
	 * Metoda pretvara zadano rjesenje u String formata prikladnog za ispis u
	 * datoteku.
	 * 
	 * @param timetable Raspored koji se formatira za ispis
	 * @return String koji predstavlja zapis rasporeda u formatu specificiranom
	 *         u zadatku
	 */
	public String printIndividual(Individual timetable) {
		
		StringBuilder sb = new StringBuilder();
		int numberOfGenes = this.inputData.getExams().length;
		ExamBean[] exams = this.inputData.getExams();

		for (int i = 0; i < numberOfGenes; i++) {
			TermBean term = timetable.getTerm(i);
			ExamBean exam = exams[i];
			sb.append(term.getDate()).append('\t').append(2).append('\t')
					.append(exam.getClassName()).append('\t')
					.append(Population.format00000(exam.getExamID()))
					.append('\n');
		}

		return sb.toString();
	}

}
