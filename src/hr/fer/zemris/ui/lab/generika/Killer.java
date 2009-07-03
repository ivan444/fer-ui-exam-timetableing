package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.ExamsData;

public abstract class Killer {
	
	protected ExamsData data;
	
	public Killer(ExamsData input){
		this.data = input;
	}
	
	public abstract void kill(float deathProbability, Individual ind);
}
