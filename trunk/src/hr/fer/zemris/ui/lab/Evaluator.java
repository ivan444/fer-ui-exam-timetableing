package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Evaluator {

	private ExamsData inputData;
	ConflictMatrix conflictMatrix;

	public Evaluator(ExamsData input, ConflictMatrix m) {
		this.inputData = input;
		this.conflictMatrix = m;
	}

	public float evaluateFitness(Individual timetable) {
		// Ovo je ukradeno iz evaluatora, no license agreement :-P

		if (isEveryCourseInAcceptableTerm(timetable) == false
				|| isCourseClustersTogether(timetable) == false) {
			return Float.MAX_VALUE;
		}
		return 10000 * countConflictedStudents(timetable) + 10000
				* countCapacityOverflow(timetable) + 4
				* countStudentsWithExamsInDayDiference(timetable, 0) //ispiti u istom danu
				+ countStudentsWithExamsInDayDiference(timetable, 1); // ispiti u sljedecem danu
	}

	private boolean isCourseClustersTogether(Individual timetable) {
		// TODO: Do implement, please
		return true;
	}

	private boolean isEveryCourseInAcceptableTerm(Individual timetable) {
		// TODO: Do implement, please
		return true;
	}

	private int countStudentsWithExamsInDayDiference(Individual timetable, int delta) {
		int conflict = 0;
		int termsNum = inputData.getTerms().length;
		TermBean[] terms = inputData.getTerms();
		for (int i = 0; i < termsNum; i++) {
			TermBean outer = terms[i];
			ExamBean[] examsOuter = timetable.getExamsInTerm(outer);
			
			for (int j = i+1; j < termsNum; j++) {
				TermBean inner = terms[j];
				int dayDiff = Math.abs(outer.getDayIndex()-inner.getDayIndex());
				
				if (dayDiff != delta) continue;
				
				ExamBean[] examsInner = timetable.getExamsInTerm(inner);
				
				for (ExamBean examOuter : examsOuter) {
					if (examOuter == null) break;
					for (ExamBean examInner : examsInner) {
						if (examInner == null) break;
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
			ExamBean[] exams = timetable.getExamsInTerm(term);
			
			for (ExamBean exam : exams) {
				if (exam == null) break;
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
			ExamBean[] examsInTerm = timetable.getExamsInTerm(term);

			for (int i = 0; i < examsInTerm.length - 1; i++) {
				if (examsInTerm[i] == null) break;
				int firstIndex = examsInTerm[i].index();
				for (int j = i + 1; j < examsInTerm.length; j++) {
					if (examsInTerm[j] == null) break;
					int secondIndex = examsInTerm[j].index();
					conflicted += conflictMatrix.shared(firstIndex, secondIndex);
				}
			}
		}
		return conflicted;
	}

}
