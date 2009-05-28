package hr.fer.zemris.ui.lab.genetic;

import java.util.ArrayList;
import java.util.List;


/**
 * Isto ako i za TermList. Stavio sam cisto trenutno kao ovojnicu
 * tako da lakse zamijenimo. Nisam siguran oce nam biti isto kao i 
 * lista termina, pa ce mozda obje klase biti iste....
 *
 */
public class ExamList {
	
private List<Exam> examList;
	
	public ExamList(){
		this.examList = new ArrayList<Exam>();
	}
	
	public ExamList(int size){
		this.examList = new ArrayList<Exam>(size);
	}
	
	public boolean add(Exam exam){
		return this.examList.add(exam);
	}

	public Exam get(int i){
		return this.examList.get(i);
	}


}
