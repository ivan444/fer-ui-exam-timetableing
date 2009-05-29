package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;

public class Population {
	private Individual[] individuals;
	private ExamBean[] exams;
	private ExamsData data;
	
	public Population(ExamsData data, int populationSize) {
		super();
		this.data = data;
		this.exams = data.getExams();
		
		individuals = new Individual[populationSize];
		generatePopulation();
	}

	private void generatePopulation() {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = createIndividual();
		}
	}
	
	private Individual createIndividual() {
		int len = exams.length;
		int termsNum = data.getTerms().length;
		Individual novi = new Individual(len);
		
		for (int i = 0; i < len; i++){
			int index = (int) Math.round(Math.random()*termsNum);
			novi.setTerm(i, data.getTerms()[index]);
		}
		return novi;
	}

	public String printIndividual(int index) {
		StringBuilder sb = new StringBuilder();
		
		
		return "";
	}
	
}
