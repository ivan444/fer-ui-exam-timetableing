package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

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
		int gth = exams.length;
		int termsNum = data.getTerms().length;
		Individual novi = new Individual(gth);
		
		for (int i = 0; i < gth; i++){
			int index = (int) Math.round(Math.random()*(termsNum-1));
			novi.setTerm(i, data.getTerms()[index]);
		}
		return novi;
	}

	public String printIndividual(int index) {
		StringBuilder sb = new StringBuilder();
		Individual timetable = individuals[index];
		int gth = exams.length;
		
		for (int i = 0; i < gth; i++) {
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
	
	private String format00000(int num) {
		String numS = Integer.toString(num);
		int nl = numS.length();
		for (int i = nl; i < 5; i++) {
			numS = "0" + numS;
		}
		
		return numS;
	}
	
}
