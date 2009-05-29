package hr.fer.zemris.ui.lab;

import java.io.IOException;

import hr.fer.zemris.ui.lab.generator.ExamsData;

public class Startup {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String path = "podatci/primjer_problema.txt";
		ExamsData ed = new ExamsData(path);
		
	}
	
	public static void stop() {
		
	}

}
