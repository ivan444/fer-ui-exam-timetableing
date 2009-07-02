package hr.fer.zemris.ui.lab.generator.beans;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * Opisnik ispita.
 * 
 * @author Ivan Krišto
 * 
 */
// FIXME: Test više ne valja jer se studenti sortiraju po hashu JMBAG-a!
public class ExamBean {
	private int examID;
	private String className;
	private String[] students;
	private int[] studentsHash;

	private int index;

	public ExamBean() {
	}

	public ExamBean(String in) {
		String[] parts = StringUtils.split(in, '#');

		if (parts[0] == null)
			throw new IllegalArgumentException("Nije definiran ID ispita!");
		if (!StringUtils.isNumeric(parts[0]))
			throw new IllegalArgumentException("Nije definiran ID ispita!");
		;
		int examID = Integer.parseInt(parts[0]);
		setExamID(examID);

		setClassName(parts[1]);
		
		String[] students = StringUtils.split(parts[2], ',');
		// Uklanjanje oznake kraja reda ili krajnjih razmaka ako postoje.
		students[students.length - 1] = students[students.length - 1].trim();
		setStudents(students);

		calcStudentsHash();
	}

	/**
	 * Računa hash niza JMBAG-ova studenata i pohranjuje ih u novi niz.
	 */
	private void calcStudentsHash() {
		studentsHash = new int[students.length];
		for (int i = 0; i < students.length; i++) {
			String num = "0x" + students[i].substring(2, 9);
			String xorS = "0x" + students[i].substring(0, 2);
			int hash = Integer.decode(num).intValue();
			int xor = Integer.decode(xorS).intValue() << 20;

			hash ^= xor;

			studentsHash[i] = hash;
		}
		sortStudents();
	}

	public boolean containsStudent(int hash) {
		return Arrays.binarySearch(studentsHash, hash) >= 0;
	}
	
	/**
	 * Quick sort studenata i njihovih hasheva.
	 * 
	 * Drpio sa: http://www.inf.fh-flensburg.de/lang/algorithmen/sortieren/quick/quicken.htm
	 * 
	 * @param a Niz
	 * @param lo lower index of the region of array a that is to be sorted
	 * @param hi upper index of the region of array a that is to be sorted
	 */
	void quicksort(int[] a, int lo, int hi) {
		int i = lo, j = hi, h;
		int x = a[(lo + hi) / 2];

		//  partition
		do {
			while (a[i] < x)
				i++;
			while (a[j] > x)
				j--;
			if (i <= j) {
				String swapS = students[i];
				students[i] = students[j];
				students[j] = swapS;
				
				h = a[i];
				a[i] = a[j];
				a[j] = h;
				i++;
				j--;
			}
		} while (i <= j);

		//  recursion
		if (lo < j)
			quicksort(a, lo, j);
		if (i < hi)
			quicksort(a, i, hi);
	}

	private void sortStudents() {
		quicksort(studentsHash, 0, studentsHash.length-1);
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
		if (students == null)
			return false;
		if (index >= students.length)
			return false;
		if (index < 0)
			return false;

		students[index] = student;
		return true;
	}

	public int[] getStudentsHash() {
		return studentsHash;
	}

	@Override
	// TODO: Provjeri je li format i dalje ok (tj. jesam li dobro ovo prepravio... Ivan...)
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.className).append(" (");
		String broj = Integer.toString(this.examID);
		int nl = broj.length();
		for (int j = nl; j < 5; j++) {
			sb.append("0");
		}
		sb.append(broj).append(")");

		return sb.toString();
	}

	public void setIndex(int i) {
		this.index = i;

	}

	public int index() {
		return this.index;
	}
}
