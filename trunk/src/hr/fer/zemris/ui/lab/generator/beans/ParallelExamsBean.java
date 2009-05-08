package hr.fer.zemris.ui.lab.generator.beans;

import org.apache.commons.lang.StringUtils;

/**
 * Opisnik podataka za ispite koji se moraju odvijati
 * u isto vrijeme.
 * 
 * @author Ivan Krišto
 */
public class ParallelExamsBean {
	private int[] examIDs;
	
	public ParallelExamsBean() {
	}
	
	public ParallelExamsBean(String in) {
		String[] parts = StringUtils.split(in, '#');
		
		examIDs = new int[parts.length];
		
		for (int i = 0; i < examIDs.length; i++) {
			if (parts[i] == null) throw new IllegalArgumentException("Nije definiran ID ispita!");
			if (!StringUtils.isNumeric(parts[i])) throw new IllegalArgumentException("Nije definiran ID ispita!");
			int examID = Integer.parseInt(parts[i]);
			setExamIDs(i, examID);
		}
	}
	
	public int[] getExamIDs() {
		return examIDs;
	}

	public void setExamIDs(int[] examIDs) {
		this.examIDs = examIDs;
	}

	public int getExamIDs(int index) {
		return examIDs[index];
	}

	public void setExamIDs(int index, int examID) {
		this.examIDs[index] = examID;
	}
	
}
