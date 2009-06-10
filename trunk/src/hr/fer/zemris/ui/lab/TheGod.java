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
	private ConflictMatrix conflictMatrix;
	
	// ovdje bismo mogli definirati parametre genetskog algoritma
	// evo samo neki za primjer:
	private int initialPopulationSize = 2;		//Adam & Eve
	private float mutationFactor = 0.00f;		//No mutants allowed!
	private boolean incest = false;				//No Fritzl allowed!
	private int numberOfParents = 2;			//No orgies allowed!
	
	public TheGod(ExamsData eddie){
		this.inputData = eddie;
		conflictMatrix = new ConflictMatrix(eddie.getExams());
	}
	
	/**
	 * Ova metoda modelira genetski algoritam.
	 */
	public void doEvolution(){
		
		Population childrenOfGod = new Population(this.inputData, initialPopulationSize);
		
		//while(1)
		//	select k1,k2
		//	k3 = crossover k1,k2
		//	mutate k3
		//	
		//	...
		
		//u nastavku primjer jednog krizanja, nastaje kain, koji onda biva evaluiran
		
		Individual kain = makeBabies(childrenOfGod.getIndividaul(0),childrenOfGod.getIndividaul(1));
		kain.mutate();
		System.out.println(printIndividual(kain));
		
		float fitness1 = evaluateFitness(kain);
		float fitness2 = evaluateFitness(childrenOfGod.getIndividaul(0));
		float fitness3 = evaluateFitness(childrenOfGod.getIndividaul(1));
		
		System.out.println("Dobrota tate:\t\t" + fitness2);
		System.out.println("Dobrota mame:\t\t" + fitness3);
		System.out.println("------------------------------");
		System.out.println("Dobrota djeteta:\t" + fitness1);
	}
	
	private float evaluateFitness(Individual timetable) {
		// Ovo je ukradeno iz evaluatora, no license agreement :-P
		if(isEveryCourseInAcceptableTerm(timetable) == false
				|| isCourseClustersTogether(timetable) == false
				|| isEveryFixedCourseInFixedTerm(timetable) == false)
				{
					return Float.MAX_VALUE; 
				}
		return 10000*countConflictedStudents(timetable)
			+ 10000*countCapacityOverflow(timetable)
			+ 4*countStudentsWithExamsInDayDiference(timetable, 0) //ispiti u istom danu
			+ countStudentsWithExamsInDayDiference(timetable, 1); // ispiti u sljedecem danu
	}

	private boolean isEveryFixedCourseInFixedTerm(Individual timetable) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isCourseClustersTogether(Individual timetable) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isEveryCourseInAcceptableTerm(Individual timetable) {
		// TODO Auto-generated method stub
		return true;
	}

	private int countStudentsWithExamsInDayDiference(Individual timetable, int delta) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int countCapacityOverflow(Individual timetable) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Metoda izracunava broj studenata kojima se poklapaju ispiti u istom terminu.
	 * @param timetable Primjer rasporeda koji se evaluira
	 * @return broj studenata koji imaju dva ispita u istom terminu
	 */
	private int countConflictedStudents(Individual timetable) {
		
		TermBean[] terms = inputData.getTerms();
		int conflicted = 0;
		
		for(int k = 0; k < terms.length; k++)
		{
			TermBean term = terms[k];
			List <ExamBean> examsInTerm = timetable.getExamsInTerm(term);
			
			if(examsInTerm == null) // prazan termin
			{
				continue;
			}
			
			for (int i = 0; i < examsInTerm.size()-1; i++) {
				
				int firstIndex = examsInTerm.get(i).index();
				
				for(int j = i+1; j < examsInTerm.size(); j++)
				{
					int secondIndex = examsInTerm.get(j).index();
					conflicted += conflictMatrix.shared(firstIndex,secondIndex);
				}
			}
		}
		//System.out.println("counted "  + conflicted + " conflicted students. Ouch!");
		return conflicted;
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
