package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class AIDS extends Killer {

	
	public AIDS(ExamsData input, float deathProbability){
		super(input, deathProbability);
	}
	@Override
	public void kill(Individual ind) {
		
		if (Math.random() < deathProbability) {
			
			TermBean[] terms = this.data.getTerms();
			int termsNum = terms.length;
			ExamBean[] exams = this.data.getExams();
			int examsNum = exams.length;
			
			for (int i = 0; i < examsNum; i++) {
				int index = (int) Math.round(Math.random() * (termsNum - 1));
				ind.setTerm(i, data.getTerms()[index]);
			}
		}
	}

}
