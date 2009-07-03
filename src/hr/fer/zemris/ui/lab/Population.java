package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.FixedTermBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Population {
	private Individual[] individuals;
	private ExamBean[] exams;
	private ExamsData data;
	private Individual bestIndividual;
	private int bestIndividualIndex;
	
	private int[] fixedTermIndexes;

	private double populationFitness;
	private double maxPopulationFitness;
	private double avgPopulationFitness;
	private double minPopulationFitness;

	public Population(ExamsData data, int populationSize) {
		super();
		this.data = data;
		this.exams = data.getExams();
		this.fixedTermIndexes = data.getFixedTermExamIndexes();
		this.individuals = new Individual[populationSize];
		this.bestIndividualIndex = -1;
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
		Individual created = new Individual(data);

		for (int i = 0; i < examsNum; i++) {
			if (isFixedIndex(i)){
				created.setFixedTerm(i, getTermForFixed(i));
			} else {
				int index = (int) Math.round(Math.random() * (termsNum - 1));
				created.setTerm(i, data.getTerms()[index]);
			}
			
		}
		return created;
	}
	
	public boolean isFixedIndex(int index) {
		
		for (int i = 0; i < fixedTermIndexes.length; i++) {
			if (fixedTermIndexes[i] == index) return true;
		}
		
		return false;
	}

	public Individual getIndividual(int index) {
		return this.individuals[index];
	}

	public Individual[] getAllIndividuals() {
		return individuals;
	}

	public void setIndividaul(Individual ind, int index) {
		this.individuals[index] = ind;
	}

	public int size() {
		return this.individuals.length;
	}

	public String individualToString(int index) {
		return individuals[index].toString();
	}

	public void setPopulationFitness(double populationFitness, double max,
			double min, double avg) {
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
	
	public TermBean getTermForFixed(int index) {
		ExamBean exam = exams[index];
		FixedTermBean[] fiksni = data.getFixedTerms();
		
		for (int i = 0; i < fiksni.length; i++) {
			if (fiksni[i].getExamID() == exam.getExamID()){
				String date = fiksni[i].getDate();
				return findTermByDate(date);
			}
		}
		return null;
	}

	private TermBean findTermByDate(String date) {
		TermBean[] terms = data.getTerms();
		
		for (int i = 0; i < terms.length; i++) {
			if (terms[i].getDate().equals(date)) return terms[i];
		}
		
		return null;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}

	public int getBestIndividualIndex() {
		return bestIndividualIndex;
	}

	public void setBestIndividualIndex(int bestIndividualIndex) {
		this.bestIndividualIndex = bestIndividualIndex;
	}
}
