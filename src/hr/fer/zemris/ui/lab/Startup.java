package hr.fer.zemris.ui.lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.ui.lab.generator.ExamsData;

public class Startup {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Niste predali ulaznu datoteku!");
		}
		String path = args[0];
		ExamsData ed = new ExamsData(path);
		final BufferedWriter writer = new BufferedWriter(new FileWriter("tim2_rjesenje.txt"));
		final TheGod god = new TheGod(ed, 50, 0.001f, 1000, true);
		Terminator.startTerminator(5, "stop.me", god);
		god.doEvolution(writer);
	}

}
