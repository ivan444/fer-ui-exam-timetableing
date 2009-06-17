package hr.fer.zemris.ui.lab.generator.beans;

import org.apache.commons.lang.StringUtils;

public class TermBean {
	
	private String date;
	
	private int capacity;
	
	/** Index dana u intervalu (da bi se izbjegla aritmetika datuma). */
	private int dayIndex;
	
	private String termID;
	
	public TermBean() {
	}
	
	public TermBean(String in) {
		String[] parts = StringUtils.split(in, '#');
		
		setDate(parts[0]);
		
		if (parts[1] == null) throw new IllegalArgumentException("Nije definiran kapacitet!");
		if (!StringUtils.isNumeric(parts[1])) throw new IllegalArgumentException("Nije definiran kapacitet!");
		int capacity = Integer.parseInt(parts[1]);
		setCapacity(capacity);
		
		if (parts[2] == null) throw new IllegalArgumentException("Nije definiran neki broj za koji ne znam što je!");
		if (!StringUtils.isNumeric(parts[2])) throw new IllegalArgumentException("Nije definiran neki broj za koji ne znam što je");
		int num = Integer.parseInt(parts[2]);
		setDayIndex(num);
		
		setTermID(parts[3].trim());
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}

	public String getTermID() {
		return termID;
	}

	public void setTermID(String termID) {
		this.termID = termID;
	}
	
	public String toString() {
		return "" + termID + date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((termID == null) ? 0 : termID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermBean other = (TermBean) obj;
		if (termID == null) {
			if (other.termID != null)
				return false;
		} else if (!termID.equals(other.termID))
			return false;
		return true;
	}
	
	
}
