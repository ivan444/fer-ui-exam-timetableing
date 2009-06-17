package hr.fer.zemris.ui.lab;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Klasa koja sadrzi mehanizme potrebne za simuliranje evolucije genetskim algoritmom.
 * Sadrzi operator krizanja (crossover) i fitness funkciju kojom se evaluira potencijalno rjesenje.
 */
public class TheGod {
	
	private ExamsData inputData;
	private ConflictMatrix conflictMatrix;
	private Random randomGenerator;
	
	// ovdje bismo mogli definirati parametre genetskog algoritma
	// evo samo neki za primjer:
	private int populationSize = 20;		//Adam & Eve
	private float mutationFactor = 0.00f;		//No mutants allowed!
	private boolean incest = false;				//No Fritzl allowed!
	private int numberOfParents = 2;			//No orgies allowed!
	
	private Evaluator evaluator;
	
	
	public TheGod(ExamsData eddie){
		this.inputData = eddie;
		this.conflictMatrix = new ConflictMatrix(eddie.getExams());
		this.randomGenerator = new Random();
		this.evaluator = new Evaluator(eddie,this.conflictMatrix);
	}
	
	/**
	 * Ova metoda modelira genetski algoritam.
	 */
	public void doEvolution(){
		
		Population childrenOfGod = new Population(this.inputData, populationSize);
		
		int k = 0;
		Population theNewGeneration = new Population(populationSize);
		while (k++ < 1000){
			
			evaluatePopulation(childrenOfGod);
			System.out.println("f = " + childrenOfGod.getPopulationFitness());
			
			for (int i = 0; i < populationSize;i++){
				
				Individual R1 = spinTheWheel(childrenOfGod);
				Individual R2 = spinTheWheel(childrenOfGod);
				
				Individual D1 = makeBabies(R1, R2);
				D1.mutate(mutationFactor);
				
				theNewGeneration.setIndividaul(D1, i);
			}
			//evaluatePopulation(childrenOfGod);
			//System.out.println("f1 = " + theNewGeneration.getPopulationFitness());
			//childrenOfGod = theNewGeneration;
			//System.out.println("f2 = " + childrenOfGod.getPopulationFitness());
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

	private void evaluatePopulation(Population childrenOfGod) {
		
		int fitnessSum = 0;
		double maxValue = 0;
		
		for (int i = 0; i < childrenOfGod.size(); i++){
			
			Individual one = childrenOfGod.getIndividual(i);
			float onesFitness = evaluator.evaluateFitness(one);
			one.setFitness(onesFitness);
			
			fitnessSum += onesFitness;	
			
			if (i == 0 || maxValue < onesFitness)
            {
                maxValue = onesFitness;
            }
		}
		
		childrenOfGod.setPopulationFitness(fitnessSum, maxValue);
		
	}

	private Individual spinTheWheel(Population childrenOfGod) {
		
		int size = childrenOfGod.size();
		
		float fitnessSum = childrenOfGod.getPopulationFitness();
		double maxFitness =  childrenOfGod.getMaxPopulationFitness();
		
		double[] singleFitness = new double[size]; 
        double[] lenSingle = new double[size];
		
		
		for (int i = 0; i < size; i++)
        {
			singleFitness[i] = maxFitness - childrenOfGod.getIndividual(i).fitness();
        }
		
		for (int i = 0; i < size; i++)
        {
            lenSingle[i] = singleFitness[i] / fitnessSum;
        }
		
		
		double chosen = randomGenerator.nextDouble(); 
        double accumulateSum = 0;

        for (int i = 0; i < size; i++)
        {
            accumulateSum = accumulateSum + lenSingle[i];

            if (chosen < accumulateSum)
            {
            	//System.out.println(childrenOfGod.getIndividual(i).fitness());
                return childrenOfGod.getIndividual(i);
            }
        }

        return childrenOfGod.getIndividual(size - 1);
		
	}

	/**
	 * Ova metoda modelira operator crossover genetskog algoritma. Crossover se odvija nad kromosomima
	 * dvaju roditelja.
	 * @param mom prvi roditelj
	 * @param dad drugi roditelj
	 * @return mala beba
	 */
	private Individual makeBabies (Individual mom, Individual dad){
		
		// ovaj new treba izbacit! o mogucnostima cemo raspravit
		Individual baby = new Individual(this.inputData.getExams()); 
		
		int numberOfGenes = this.inputData.getExams().length;
		
		int breakpoint = randomGenerator.nextInt(numberOfGenes);
		
		for (int i = 0; i < numberOfGenes; i++){
			if (i < breakpoint){
				baby.setTerm(i, dad.getTerm(i));
			} else {
				baby.setTerm(i, mom.getTerm(i));
			}
		}
	
		return baby; 
	}
	
	/**
	 * Metoda pretvara zadano rjesenje u String formata prikladnog za ispis u datoteku.
	 * @param timetable Raspored koji se formatira za ispis
	 * @return String koji predstavlja zapis rasporeda u formatu specificiranom u zadatku
	 */
	public String printIndividual(Individual timetable) {
		StringBuilder sb = new StringBuilder();
		
		int numberOfGenes = this.inputData.getExams().length;
		ExamBean[] exams = this.inputData.getExams();
		
		for (int i = 0; i < numberOfGenes; i++) {
			TermBean term = timetable.getTerm(i);
			ExamBean exam = exams[i];
			sb	.append(term.getDate())
				.append('\t')
				.append(2)
				.append('\t')
				.append(exam.getClassName())
				.append('\t')
				.append(Population.format00000(exam.getExamID()))
				.append('\n');
		}
		
		return sb.toString();
	}

}
