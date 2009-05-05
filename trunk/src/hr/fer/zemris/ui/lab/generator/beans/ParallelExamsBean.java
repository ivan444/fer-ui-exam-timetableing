package hr.fer.zemris.ui.lab.generator.beans;

import org.apache.commons.lang.StringUtils;

/**
 * Opisnik podataka za ispite koji se moraju odvijati
 * u isto vrijeme.
 * 
 * @author Ivan Krišto
 */
public class ParallelExamsBean {
	private int examIDa;
	private int examIDb;
	
	public ParallelExamsBean() {
	}
	
	public ParallelExamsBean(String in) {
		String[] parts = StringUtils.split(in, '#');
		
		if (parts[0] == null) throw new IllegalArgumentException("Nije definiran ID ispita!");
		if (!StringUtils.isNumeric(parts[0])) throw new IllegalArgumentException("Nije definiran ID ispita!");
		int examIDa = Integer.parseInt(parts[0]);
		setExamIDa(examIDa);
		
		if (parts[1] == null) throw new IllegalArgumentException("Nije definiran ID ispita!");
		if (!StringUtils.isNumeric(parts[1])) throw new IllegalArgumentException("Nije definiran ID ispita!");
		int examIDb = Integer.parseInt(parts[1]);
		setExamIDb(examIDb);
	}
	
	public int getExamIDa() {
		return examIDa;
	}

	public void setExamIDa(int examIDa) {
		this.examIDa = examIDa;
	}

	public int getExamIDb() {
		return examIDb;
	}

	public void setExamIDb(int examIDb) {
		this.examIDb = examIDb;
	}
	
}
