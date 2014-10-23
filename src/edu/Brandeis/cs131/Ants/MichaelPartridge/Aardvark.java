package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class Aardvark extends Animal {
	private static final int STARTING_HUNGER = 3;
	private static final int SPEED = 8;
	private static final int PRIORITY = 1;
	

	public Aardvark(String name, Colour color, AntLog log) {
		super(name, color, Aardvark.PRIORITY, Aardvark.SPEED, Aardvark.STARTING_HUNGER, log);
	}
	
	public Aardvark(String name, Colour color) {
		super(name, color, Aardvark.PRIORITY, Aardvark.SPEED, Aardvark.STARTING_HUNGER);
	}
	
    @Override
    public String toString() {
        return String.format("%s AARDVARK %s", this.getColour(), this.getName());
    }


}
