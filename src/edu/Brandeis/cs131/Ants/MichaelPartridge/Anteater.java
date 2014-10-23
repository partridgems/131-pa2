package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class Anteater extends Animal {

	private static final int STARTING_HUNGER = 3;
	private static final int SPEED = 4;
	private static final int PRIORITY = 2;
	

	public Anteater(String name, Colour color, AntLog log) {
		super(name, color, Anteater.PRIORITY, Anteater.SPEED, Anteater.STARTING_HUNGER, log);
	}
	
	public Anteater(String name, Colour color) {
		super(name, color, Anteater.PRIORITY, Anteater.SPEED, Anteater.STARTING_HUNGER);
	}
	
    @Override
    public String toString() {
        return String.format("%s ANTEATER %s", this.getColour(), this.getName());
    }

}
