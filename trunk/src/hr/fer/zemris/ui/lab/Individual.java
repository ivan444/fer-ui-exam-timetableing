package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Individual {
	private TermBean[] terms;
	
	public Individual(int examsNum) {
		this.terms = new TermBean[examsNum];
	}
	
	public TermBean getTerm(int index) {
		return terms[index];
	}
	
	public void setTerm(int index, TermBean term) {
		this.terms[index] = term;
	}
	
	public void swapTerms() {
		
	}
	
	public void mutate() {
		
	}
}
