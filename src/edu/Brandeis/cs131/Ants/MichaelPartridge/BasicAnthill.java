package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.ArrayList;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class BasicAnthill extends Anthill {

	private ArrayList<Animal> localAnimals;

	public BasicAnthill(String name, int ants, AntLog log) {
		super(name, ants, log);

		localAnimals = new ArrayList<Animal>();
		
	}

	public BasicAnthill(String name, int ants) {
		super(name, ants);

		localAnimals = new ArrayList<Animal>();

	}

	@Override
	public synchronized boolean tryToEatAt(Animal animal) {

		if (this.antsLeft() == 0) {

			return false;

		} else if (!colorCheck(animal)) {

			return false;

		}else if (animal instanceof Aardvark) {
			//Check number of other Aardvarks and Anteaters present
			int aardvarks = 0;
			for (Animal a : localAnimals) {
				if (a instanceof Aardvark) {
					if (++aardvarks >= 2) {
						return false;
					}
				} else if (a instanceof Anteater) {
					return false;
				}
			}
			
			//Enter anthill
			localAnimals.add(animal);
			this.eatAnt();
			return true;

		} else if (animal instanceof Anteater) {
			//Check whether an Aardvark or Anteater is present
			for (Animal a : localAnimals) {
				if (a instanceof Aardvark || a instanceof Anteater) {
					return false;
				}
			}
			
			//Enter anthill
			localAnimals.add(animal);
			this.eatAnt();
			return true;

		} else if (animal instanceof Armadillo) {
			//Ensure at least one Aardvark or Anteater is present
			
			//Is anyone else here?
			if (localAnimals.size() == 0) {
				return false;
			}
			//If someone else was here, are they an Armadillo?
			for (Animal a : localAnimals) {
				if (a instanceof Armadillo) {
					return false;
				}
			}
			
			//Enter anthill
			localAnimals.add(animal);
			this.eatAnt();
			return true;

		} else {
			throw new RuntimeException("ERROR: unknown animal tried to eat at this hill.");
		}
	}	//End of tryToEatAt()


	/**
	 * Checks the currently eating animals against the colour of the incoming animal
	 * @param animal incoming animal
	 * @return false if another animal of the same colour is present
	 */
	private boolean colorCheck(Animal animal) {

		Colour c = animal.getColour();

		for (Animal a : localAnimals) {
			if (c == a.getColour()) {
				return false;
			}
		}
		return true;
	}	//End of colorCheck()

	@Override
	public synchronized void exitAnthill(Animal animal) {
		
		localAnimals.remove(animal);

	}

}
