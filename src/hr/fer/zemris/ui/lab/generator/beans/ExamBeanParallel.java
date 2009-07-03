package hr.fer.zemris.ui.lab.generator.beans;

import java.util.HashSet;
import java.util.Set;

public class ExamBeanParallel extends ExamBean {
	private ExamBean[] exams;
	
	public ExamBeanParallel(ExamBean[] exams, int examID) {
		this.examID = examID;
		this.exams = exams;
		Set<String> studentiSet = new HashSet<String>();
		
		for (int i = 0; i < exams.length; i++) {
			String[] studs = exams[i].getStudents();
			for (int j = 0; j < studs.length; j++) {
				studentiSet.add(studs[j]);
			}
		}
		
		this.students = studentiSet.toArray(new String[0]);
		
		calcStudentsHash();
	}

	@Override
	public String printToString(TermBean term) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < exams.length; i++) {
			sb.append(exams[i].printToString(term));
		}
		
		return sb.toString();
	}
	
	
}
