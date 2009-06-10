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
	
	private Map<TermBean, List<ExamBean>> examsInTerm;
	
	public Individual(ExamBean[] exams) {
		
		//TODO: Ovi new-ovi bi mogli biti unutar algoritma!
		
		this.terms = new TermBean[exams.length];
		this.exams = exams;
		
		this.examsInTerm = new HashMap<TermBean, List<ExamBean>>(terms.length);
	}
	
	public TermBean getTerm(int index) {
		return terms[index];
	}
	
	public void setTerm(int index, TermBean term) {
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
	
	/**
	 * Metoda vraca listu kolegija koji imaju ispit u istom terminu
	 * @param term Termin ciji se kolegiji dohvacaju
	 * @return lista ispita u terminu
	 */
	public List<ExamBean> getExamsInTerm(TermBean term){
		return this.examsInTerm.get(term);
	}

	public void swapTerms() {
		
	}
	
	public void mutate() {
		
	}
}
