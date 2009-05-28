package hr.fer.zemris.ui.lab.genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Stavio sam ovu klasu kao omotac jer si rekao da cemo napraviti svoje kolekcije zbog efikasnosti,
 * privremeno je obicna lista, ilitiga radimo Agile Programming, 
 * pa da mozemo ovo privremeno koristiti
 *
 */
public class TermList {
	
	private List<Term> termList;
	
	public TermList(){
		this.termList = new ArrayList<Term>();
	}
	
	public TermList(int size){
		this.termList = new ArrayList<Term>(size);
	}
	
	public boolean add(Term term){
		return this.termList.add(term);
	}

	public Term get(int i){
		return this.termList.get(i);
	}
}
