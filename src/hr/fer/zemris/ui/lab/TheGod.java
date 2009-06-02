package hr.fer.zemris.ui.lab;

import java.util.List;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

public class TheGod {
	
	private ExamsData inputData;
	
	// ovdje bismo mogli definirati parametre genetskog algoritma
	// evo samo neki za primjer:
	private int initialPopulationSize = 2;		//Adam & Eve
	private float mutationFactor = 0.00f;		//No mutants allowed!
	private boolean incest = false;				//No Fritzl allowed!
	private int numberOfParents = 2;			//No orgies allowed!
	
	public TheGod(ExamsData eddie){
		this.inputData = eddie;
	}
	
	/**
	 * Ova metoda modelira genetski algoritam.
	 */
	public void doEvolution(){
		
		Population childrenOfGod = new Population(this.inputData, initialPopulationSize);
		
		//while(1)
		//	crossover
		//	mutate
		//	select
		//	...
		
		Individual kain = makeBabies(childrenOfGod.getIndividaul(0),childrenOfGod.getIndividaul(1));
		kain.mutate();
		
		System.out.println(printIndividual(kain));
		
		
		//pokusni ispis da isprobam dohvat kolegija u terminu
		
		TermBean termin = this.inputData.getTerms()[0];
		List<ExamBean> lista = kain.getExamsinTerm(termin);
		
		System.out.println("U terminu " + termin.getDate() + " su ispiti kolegija:");
		for (ExamBean exam : lista){
			System.out.println(exam);
		}
	}
	
	/**
	 * Ova metoda modelira operator crossover genetskog algoritma. Crossover se odvija nad kromosomima
	 * dvaju roditelja
	 * @param mom prvi roditelj
	 * @param dad drugi roditelj
	 * @return mala beba
	 */
	private Individual makeBabies (Individual mom, Individual dad){
		
		// ovaj new treba izbacit! o mogucnostima cemo raspravit
		Individual baby = new Individual(this.inputData.getExams()); 
		
		int numberOfGenes = this.inputData.getExams().length;
		
		for (int i = 0; i < numberOfGenes; i++){
			if (i < numberOfGenes/2){
				baby.setTerm(i, dad.getTerm(i));
			} else {
				baby.setTerm(i, mom.getTerm(i));
			}
		}
	
		return baby; 
	}
	
	public String printIndividual(Individual timetable) {
		StringBuilder sb = new StringBuilder();
		
		int numberOfGenes = this.inputData.getExams().length;
		ExamBean[] exams = this.inputData.getExams();
		
		for (int i = 0; i < numberOfGenes; i++) {
			TermBean term = timetable.getTerm(i);
			ExamBean exam = exams[i];
			sb	.append(term.getDate())
				.append('\t')
				.append(2)
				.append('\t')
				.append(exam.getClassName())
				.append('\t')
				.append(Population.format00000(exam.getExamID()))
				.append('\n');
		}
		
		return sb.toString();
	}

}
