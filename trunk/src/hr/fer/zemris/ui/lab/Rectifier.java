package hr.fer.zemris.ui.lab;

import java.util.Random;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.AllowedTermsBean;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class Rectifier {
	
	private ExamBean[] exams;
	private AllowedTermsBean[] allowedTerms;
	private TermBean[] allTerms;
	
	private Random randomGenerator;
	
	public Rectifier(ExamsData data){
		this.exams = data.getExams();
		this.allowedTerms = data.getAllowedTerms();
		this.allTerms = data.getTerms();
		this.randomGenerator = new Random();
		
		/*
		System.out.println("Stvoren ispravljac...");
		for (int i = 0; i < allowedTerms.length; i++) {
			System.out.println(allowedTerms[i].getExamID());
			
		}
		*/
	}
	
	public void rectifie(Individual individual){
		TermBean[] solution = individual.getSolutionTerms();
		
		for (int i = 0; i < solution.length; i++) {
			TermBean term = solution[i];
			ExamBean exam = exams[i];
			
			AllowedTermsBean aTerm = getExamInAllowed(exam);
			
			if (aTerm == null) continue; //nije jedan od ovih
			
			boolean inAllowedTerm = false;
			String[] allowedIDs = aTerm.getTermIDs();
			
			for (int j = 0; j < allowedIDs.length; j++) {
				if ( term.getTermID().equals(allowedIDs[j])){
					  inAllowedTerm = true;
					  break;
				}
			}
			
			if (!inAllowedTerm){
				int allowedIndex = randomGenerator.nextInt(allowedIDs.length);
				TermBean newTerm = getTermByID(allowedIDs[allowedIndex]);
				
				if (newTerm == null) 
					throw new IllegalArgumentException("Greska. Termin s ID = " +allowedIDs[0] + " nije pronadjen u podatcima!");
				
				individual.setTerm(i, newTerm);
			}
		}
	}

	private TermBean getTermByID(String ID) {
		for (int i = 0; i < allTerms.length; i++) {
			if ( allTerms[i].getTermID().equals(ID) ){
				return allTerms[i];
			}
		}
		return null;
	}

	private AllowedTermsBean getExamInAllowed(ExamBean exam) {
		
		for (int i = 0; i < allowedTerms.length; i++) {
			AllowedTermsBean aTerm = allowedTerms[i];
			if (exam.getExamID() == aTerm.getExamID()){
				return aTerm;
			}
		}
		return null;
	}

}
