package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.ArrayList;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class BasicAnthill extends Anthill {

	private ArrayList<Animal> localAnimals;
	private ArrayList<Thread> localThreads;

	public BasicAnthill(String name, int ants, AntLog log) {
		super(name, ants, log);

		localAnimals = new ArrayList<Animal>();
		localThreads = new ArrayList<Thread>();

	}

	public BasicAnthill(String name, int ants) {
		super(name, ants);

		localAnimals = new ArrayList<Animal>();
		localThreads = new ArrayList<Thread>();

	}

	@Override
	public synchronized boolean tryToEatAt(Animal animal) {

		//Nobody, including exterminators, eats here if no ants
		if (this.antsLeft() == 0) {

			return false;

		} else if (animal instanceof Exterminator) {
			//Check for any other Exterminators present
			for (Animal a : localAnimals) {
				if (a instanceof Exterminator) {
					return false;
				}
			}
			
			//Cause all animals to stop eating
			exterminate();
			localAnimals.add(animal);
			localThreads.add(Thread.currentThread());
			return true;

		//Nobody can eat here if there are no more ants
		} else if (!colorCheck(animal)) {
			//Checks for other animals of the same color

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

			//Conditions met, enter anthill
			localAnimals.add(animal);
			localThreads.add(Thread.currentThread());
			this.eatAnt();
			return true;

		} else if (animal instanceof Anteater) {
			//Check whether an Aardvark or Anteater is present
			for (Animal a : localAnimals) {
				if (a instanceof Aardvark || a instanceof Anteater) {
					return false;
				}
			}

			//Conditions met, enter anthill
			localAnimals.add(animal);
			localThreads.add(Thread.currentThread());
			this.eatAnt();
			return true;

		} else if (animal instanceof Armadillo) {
			//Ensure at least one Aardvark or Anteater is present

			//Is anyone else here?
			if (localAnimals.size() == 0) {
				return false;
			}
			//If someone else is here, are they an Armadillo?
			for (Animal a : localAnimals) {
				if (a instanceof Armadillo) {
					return false;
				}
			}

			//Conditions met, enter anthill
			localAnimals.add(animal);
			localThreads.add(Thread.currentThread());
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

		//Using == to compare enum types
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
		localThreads.remove(Thread.currentThread());
		
		//If exterminator is leaving, wake all waiting animals
		if (animal instanceof Exterminator) {
			endExtermination();
		}
	}

	/**
	 * exterminate interrupts all currently feeding animals
	 * those animals will resume eating once the exterminator leaves
	 */
	public void exterminate() {
		for (Thread t : localThreads) {
			t.interrupt();
		}
	}
	
	/**
	 * endExtermination notifies all animals presumably sleeping on their own monitors
	 * animals will loop and resume their eating wait
	 */
	public void endExtermination() {
		for (Animal a : localAnimals) {
			synchronized (a) {
				a.notify();
			}
		}
	}
	
}	//End of BasicAnthill class
