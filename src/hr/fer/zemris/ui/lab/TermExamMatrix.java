package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Poveznik termina i ispita koji se odvijaju u njemu.
 */
// TODO: Pretraživanje je izvedeno petljama a ne hashevima. Ovo možda i nije takav problem jer se radi o malom broju termina, a hash kolekcije rade ekstra posao koji nam ne treba. 
public class TermExamMatrix {
	private TermExams[] termExams;
	
	public TermExamMatrix(TermBean[] allTerms, int examsNum) {
		this.termExams = new TermExams[allTerms.length];
		
		for (int i = 0; i < allTerms.length; i++) {
			this.termExams[i] = new TermExams(allTerms[i], examsNum);
		}
	}
	
	public void addTermExam(TermBean term, ExamBean exam) {
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				termExams[i].addExam(exam);
			}
		}
	}
	
	public boolean removeTermExam(TermBean term, ExamBean exam) {
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				return termExams[i].removeExam(exam);
			}
		}
		
		return false;
	}
	
	public boolean containsTermExam(TermBean term, ExamBean exam) {
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				return termExams[i].containsExam(exam);
			}
		}
		
		return false;
	}
	
	// TODO: Možda ovdje odmah računati razmake u danima??? Moglo bi biti dobro...
	private class TermExams {
		// TODO: Razmisli treba li ovaj term tu...
		//private TermBean term;
		private ExamBean[] exams;
		private String termID;
		private int termIDhash;
		
		/** Koje mjesto u redu je slobodno (radi bržeg dodavanja) */
		private int emptySlot = 0;
		private int slotsNum;
		
		public TermExams(TermBean term, int examsNum) {
			this.exams = new ExamBean[examsNum];
			this.slotsNum = examsNum;
			//this.term = term;
			this.termID = term.getTermID();
			this.termIDhash = term.getTermID().hashCode();
		}
		
		public boolean wrapsTerm(TermBean term) {
			if (term == null) return false;
			
			return (term.getTermID().hashCode() == termIDhash) && term.getTermID().equals(termID);
		}
		
		public void addExam(ExamBean exam) {
			exams[emptySlot] = exam;
			int start = emptySlot;
			do {
				emptySlot = (emptySlot+1) % slotsNum;
				if (exams[emptySlot] == null) break;
			} while (emptySlot != start);
		}
		
		public boolean removeExam(ExamBean exam) {
			for (int i = 0; i < exams.length; i++) {
				if (exams[i] != null && exams[i].getExamID() == exam.getExamID()) {
					exams[i] = null;
					return true;
				}
			}
			
			return false;
		}
		
		public boolean containsExam(ExamBean exam) {
			for (int i = 0; i < exams.length; i++) {
				if (exams[i] != null && exams[i].getExamID() == exam.getExamID()) {
					return true;
				}
			}
			
			return false;
		}
	}
}
