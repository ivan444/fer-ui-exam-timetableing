package hr.fer.zemris.ui.lab;

import java.io.File;

/**
 * Razred za terminiranje generiranja rasporeda.
 * Provjerava svakih N sekundi je li u direktorij programa dodana datoteka "STOP".
 * Ako jest, pokreće terminiranje generatora.
 * 
 * @author Ivan Krišto
 */
public class Terminator implements Runnable {
	private int checkInterval;
	private String termDat;
	private TheGod god;
	
	/**
	 * Konstruktor.
	 * 
	 * @param checkInterval Vrijeme u sekundama koje treba proći između dvije provjere.
	 * @param termDat Naziv datoteke koja označava da trebamo terminirati generator.
	 */
	private Terminator(int checkInterval, String termDat, TheGod god) {
		this.checkInterval = checkInterval;
		this.termDat = termDat;
		this.god = god;
	}
	
	/**
	 * Pokretanje terminatora.
	 * 
	 * @param checkInterval Vrijeme u sekundama koje treba proći između dvije provjere.
	 * @param termDat Naziv datoteke koja označava da trebamo terminirati generator.
	 */
	public static void startTerminator(int checkInterval, String termDat, TheGod god) {
		new Thread(new Terminator(checkInterval, termDat, god)).start();
	}
	
	@Override
	public void run() {
		File fTermDat = new File(termDat);
		long checkInt = checkInterval*1000L;
		
		while (!fTermDat.exists()) {
			try {
				Thread.sleep(checkInt);
			} catch (InterruptedException e) {
				// TODO: Trebalo bi srediti logiranje (log4j?)... neka zasad ostane ovako...
				e.printStackTrace();
			}
		}
		
		god.terminate();
	}
}
