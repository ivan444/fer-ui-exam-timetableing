package hr.fer.zemris.ui.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Individual {
	private TermBean[] terms;
	private ExamBean[] exams;

	private int[] fixedTermIndexes;
	
	// TODO: dodati term day difference matricu
	private Map<TermBean, List<ExamBean>> examsInTerm;
	
	private float fitness;
	
	public Individual(ExamBean[] exams, int[] fixedTerms) {
		this.terms = new TermBean[exams.length];
		this.exams = exams;
		this.fixedTermIndexes = fixedTerms;
		
		this.examsInTerm = new HashMap<TermBean, List<ExamBean>>(terms.length);
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

		List<ExamBean> examList = this.examsInTerm.get(term);
		
		// i ovaj new bi trebalo izbaciti van. mogli bi prije algoritma
		// instancirati sve lista svih termina pa onda ova provjera nije ni potreba!
		if (examList == null){
			examList = new ArrayList<ExamBean>();
			this.examsInTerm.put(term,examList);
			
		}
		ExamBean exam = this.exams[index];
		examList.add(exam);
	}
	
	public void setFixedTerm(int index, TermBean term) {
		
		if (!isFixedIndex(index)) throw new IllegalArgumentException("Pokusaj ilegalnog ubacivanja u fiksni termin!");
		
		TermBean oldTerm = terms[index];
		if (oldTerm != null) {
			removeExamFromTerm(oldTerm, index);
		}
		
		this.terms[index] = term;

		List<ExamBean> examList = this.examsInTerm.get(term);
		
		if (examList == null){
			examList = new ArrayList<ExamBean>();
			this.examsInTerm.put(term,examList);
			
		}
		ExamBean exam = this.exams[index];
		examList.add(exam);
	}

	private void removeExamFromTerm(TermBean term, int examIndex) {
		List<ExamBean> examList = this.examsInTerm.get(term);
		int toRemove = -1;
		for (int i = 0; i < examList.size(); i++) {
			if (examList.get(i).index() == examIndex) {
				toRemove = i;
				break;
			}
			
		}
		
		examList.remove(toRemove);
	}
	
	/**
	 * Metoda vraca listu kolegija koji imaju ispit u istom terminu
	 * @param term Termin ciji se kolegiji dohvacaju
	 * @return lista ispita u terminu
	 */
	public List<ExamBean> getExamsInTerm(TermBean term){
		return this.examsInTerm.get(term);
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
