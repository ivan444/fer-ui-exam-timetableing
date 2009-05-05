package hr.fer.zemris.ui.lab.generator.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hr.fer.zemris.ui.lab.generator.ExamsData;

import org.junit.Assert;
import org.junit.Test;

public class ExamDataTest {
	
	@Test
	public void equalityTest() throws IOException {
		String path = "podatci/primjer_problema.txt";
		ExamsData ed = new ExamsData(path);
		StringBuilder sbLoaded = new StringBuilder();
		sbLoaded.append(ed.getExams().length).append('\n');
		for (int i = 0; i < ed.getExams().length; i++) {
			sbLoaded.append(format00000((ed.getExams()[i].getExamID()))).append('#');
			sbLoaded.append(ed.getExams()[i].getClassName()).append('#');
			String[] jmbags = ed.getExams()[i].getStudents();
			for (int j = 0; j < jmbags.length; j++) {
				sbLoaded.append(jmbags[j]);
				if (j != jmbags.length-1) {
					sbLoaded.append(',');
				}
			}
			sbLoaded.append('\n');
		}
		sbLoaded.append(ed.getTerms().length).append('\n');
		for (int i = 0; i < ed.getTerms().length; i++) {
			sbLoaded.append(ed.getTerms()[i].getDate()).append('#');
			sbLoaded.append(ed.getTerms()[i].getCapacity()).append('#');
			sbLoaded.append(ed.getTerms()[i].getNum()).append('#');
			sbLoaded.append(ed.getTerms()[i].getTermID());
			sbLoaded.append('\n');
		}
		sbLoaded.append(ed.getFixedTerms().length).append('\n');
		for (int i = 0; i < ed.getFixedTerms().length; i++) {
			sbLoaded.append(ed.getFixedTerms()[i].getDate()).append('#');
			sbLoaded.append(format00000(ed.getFixedTerms()[i].getExamID()));
			sbLoaded.append('\n');
		}
		sbLoaded.append(ed.getParallelExams().length).append('\n');
		for (int i = 0; i < ed.getParallelExams().length; i++) {
			sbLoaded.append(format00000(ed.getParallelExams()[i].getExamIDa())).append('#');
			sbLoaded.append(format00000(ed.getParallelExams()[i].getExamIDb()));
			sbLoaded.append('\n');
		}
		sbLoaded.append(ed.getAllowedTerms().length).append('\n');
		for (int i = 0; i < ed.getAllowedTerms().length; i++) {
			sbLoaded.append(format00000(ed.getAllowedTerms()[i].getExamID())).append('#');
			String[] terms = ed.getAllowedTerms()[i].getTermIDs();
			for (int j = 0; j < terms.length; j++) {
				sbLoaded.append(terms[j]);
				if (j != terms.length-1) {
					sbLoaded.append(',');
				}
			}
			sbLoaded.append('\n');
		}
		
		StringBuilder sbReader = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		
		while (true) {
			line = reader.readLine();
			if (line == null) break;
			sbReader.append(line.trim()).append('\n');
		}
		reader.close();
		
		Assert.assertTrue(sbReader.toString().equals(sbLoaded.toString()));
	}
	
	private String format00000(int num) {
		String numS = Integer.toString(num);
		int nl = numS.length();
		for (int i = nl; i < 5; i++) {
			numS = "0" + numS;
		}
		
		return numS;
	}
}
