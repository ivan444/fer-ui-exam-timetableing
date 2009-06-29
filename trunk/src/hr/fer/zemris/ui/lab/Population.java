package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;

public class Population {
	private Individual[] individuals;
	private ExamBean[] exams;
	private ExamsData data;
	
	private double populationFitness;
	private double maxPopulationFitness;
	private double avgPopulationFitness;
	private double minPopulationFitness;
	
	// TODO: Mislim da ovo ne odgovara tu...
	private boolean elitizam;
	
	private Individual bestIndividual;
	
	public Population(ExamsData data, int populationSize, boolean elitizam) {
		super();
		this.elitizam = elitizam;
		this.data = data;
		this.exams = data.getExams();

		individuals = new Individual[populationSize];
		generateInitialPopulation();
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
	
	public int size() {
		return this.individuals.length;
	}

	public String individualToString(int index) {
		return individuals[index].toString();
	}
	
	public void setPopulationFitness(double populationFitness, double max, double min, double avg) {
		this.populationFitness = populationFitness;
		this.maxPopulationFitness = max;
		this.minPopulationFitness = min;
		this.avgPopulationFitness = avg;
	}

	public double getPopulationFitness() {
		return populationFitness;
	}
	
	public double getMaxPopulationFitness() {
		return this.maxPopulationFitness;
	}

	public double getAvgPopulationFitness() {
		return avgPopulationFitness;
	}

	public double getMinPopulationFitness() {
		return minPopulationFitness;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}
	
}
