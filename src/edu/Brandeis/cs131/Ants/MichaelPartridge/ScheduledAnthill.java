package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class ScheduledAnthill extends Anthill {

	//Ant hills subordinate to this scheduler
	private Collection<Anthill> hills;
	
	//Used to find animals to exit ant hills
	private HashMap<Animal, Anthill> animalLocator;

	//Keep track of animals sleeping by priority
	private ArrayList<Integer> highPriorityWaitCount;

	//Total number of ants in all subordinate BasicAnthills
	private int ants;



	protected ScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log) {

		super(label, 0, log);

		//Make this hill have the total of all subordinate ant hills
		for (Anthill a : basicAnthills) {
			this.ants += a.antsLeft();
		}


		//Keeps track of number of high priority animals waiting
		highPriorityWaitCount = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++) {
			highPriorityWaitCount.add(i, 0);
		}
		

		//Maintaining reference to original object instead of creating a new local one
		//in case the original object is used for logging purposes.
		this.hills = basicAnthills;

		//Tracker to locate animals efficiently for exiting
		animalLocator = new HashMap<Animal, Anthill>();

	}	//End of constructor()
	
	protected ScheduledAnthill(String label, Collection<Anthill> basicAnthills) {

		super(label, 0);

		//Make this hill have the total of all subordinate ant hills
		for (Anthill a : basicAnthills) {
			this.ants += a.antsLeft();
		}


		//Keeps track of number of high priority animals waiting
		highPriorityWaitCount = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++) {
			highPriorityWaitCount.add(i, 0);
		}
		

		//Maintaining reference to original object instead of creating a new local one
		//in case the original object is used for logging purposes.
		this.hills = basicAnthills;

		//Tracker to locate animals efficiently for exiting
		animalLocator = new HashMap<Animal, Anthill>();

	}	//End of constructor()



	@Override
	public boolean tryToEatAt(Animal animal) {

		//Flag terminates feeding loop when animal successfully eats somewhere
		boolean fed = false;

		//Loop until animal eats
		while (!fed) {

			//Check priority and sleep if higher priority animals are waiting
			for (int p = 4; p > animal.getPriority(); p--) {
				if (highPriorityWaitCount.get(p) > 0) {

					synchronized (this) {
						//Someone is waiting ahead of this animal
						try {
							//Wake someone else up to try
							this.notify();
							
							//Sleep until this animal's turn
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

					synchronized (highPriorityWaitCount) {
						//Add this animal's waiting to priority table
						highPriorityWaitCount.set( animal.getPriority(),
										highPriorityWaitCount.get(animal.getPriority()) + 1 );
					}
					
					try {
						this.wait();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					//Animal has awakened, remove this animal from priority table
					synchronized (highPriorityWaitCount) {
						//Add this animal's waiting to priority table
						highPriorityWaitCount.set( animal.getPriority(),
										highPriorityWaitCount.get(animal.getPriority()) - 1 );
					}
					
				}
				
			}	//End of the if (!fed) waiting block.
			
			
			//Animal is now awake and that is reflected in highPriorityWaitCount


		}	//End of while loop: Animal was fed if this loop terminates

		return true;
	}


	@Override
	public void exitAnthill(Animal animal) {	


		//Leave ant hill pointed to by locator
		synchronized (animalLocator) {
			animalLocator.get(animal).exitAnthill(animal);

			//Remove from locator
			animalLocator.remove(animal);
		}

		//Wake up next animal waiting for food (that animal will handle priority)
		synchronized (this) {
			this.notify();
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
