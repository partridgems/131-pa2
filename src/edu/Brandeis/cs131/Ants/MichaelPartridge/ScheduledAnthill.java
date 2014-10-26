package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.Collection;
import java.util.HashMap;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class ScheduledAnthill extends Anthill {

	private Collection<Anthill> hills;
	private HashMap<Animal, Anthill> animalLocator;

	private volatile int[] highPriorityWaitCount;



	private int ants;



	protected ScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log) {

		super(label, 0, log);

		//Make this hill have the total of all subordinate ant hills
		for (Anthill a : basicAnthills) {
			this.ants += a.antsLeft();
		}


		//Keeps track of number of high priority animals waiting
		highPriorityWaitCount = new int[5];

		//Maintaining reference to original object instead of creating a new local one
		//in case the original object is used for logging purposes.
		this.hills = basicAnthills;

		//Tracker to locate animals efficiently for exiting
		animalLocator = new HashMap<Animal, Anthill>();

	}	//End of constructor()



	@Override
	public boolean tryToEatAt(Animal animal) {

		boolean fed = false;


		while (!fed) {

			//Check priority and sleep if higher priority animals are waiting
			for (int p = 4; p > animal.getPriority(); p--) {
				if (highPriorityWaitCount[p] > 0) {

					synchronized (this) {
						//Someone is waiting ahead of this animal
						try {
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}

			for (Anthill anthill : hills) {
				if (anthill.tryToEatAt(animal)) {

					//Animal successfully eating
					synchronized (animalLocator) {
						animalLocator.put(animal, anthill);
					}
					eatAnt();
					fed = true;
					break;
				}
				if (fed) {
					//Error checking
					throw new RuntimeException("ERROR, BREAK DID NOT WORK AS EXPECTED");
				}

			}	//End of for: each anthill


			//If not fed, animal failed to eat at any hill. Have animal wait until someone finishes
			if (!fed) {

				synchronized (this) {
					//Someone is waiting ahead of this animal

					//Add this animal's waiting to priority table
					highPriorityWaitCount[animal.getPriority()]++;

					try {
						this.wait();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					//Animal has awakened, remove this animal from priority table
					highPriorityWaitCount[animal.getPriority()]--;
				}
			}


		}	//End of while loop: Animal was fed

		return true;
	}


	@Override
	public void exitAnthill(Animal animal) {	


		//Leave ant hill tracker
		synchronized (animalLocator) {
			animalLocator.get(animal).exitAnthill(animal);

			//Remove from tracker
			animalLocator.remove(animal);
		}

		//Wake up next animal waiting for food (that animal will handle priority)
		synchronized (this) {
			this.notifyAll();
		}

	}	//End of exitAnthill()


	@Override
	public int antsLeft() {
		return this.ants;
	}

	@Override
	public void eatAnt() {
		this.ants--;
	}


}	//End of ScheduledAnthill
