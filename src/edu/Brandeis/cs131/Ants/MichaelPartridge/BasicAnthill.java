package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class BasicAnthill extends Anthill {

	public BasicAnthill(String name, int ants, AntLog log) {
		super(name, ants, log);
		// TODO Auto-generated constructor stub
	}

	public BasicAnthill(String name, int ants) {
		super(name, ants);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean tryToEatAt(Animal animal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitAnthill(Animal animal) {
		// TODO Auto-generated method stub

	}

}
