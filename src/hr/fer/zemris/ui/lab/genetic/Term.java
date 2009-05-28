package hr.fer.zemris.ui.lab.genetic;

public class Term {
	
	private ExamList exams;
	private int fitness;
	
	public Term(){
		exams = new ExamList();
	}
	
	public boolean addExam(Exam exam){
		return this.exams.add(exam);
	}
	
	public int fitness(){
		//pitanje ocemo li dobrotu traziti prilikom poziva funkcije ili mora vec biti izracunata!
		return this.fitness;
	}

}
