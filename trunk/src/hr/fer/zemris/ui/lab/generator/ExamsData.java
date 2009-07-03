package hr.fer.zemris.ui.lab.generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import hr.fer.zemris.ui.lab.generator.beans.AllowedTermsBean;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.ExamBeanParallel;
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
			
			mergeParallelExams();
			
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
	
	private void mergeParallelExams() {
		int newId = -1;
		List<ExamBeanParallel> newExams = new LinkedList<ExamBeanParallel>();
		int merged = 0;
		
		for (int i = 0; i < parallelExams.length; i++) {
			
			List<ExamBean> examList = new LinkedList<ExamBean>();
			int[] examIDs = parallelExams[i].getExamIDs();
			
			for (int j = 0; j < exams.length; j++) {
				if (exams[j] == null) continue;
				for (int k = 0; k < examIDs.length; k++) {
					if (exams[j].getExamID() == examIDs[k]) {
						examList.add(exams[j]);
						merged++;
						exams[j] = null;
						break;
					}
				}
			}
			
			newExams.add(new ExamBeanParallel(examList.toArray(new ExamBean[0]), newId));
			
			for (ExamBean e : examList) {
				for (int j = 0; j < fixedTerms.length; j++) {
					if (e.getExamID() == fixedTerms[j].getExamID()) {
						fixedTerms[j].setExamID(newId);
					}
				}
			}
			
			// TODO: Zasad se ne gleda ima li više ispita allowed, pa se traži presjek nego se samo krca...
			// Zapravo, nemamo pojma kakvi su ulazni podatci, tako da možemo pretpostaviti da je ovo ok (valjda...)
			for (ExamBean e : examList) {
				for (int j = 0; j < allowedTerms.length; j++) {
					if (e.getExamID() == allowedTerms[j].getExamID()) {
						allowedTerms[j].setExamID(newId);
					}
				}
			}
			
			merged--;
			newId--;
		}
		
		ExamBean[] updatedExams = new ExamBean[exams.length-merged];
		int ueIndex = 0;
		for (int i = 0; i < exams.length; i++) {
			if (exams[i] != null) {
				updatedExams[ueIndex] = exams[i];
				ueIndex++;
			}
		}
		
		for (ExamBeanParallel ebp : newExams) {
			updatedExams[ueIndex] = ebp;
			ueIndex++;
		}
		
		this.exams = updatedExams;
		
	}

	public ExamBean[] getExams() {
		return exams;
	}

	public TermBean[] getTerms() {
		return terms;
	}

	public FixedTermBean[] getFixedTerms() {
		return fixedTerms;
	}

	public ParallelExamsBean[] getParallelExams() {
		return parallelExams;
	}

	public AllowedTermsBean[] getAllowedTerms() {
		return allowedTerms;
	}

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
	
	
	/**
	 * Metoda trazi indekse svih fiksnih termina. 
	 * @return niz indeksa fiksnih termina
	 */
	public int[] getFixedTermExamIndexes(){
		//Postoje dva niza. Niz exama sa fiksnim terminima
		// i niz svih exama zajedno. Index i-tog fiksnog 
		// termina je j-ti indeks u nizu svih exama zajedno.
		 
		int[] fixedTermIndexes = new int[fixedTerms.length];
		
		for (int i = 0; i < fixedTerms.length; i++) {
			
			FixedTermBean fTerm = fixedTerms[i];
			
			for (int j = 0; j < exams.length; j++) {
				if (fTerm.getExamID() == exams[j].getExamID()){
					fixedTermIndexes[i] = j;
					break;
				}
			}
			
		}
		
		return fixedTermIndexes;
	}

}
