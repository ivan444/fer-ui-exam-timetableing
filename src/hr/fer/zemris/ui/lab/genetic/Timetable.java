package hr.fer.zemris.ui.lab.genetic;

/**
 * Ovo je rjesenje, primjerak rasporeda, jedinka u genetskom algoritmu.
 *
 */
public class Timetable {
	
	private TermList terms;
	private int fitness;
	
	public Timetable(){
		terms = new TermList(12);
		//kad najefikasnije stvorit termine, ako ih uvijek ima 12 mozemo i tu odmah
	}
	
	public boolean addTerm(Term term){
		return this.terms.add(term);
	}
	
	public int fitness(){
		return this.fitness;
	}
	
	// ideja je ove metode da vrati string reprezentaciju
	// koja ce biti direktno spremna za ispis u datoteku
	// a pozvala bi se iz onog timetable koji nadjemo da je najbolji u 
	// tom trenutku. pitanje gdje smjestiti ovaj mehanizam, ovdje ili negdje
	// drugdje (nacelo jedinstvene odgovornosti :))
	public String toSolutionString(){
		return "nista";
	}
	
	

}
