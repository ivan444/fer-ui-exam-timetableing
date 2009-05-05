package hr.fer.zemris.ui.lab.generator.beans;

import org.apache.commons.lang.StringUtils;

/**
 * Opisnik podataka za ispit koji ima striktno određene
 * moguće termine.
 * Tj. ispit može biti samo u zadanim terminima.
 * 
 * @author Ivan Krišto
 *
 */
public class AllowedTermsBean {
	private int examID;
	private String[] termIDs;
	
	public AllowedTermsBean() {
	}
	
	public AllowedTermsBean(String in) {
		String[] parts = StringUtils.split(in, '#');
		
		if (parts[0] == null) throw new IllegalArgumentException("Nije definiran ID ispita!");
		if (!StringUtils.isNumeric(parts[0])) throw new IllegalArgumentException("Nije definiran ID ispita!");
		int examID = Integer.parseInt(parts[0]);
		setExamID(examID);
		
		String[] termIDs = StringUtils.split(parts[1], ',');
		// Uklanjanje oznake kraja reda ili krajnjih razmaka ako postoje.
		termIDs[termIDs.length-1] = termIDs[termIDs.length-1].trim();
		setTermIDs(termIDs);
	}
	
	public int getExamID() {
		return examID;
	}
	
	public void setExamID(int examID) {
		this.examID = examID;
	}

	public String[] getTermIDs() {
		return termIDs;
	}

	public void setTermIDs(String[] termIDs) {
		this.termIDs = termIDs;
	}
	
	public boolean addTerm(int index, String termID) {
		if (termIDs == null) return false;
		if (index >= termIDs.length) return false;
		if (index < 0) return false;
		
		termIDs[index] = termID;
		return true;
	}
}
