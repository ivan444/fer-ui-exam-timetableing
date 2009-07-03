package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Individual {
	private TermBean[] terms;
	private ExamBean[] exams;

	private int[] fixedTermIndexes;
	
	// TODO: dodati term day difference matricu
	//private Map<TermBean, List<ExamBean>> examsInTerm;
	private TermExamMatrix examsInTerm;
	
	private float fitness;
	
	public Individual(ExamsData examData) {
		this.terms = new TermBean[examData.getExams().length];
		this.exams = examData.getExams();
		this.fixedTermIndexes = examData.getFixedTermExamIndexes();
		
		this.examsInTerm = new TermExamMatrix(examData.getTerms(), examData.getExams().length);
	}
	
	public TermBean getTerm(int index) {
		return terms[index];
	}
	
	public TermBean[] getSolutionTerms(){
		return this.terms;
	}
	
	public void setTerm(int index, TermBean term) {
		
		if (isFixedIndex(index)) {
			return; //fiksne termine ne mijenjamo!!!
		}
		
		TermBean oldTerm = terms[index];
		if (oldTerm != null) {
			removeExamFromTerm(oldTerm, index);
		}
		
		this.terms[index] = term;

		ExamBean exam = this.exams[index];
		this.examsInTerm.addTermExam(term, exam);
	}
	
	public void setFixedTerm(int index, TermBean term) {
		
		if (!isFixedIndex(index)) throw new IllegalArgumentException("Pokusaj ilegalnog ubacivanja u fiksni termin!");
		
		TermBean oldTerm = terms[index];
		if (oldTerm != null) {
			removeExamFromTerm(oldTerm, index);
		}
		
		this.terms[index] = term;

		ExamBean exam = this.exams[index];
		this.examsInTerm.addTermExam(term, exam);
	}

	private void removeExamFromTerm(TermBean term, int examIndex) {
		this.examsInTerm.removeTermExam(term, exams[examIndex]);
	}
	
	/**
	 * Metoda vraca listu kolegija koji imaju ispit u istom terminu
	 * @param term Termin ciji se kolegiji dohvacaju
	 * @return lista ispita u terminu
	 */
	public ExamBean[] getExamsInTerm(TermBean term){
		return this.examsInTerm.getAllExams(term);
	}

	public void setFitness(float f) {
		this.fitness = f;
	}
	
	public float getFitness() {
		return this.fitness;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < exams.length; i++) {
			TermBean term = this.getTerm(i);
			ExamBean exam = exams[i];
			
			sb	.append(term.getDate())
				.append('\t')
				.append(2)
				.append('\t')
				.append(exam.getClassName())
				.append('\t');
			
			String id = Integer.toString(exam.getExamID());
			int nl = id.length();
			for (int j = nl; j < 5; j++) {
				sb.append("0");
			}
			sb.append(id).append('\n');
		}
		
		return sb.toString();
	}
	
	public boolean isFixedIndex(int index) {
		
		for (int i = 0; i < fixedTermIndexes.length; i++) {
			if (fixedTermIndexes[i] == index) return true;
		}
		
		return false;
	}
}
