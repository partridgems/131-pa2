package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class Armadillo extends Animal {

	private static final int STARTING_HUNGER = 2;
	private static final int SPEED = 6;
	private static final int PRIORITY = 2;
	

	public Armadillo(String name, Colour color, AntLog log) {
		super(name, color, Armadillo.PRIORITY, Armadillo.SPEED, Armadillo.STARTING_HUNGER, log);
	}
	
	public Armadillo(String name, Colour color) {
		super(name, color, Armadillo.PRIORITY, Armadillo.SPEED, Armadillo.STARTING_HUNGER);
	}

}
