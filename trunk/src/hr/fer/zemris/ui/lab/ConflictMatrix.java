package hr.fer.zemris.ui.lab;

import hr.fer.zemris.ui.lab.generator.beans.ExamBean;

/**
 * Tablica konflikata je kvadratna matrica sa dimenzijama broja predmeta.
 * Stupci i retci tablice se tumace kao indexi predmeta. Vrijednost matrice u stupcu 
 * i, retku j oznacava studente koji su dijeljeni izmedju predmeta sa indexom i,j.
 * Matrica se zove tablica konflikata zato sto vrijednost matrice razlicita od 0 oznacava
 * da se dva predmeta nemogu odrzavati u istom terminu. Konflikt nastaje kada
 * student treba u istom terminu biti na razlicitim ispitima( dva mjesta u isto vrijeme).
 * @author Dino, ibelsa
 *
 */
public class ConflictMatrix {

	/**
	 * Sadrzava broj dijeljenih studenata izmedju svih parova predmeta.
	 */
	private int [][] conflictMatrix;
	
	/**
	 * Stvara novu tablicu konflikata iz zadanog zadatka.
	 * Potrebni su samo predmeti zadatka.
	 * @param task zadatak
	 */
	public ConflictMatrix(ExamBean[] exams)
	{
		conflictMatrix = new int[exams.length][exams.length];
		
		for(int i = 0; i < exams.length; i++)
		{
			ExamBean ei = exams[i];
			ei.setIndex(i);
			
			for(int j = i; j < exams.length; j++)
			{
				ExamBean ej = exams[j];
				int sharedStud = countSharedStud(ei,ej);
				conflictMatrix[i][j] = conflictMatrix[j][i] = sharedStud;
			}
		}
	}
	
	/**
	 * Broji zajednicke studente izmedju predmeta exam1 i exam2. 
	 * @param exam1
	 * @param exam2
	 * @return
	 */
	public static int countSharedStud(ExamBean exam1, ExamBean exam2)
	{
		int numberOfStudents = exam1.getStudents().length;
		
		if(exam1 == exam2)
		{
			return numberOfStudents;
		}
		
		int shared = 0;
		
		for (int i = 0; i < numberOfStudents; i++){
			
			int student = exam1.getStudentsHash()[i];
			if (exam2.containsStudent(student)) {
				shared++;
			}
		}
		return shared;
	}
	
	public int shared(int i , int j)
	{
		return conflictMatrix[i][j];
	}
}
