package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

import java.util.List;

public class Evaluator {

	private ExamsData inputData;
	ConflictMatrix conflictMatrix;

	public Evaluator(ExamsData input, ConflictMatrix m) {
		this.inputData = input;
		this.conflictMatrix = m;
	}

	public float evaluateFitness(Individual timetable) {
		// Ovo je ukradeno iz evaluatora, no license agreement :-P
		
		return 10000 * countConflictedStudents(timetable) +
				10000* countCapacityOverflow(timetable) + 
				4* countStudentsWithExamsInDayDiference(timetable, 0) //ispiti u istom danu
				+ countStudentsWithExamsInDayDiference(timetable, 1); // ispiti u sljedecem danu
	}

	private int countStudentsWithExamsInDayDiference(Individual timetable, int delta) {
		int conflict = 0;
		int termsNum = inputData.getTerms().length;
		TermBean[] terms = inputData.getTerms();
		for (int i = 0; i < termsNum; i++) {
			TermBean outer = terms[i];
			List<ExamBean> examsOuter = timetable.getExamsInTerm(outer);
			
			if (examsOuter == null) continue; // Prazan termin
			
			for (int j = i+1; j < termsNum; j++) {
				TermBean inner = terms[j];
				int dayDiff = Math.abs(outer.getDayIndex()-inner.getDayIndex());
				
				if (dayDiff != delta) continue;
				
				List<ExamBean> examsInner = timetable.getExamsInTerm(inner);
				
				if (examsInner == null) continue;
				for (ExamBean examOuter : examsOuter) {
					for (ExamBean examInner : examsInner) {
						int indexOut = examOuter.index();
						int indexIn = examInner.index();
						conflict += conflictMatrix.shared(indexIn, indexOut);
					}
					
				}
			}
			
		}
		
		return conflict;
	}

	private int countCapacityOverflow(Individual timetable) {
		int sum = 0;
		
		TermBean[] terms = inputData.getTerms();
		for (TermBean term : terms) {
			int students = 0;
			
			List<ExamBean> exams = timetable.getExamsInTerm(term);
			if (exams == null) continue;
			
			for (ExamBean exam : exams) {
				students += exam.getStudents().length;
			}
			
			sum += Math.max(0, students-term.getCapacity());
		}
		
		return sum;
	}

	/**
	 * Metoda izracunava broj studenata kojima se poklapaju ispiti u istom
	 * terminu.
	 * 
	 * @param timetable Primjer rasporeda koji se evaluira
	 * @return broj studenata koji imaju dva ispita u istom terminu
	 */
	private int countConflictedStudents(Individual timetable) {

		TermBean[] terms = inputData.getTerms();
		int conflicted = 0;

		for (int k = 0; k < terms.length; k++) {
			TermBean term = terms[k];
			List<ExamBean> examsInTerm = timetable.getExamsInTerm(term);

			if (examsInTerm == null) {
				// prazan termin
				continue;
			}

			for (int i = 0; i < examsInTerm.size() - 1; i++) {
				int firstIndex = examsInTerm.get(i).index();
				for (int j = i + 1; j < examsInTerm.size(); j++) {
					int secondIndex = examsInTerm.get(j).index();
					conflicted += conflictMatrix.shared(firstIndex, secondIndex);
				}
			}
		}
		//System.out.println("counted "  + conflicted + " conflicted students. Ouch!");
		return conflicted;
	}

}
