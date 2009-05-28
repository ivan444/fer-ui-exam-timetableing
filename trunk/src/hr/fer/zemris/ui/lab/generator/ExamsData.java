package hr.fer.zemris.ui.lab.generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;

import hr.fer.zemris.ui.lab.generator.beans.AllowedTermsBean;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.FixedTermBean;
import hr.fer.zemris.ui.lab.generator.beans.ParallelExamsBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class ExamsData {
	private ExamBean[] exams;
	private TermBean[] terms;
	private FixedTermBean[] fixedTerms;
	private ParallelExamsBean[] parallelExams;
	private AllowedTermsBean[] allowedTerms;
	
	// TODO: Srediti obradu exceptiona! Većinu zanemariti osim bounds i arguments...
	public ExamsData(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		int startLine = 1;
		int num;
		try {
			num = readNum(reader, startLine);
			startLine++;
			exams = new ExamBean[num];	// FIXME: Bio je problem sa korištenjem reflectiona... nisam htio bacati još vremena.
			startLine = load(reader, startLine, num, exams, "hr.fer.zemris.ui.lab.generator.beans.ExamBean");
			
			num = readNum(reader, startLine);
			startLine++;
			terms = new TermBean[num];	// FIXME: Bio je problem sa korištenjem reflectiona... nisam htio bacati još vremena.
			startLine = load(reader, startLine, num, terms, "hr.fer.zemris.ui.lab.generator.beans.TermBean");
			
			num = readNum(reader, startLine);
			startLine++;
			fixedTerms = new FixedTermBean[num];	// FIXME: Bio je problem sa korištenjem reflectiona... nisam htio bacati još vremena.
			startLine = load(reader, startLine, num, fixedTerms, "hr.fer.zemris.ui.lab.generator.beans.FixedTermBean");
			
			num = readNum(reader, startLine);
			startLine++;
			parallelExams = new ParallelExamsBean[num];	// FIXME: Bio je problem sa korištenjem reflectiona... nisam htio bacati još vremena.
			startLine = load(reader, startLine, num, parallelExams, "hr.fer.zemris.ui.lab.generator.beans.ParallelExamsBean");
			
			num = readNum(reader, startLine);
			startLine++;
			allowedTerms = new AllowedTermsBean[num];	// FIXME: Bio je problem sa korištenjem reflectiona... nisam htio bacati još vremena.
			startLine = load(reader, startLine, num, allowedTerms, "hr.fer.zemris.ui.lab.generator.beans.AllowedTermsBean");
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		reader.close();
		
	}
	
	// TODO: Mislim da zakomentirane metode ipak ne trebaju.
//	public boolean addExam(int index, ExamBean exam) {
//		if (exams == null) return false;
//		if (index >= exams.length) return false;
//		if (index < 0) return false;
//		
//		exams[index] = exam;
//		return true;
//	}
//	
//	public boolean addTerm(int index, TermBean term) {
//		if (terms == null) return false;
//		if (index >= terms.length) return false;
//		if (index < 0) return false;
//		
//		terms[index] = term;
//		return true;
//	}
//	
//	public boolean addFixedTerm(int index, FixedTermBean fTerm) {
//		if (fixedTerms == null) return false;
//		if (index >= fixedTerms.length) return false;
//		if (index < 0) return false;
//		
//		fixedTerms[index] = fTerm;
//		return true;
//	}
//	
//	public boolean addParallelExams(int index, ParallelExamsBean pExams) {
//		if (parallelExams == null) return false;
//		if (index >= parallelExams.length) return false;
//		if (index < 0) return false;
//		
//		parallelExams[index] = pExams;
//		return true;
//	}
//	
//	public boolean addAllowedTerms(int index, AllowedTermsBean aTerms) {
//		if (allowedTerms == null) return false;
//		if (index >= allowedTerms.length) return false;
//		if (index < 0) return false;
//		
//		allowedTerms[index] = aTerms;
//		return true;
//	}
	
	public ExamBean[] getExams() {
		return exams;
	}

//	public void setExams(ExamBean[] exams) {
//		this.exams = exams;
//	}

	public TermBean[] getTerms() {
		return terms;
	}

//	public void setTerms(TermBean[] terms) {
//		this.terms = terms;
//	}

	public FixedTermBean[] getFixedTerms() {
		return fixedTerms;
	}

//	public void setFixedTerms(FixedTermBean[] fixedTerms) {
//		this.fixedTerms = fixedTerms;
//	}

	public ParallelExamsBean[] getParallelExams() {
		return parallelExams;
	}

//	public void setParallelExams(ParallelExamsBean[] parallelExams) {
//		this.parallelExams = parallelExams;
//	}

	public AllowedTermsBean[] getAllowedTerms() {
		return allowedTerms;
	}

//	public void setAllowedTerms(AllowedTermsBean[] allowedTerms) {
//		this.allowedTerms = allowedTerms;
//	}
	
	private int readNum(BufferedReader reader, int startLine) throws IOException {
		String line = null;
		
		// Učitaj broj ispita
		line = reader.readLine();
		if (line == null) {
			throw new IllegalArgumentException("Linija " + startLine + ": Neispravan format ulazne datoteke! " +
					"Nije zadan broj unosa!");
		}
		if (!StringUtils.isNumeric(line)) {
			throw new IllegalArgumentException("Linija " + startLine + ": Neispravan format ulazne datoteke! " +
					"Nije zadan broj unosa (neispravan format unosa)!");
		}
		
		return Integer.parseInt(line);
	}
	
	@SuppressWarnings("unchecked")
	public <T> int load(BufferedReader reader, int startLine, int num, T[] array, String className)
		throws IOException, IllegalArgumentException, InstantiationException,
		IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		
		String line = null;
		
//		// Učitaj broj ispita
//		line = reader.readLine();
//		if (line == null) {
//			throw new IllegalArgumentException("Linija " + startLine + ": Neispravan format ulazne datoteke! " +
//					"Nije zadan broj unosa!");
//		}
//		if (!StringUtils.isNumeric(line)) {
//			throw new IllegalArgumentException("Linija " + startLine + ": Neispravan format ulazne datoteke! " +
//					"Nije zadan broj unosa (neispravan format unosa)!");
//		}
//		int num = Integer.parseInt(line);
		
		// Rezerviraj memoriju za ispite
		Class<T> Tclass = (Class<T>) Class.forName(className);
		Constructor<T> strTConstruct = null;
		Constructor<T>[] constructors = (Constructor<T>[]) Tclass.getConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Class<?>[] params = constructors[i].getParameterTypes();
			if (params.length != 1) continue;
			// FIXME: Ovdje bi trebala provjera je li taj parametar string.. ali, ne prolazi mi ispitivanje...
			strTConstruct = constructors[i];
		}
//		array = (T[]) Array.newInstance(Tclass, num);
		
		// Učitaj sve podatke za ispite
		for (int i = 0; i < num; i++) {
			line = reader.readLine();
			if (line == null) {
				throw new IllegalArgumentException("Linija " + (startLine + i + 1) + ".: Neispravan " +
						"format ulazne datoteke (datoteka je naglo završila)!");
			}
			T bean = strTConstruct.newInstance(line);
			if (bean == null) {
				throw new IllegalArgumentException("Linija " + (startLine + i + 2) + ".: Neispravan " +
					"format ulazne datoteke!");
			}
			array[i] = bean;
		}
		
		return startLine + num;
		
	}
}
