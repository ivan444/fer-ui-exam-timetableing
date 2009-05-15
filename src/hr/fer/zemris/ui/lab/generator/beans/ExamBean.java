package hr.fer.zemris.ui.lab.generator.beans;

import org.apache.commons.lang.StringUtils;

/**
 * Opisnik ispita.
 * 
 * @author Ivan Krišto
 *
 */
public class ExamBean {
	private int examID;
	private String className;
	private String[] students;
	
	public ExamBean() {
	}
	
	public ExamBean(String in) {
		String[] parts = StringUtils.split(in, '#');
		
		if (parts[0] == null) throw new IllegalArgumentException("Nije definiran ID ispita!");
		if (!StringUtils.isNumeric(parts[0])) throw new IllegalArgumentException("Nije definiran ID ispita!");;
		int examID = Integer.parseInt(parts[0]);
		setExamID(examID);
		
		setClassName(parts[1]);
		
		String[] students = StringUtils.split(parts[2], ',');
		// Uklanjanje oznake kraja reda ili krajnjih razmaka ako postoje.
		students[students.length-1] = students[students.length-1].trim();
		setStudents(students);
	}
	
	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getStudents() {
		return students;
	}

	public void setStudents(String[] students) {
		this.students = students;
	}
	
	public boolean addStudent(int index, String student) {
		if (students == null) return false;
		if (index >= students.length) return false;
		if (index < 0) return false;
		
		students[index] = student;
		return true;
	}
}