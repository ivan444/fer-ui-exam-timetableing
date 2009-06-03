package hr.fer.zemris.ui.lab;

import java.util.List;

import hr.fer.zemris.ui.lab.generator.ExamsData;
import hr.fer.zemris.ui.lab.generator.beans.ExamBean;
import hr.fer.zemris.ui.lab.generator.beans.TermBean;

/**
 * Klasa koja sadrzi mehanizme potrebne za simuliranje evolucije genetskim algoritmom.
 * Sadrzi operator krizanja (crossover) i fitness funkciju kojom se evaluira potencijalno rjesenje.
 */
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
		
		evaluateFitness(kain);
	}
	
	private float evaluateFitness(Individual timetable) {
		
		/* Ovo je ukradeno iz evaluatora, primjer kako bi trebala i nasa izgledati:
		if(isEveryCourseInAcceptableTerm(timetable) == false
				|| isCourseClustersTogether(timetable) == false
				|| isEveryFixedCourseInFixedTerm(timetable) == false)
				{
					return Float.MAX_VALUE; 
				}
				
				return 10000*countConflictedStudents(solution)
				+ 10000*countCapacityOverflow(solution)
				+ 4*countStudentsWithExamsInDayDiference(solution, 0) //ispiti u istom danu
				+ countStudentsWithExamsInDayDiference(solution, 1); // ispiti u sljedecem danu
		
		*/
		
		return 10000*countConflictedStudents(timetable);
	}

	/**
	 * Metoda izracunava broj studenata kojima se poklapaju ispiti u istom terminu.
	 * @param timetable Primjer rasporeda koji se evaluira
	 * @return broj studenata koji imaju dva ispita u istom terminu
	 */
	private int countConflictedStudents(Individual timetable) {
		TermBean[] terms = inputData.getTerms();
		for (int i = 0; i < terms.length; i++){
			TermBean term = terms[i];
			
			List <ExamBean> termExams = timetable.getExamsInTerm(term);
			
			//sad kad imamo sve ispite u nekom terminu, moramo pomocu ConflictMatrix
			//dohvatiti koliko se studenata preklapa u svakoj kombinaciji:
			// this.inputData.getConflictMatrix().countSharedStud(term1, term2);
			
			//pokusni ispis da isprobam dohvat kolegija u terminu:
			System.out.println("***********************************************");
			System.out.println("U terminu " + term.getDate() + " su ispiti kolegija:\n");
			
			if (termExams != null){
				for (ExamBean exam : termExams){
					System.out.print("\t- ");
					System.out.println(exam);
				}
			} else {
				System.out.println("\t- termin prazan");
			}
			System.out.println();
		}
		return 0;
	}

	/**
	 * Ova metoda modelira operator crossover genetskog algoritma. Crossover se odvija nad kromosomima
	 * dvaju roditelja.
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
	
	/**
	 * Metoda pretvara zadano rjesenje u String formata prikladnog za ispis u datoteku.
	 * @param timetable Raspored koji se formatira za ispis
	 * @return String koji predstavlja zapis rasporeda u formatu specificiranom u zadatku
	 */
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
