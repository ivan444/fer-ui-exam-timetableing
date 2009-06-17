package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Population {
	private Individual[] individuals;
	private ExamBean[] exams;
	private ExamsData data;
	
	private float populationFitness;
	private double maxPopulationFitness;
	
	
	public Population(ExamsData data, int populationSize) {
		super();
		this.data = data;
		this.exams = data.getExams();
		
		individuals = new Individual[populationSize];
		generateInitialPopulation();
	}
	
	public Population(int populationSize) {
		super();
		
		individuals = new Individual[populationSize];
	}

	private void generateInitialPopulation() {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = createRandomIndividual();
		}
	}
	
	private Individual createRandomIndividual() {
		int examsNum = exams.length;
		int termsNum = data.getTerms().length;
		Individual created = new Individual(exams);
		
		for (int i = 0; i < examsNum; i++){
			int index = (int) Math.round(Math.random()*(termsNum-1));
			created.setTerm(i, data.getTerms()[index]);
		}
		return created;
	}
	
	public Individual getIndividual (int index){
		return this.individuals[index];
	}
	
	public Individual[] getAllIndividuals (){
		return individuals;
	}
	
	public void setIndividaul (Individual ind,int index){
		this.individuals[index] = ind;
	}
	
	public int size(){
		return this.individuals.length;
	}

	public String printIndividual(int index) {
		StringBuilder sb = new StringBuilder();
		Individual timetable = individuals[index];
		
		for (int i = 0; i < exams.length; i++) {
			TermBean term = timetable.getTerm(i);
			ExamBean exam = exams[i];
			sb	.append(term.getDate())
				.append('\t')
				.append(2)
				.append('\t')
				.append(exam.getClassName())
				.append('\t')
				.append(format00000(exam.getExamID()))
				.append('\n');
		}
		
		return sb.toString();
	}
	
	/**
	 * LOL!
	 */
	public static String format00000(int num) {
		String numS = Integer.toString(num);
		int nl = numS.length();
		for (int i = nl; i < 5; i++) {
			numS = "0" + numS;
		}
		
		return numS;
	}

	public void setPopulationFitness(float populationFitness, double max) {
		this.populationFitness = populationFitness;
		this.maxPopulationFitness = max;
	}

	public float getPopulationFitness() {
		return populationFitness;
	}
	
	public double getMaxPopulationFitness() {
		return this.maxPopulationFitness;
	}
	
}
