package hr.fer.zemris.ui.lab.generika;

import hr.fer.zemris.ui.lab.Individual;
import hr.fer.zemris.ui.lab.generator.ExamsData;

public abstract class Killer {
	protected float deathProbability;
	protected ExamsData data;
	
	public Killer(ExamsData input, float deathProbability){
		this.data = input;
		this.deathProbability = deathProbability;
	}
	
	public abstract void kill(Individual ind);
}
