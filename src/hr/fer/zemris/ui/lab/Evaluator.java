package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

import java.util.List;

public class Evaluator {
	
	private ExamsData inputData;
	ConflictMatrix conflictMatrix;
	
	public Evaluator(ExamsData input, ConflictMatrix m){
		this.inputData = input;
		this.conflictMatrix = m;
	}
	
	
	
	public float evaluateFitness(Individual timetable) {
		// Ovo je ukradeno iz evaluatora, no license agreement :-P
		
		if(isEveryCourseInAcceptableTerm(timetable) == false
				|| isCourseClustersTogether(timetable) == false
				|| isEveryFixedCourseInFixedTerm(timetable) == false)
				{
					return Float.MAX_VALUE; 
				}
		return 10000*countConflictedStudents(timetable)
			+ 10000*countCapacityOverflow(timetable)
			+ 4*countStudentsWithExamsInDayDiference(timetable, 0) //ispiti u istom danu
			+ countStudentsWithExamsInDayDiference(timetable, 1); // ispiti u sljedecem danu
	}

	private boolean isEveryFixedCourseInFixedTerm(Individual timetable) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isCourseClustersTogether(Individual timetable) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isEveryCourseInAcceptableTerm(Individual timetable) {
		// TODO Auto-generated method stub
		return true;
	}

	private int countStudentsWithExamsInDayDiference(Individual timetable, int delta) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int countCapacityOverflow(Individual timetable) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Metoda izracunava broj studenata kojima se poklapaju ispiti u istom terminu.
	 * @param timetable Primjer rasporeda koji se evaluira
	 * @return broj studenata koji imaju dva ispita u istom terminu
	 */
	private int countConflictedStudents(Individual timetable) {
		
		TermBean[] terms = inputData.getTerms();
		int conflicted = 0;
		
		for(int k = 0; k < terms.length; k++)
		{
			TermBean term = terms[k];
			List <ExamBean> examsInTerm = timetable.getExamsInTerm(term);
			
			if(examsInTerm == null) // prazan termin
			{
				continue;
			}
			
			for (int i = 0; i < examsInTerm.size()-1; i++) {
				
				int firstIndex = examsInTerm.get(i).index();
				
				for(int j = i+1; j < examsInTerm.size(); j++)
				{
					int secondIndex = examsInTerm.get(j).index();
					
					conflicted += conflictMatrix.shared(firstIndex,secondIndex);
				}
			}
		}
		//System.out.println("counted "  + conflicted + " conflicted students. Ouch!");
		return conflicted;
	}

}
