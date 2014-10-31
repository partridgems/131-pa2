package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class Exterminator extends MyAnimal {
	private final static int HUNGER = 5;
	private final static int SPEED = 1;
	private final static int PRIORITY = 4;

	public Exterminator(String name, Colour color, int priority, int speed,
			int hunger, AntLog log) {
		super(name, color, PRIORITY, SPEED, HUNGER, log);
	}

	public Exterminator(String name, Colour color, int priority, int speed,
			int hunger) {
		super(name, color, PRIORITY, SPEED, HUNGER);
	}
	
	public String toString() {
        return String.format("%s EXTERMINATOR %s", this.getColour(), this.getName());
    }

}
