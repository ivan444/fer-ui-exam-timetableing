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

	// TODO: dodati term day difference matricu
	private Map<TermBean, List<ExamBean>> examsInTerm;
	
	private float fitness;
	
	public Individual(ExamBean[] exams) {
		this.terms = new TermBean[exams.length];
		this.exams = exams;
		
		this.examsInTerm = new HashMap<TermBean, List<ExamBean>>(terms.length);
	}
	
	public TermBean getTerm(int index) {
		return terms[index];
	}
	
	public TermBean[] getSolutionTerms(){
		return this.terms;
	}
	
	public void setTerm(int index, TermBean term) {
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

	public void swapTerms() {
		
	}
	
	public void mutate(float mutationFactor, TermBean[] allTerms) {
		//int sum = 0;
		for (int i = 0; i < terms.length; i++) {
			if (Math.random() < mutationFactor) {
				int swpIndex = (int) Math.round(Math.random()*(allTerms.length-1));
				setTerm(i, allTerms[swpIndex]);
			//	sum++;
			}
		}
		//System.out.println("Mutirao jedinku " + sum + " puta!");
	}

	public void setFitness(float f) {
		this.fitness = f;
	}
	
	public float fitness(){
		return this.fitness;
	}
}
