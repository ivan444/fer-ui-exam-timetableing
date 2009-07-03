package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Poveznik termina i ispita koji se odvijaju u njemu.
 */
// TODO: Pretraživanje je izvedeno petljama a ne hashevima. Ovo možda i nije takav problem jer se radi o malom broju termina, a hash kolekcije rade ekstra posao koji nam ne treba. 
public class TermExamMatrix {
	private TermExams[] termExams;
	private int cachedIndex;
	
	public TermExamMatrix(TermBean[] allTerms, int examsNum) {
		this.termExams = new TermExams[allTerms.length];
		cachedIndex = 0;
		
		for (int i = 0; i < allTerms.length; i++) {
			this.termExams[i] = new TermExams(allTerms[i], examsNum);
		}
	}
	
	public void addTermExam(TermBean term, ExamBean exam) {
		if (termExams[cachedIndex].wrapsTerm(term)) {
			termExams[cachedIndex].addExam(exam);
			return;
		}
		
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				termExams[i].addExam(exam);
				cachedIndex = i;
			}
		}
	}
	
	public boolean removeTermExam(TermBean term, ExamBean exam) {
		if (termExams[cachedIndex].wrapsTerm(term)) {
			return termExams[cachedIndex].removeExam(exam);
		}
		
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				cachedIndex = i;
				return termExams[i].removeExam(exam);
			}
		}
		
		return false;
	}
	
	public boolean containsTermExam(TermBean term, ExamBean exam) {
		if (termExams[cachedIndex].wrapsTerm(term)) {
			return termExams[cachedIndex].containsExam(exam);
		}
		
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				cachedIndex = i;
				return termExams[i].containsExam(exam);
			}
		}
		
		return false;
	}
	
	public ExamBean[] getAllExams(TermBean term) {
		if (termExams[cachedIndex].wrapsTerm(term)) {
			return termExams[cachedIndex].getAllExams();
		}
		
		for (int i = 0; i < termExams.length; i++) {
			if (termExams[i].wrapsTerm(term)) {
				cachedIndex = i;
				return termExams[i].getAllExams();
			}
		}
		
		return null;
	}
	
	// TODO: Možda ovdje odmah računati razmake u danima??? Moglo bi biti dobro...
	private class TermExams {
		private ExamBean[] exams;
		private String termID;
		private int termIDhash;
		
		/** Koje mjesto u redu je slobodno (radi bržeg dodavanja) */
		private int emptySlot;
		
		public TermExams(TermBean term, int examsNum) {
			this.exams = new ExamBean[examsNum];
			this.termID = term.getTermID();
			this.termIDhash = term.getTermID().hashCode();
			this.emptySlot = 0;
		}
		
		public boolean wrapsTerm(TermBean term) {
			if (term == null) return false;
			
			return (term.getTermID().hashCode() == termIDhash) && term.getTermID().equals(termID);
		}
		
		public ExamBean[] getAllExams() {
			return exams;
		}
		
		public void addExam(ExamBean exam) {
			exams[emptySlot] = exam;
			emptySlot = emptySlot+1;
		}
		
		public boolean removeExam(ExamBean exam) {
			for (int i = 0; i < exams.length; i++) {
				if (exams[i] == null) return false;
				if (exams[i].getExamID() == exam.getExamID()) {
					exams[i] = null;
					while (i+1 < exams.length && exams[i+1] != null) {
						exams[i] = exams[i+1];
						i++;
					}
					
					if (i+1 != exams.length) {
						exams[i+1] = null;
					}
					
					emptySlot--;
					
					return true;
				}
			}
			
			return false;
		}
		
		public boolean containsExam(ExamBean exam) {
			for (int i = 0; i < exams.length; i++) {
				if (exams[i] == null) return false;
				if (exams[i].getExamID() == exam.getExamID()) {
					return true;
				}
			}
			
			return false;
		}
	}
}
