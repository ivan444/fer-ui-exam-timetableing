package hr.fer.zemris.ui.lab.generator.beans;

import org.apache.commons.lang.StringUtils;

/**
 * Opisnik podataka za ispite sa fiksnim terminom.
 * 
 * @author Ivan Krišto
 *
 */
public class FixedTermBean {
	private String date;
	private int examID;
	
	public FixedTermBean() {
	}
	
	public FixedTermBean(String in) {
		String[] parts = StringUtils.split(in, '#');
		
		setDate(parts[0]);
		
		if (parts[1] == null) throw new IllegalArgumentException("Nije definiran ID ispita!");
		if (!StringUtils.isNumeric(parts[1])) throw new IllegalArgumentException("Nije definiran ID ispita!");
		int examID = Integer.parseInt(parts[1]);
		setExamID(examID);
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}
}
