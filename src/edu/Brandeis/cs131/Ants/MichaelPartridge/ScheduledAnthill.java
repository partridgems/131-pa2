package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.Collection;
import java.util.HashMap;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class ScheduledAnthill extends Anthill {

	private Collection<Anthill> hills;
	private HashMap<Animal, Anthill> animalLocator;
	
	private volatile int pri2waiting;
	


	private int ants;



	protected ScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log) {

		super(label, 0, log);

		//Make this hill have the total of all subordinate ant hills
		for (Anthill a : basicAnthills) {
			this.ants += a.antsLeft();
		}


		pri2waiting = 0;

		//Maintaining reference to original object instead of creating a new local one
		//in case the original object is used for logging purposes.
		hills = basicAnthills;

		//Tracker to locate animals efficiently
		animalLocator = new HashMap<Animal, Anthill>();


	}
	
//	private Object getQueue (Animal animal) {
//		if (animal instanceof Armadillo) {
//			return armadilloQueue;
//		} else if (animal instanceof Anteater) {
//			return anteaterQueue;
//		} else if (animal instanceof Aardvark) {
//			return aardvarkQueue;
//		} else {
//			return null;
//		}
//	}



	@Override
	public boolean tryToEatAt(Animal animal) {

		boolean fed = false;

		//First check for higher priority animals waiting
		
		if (animal instanceof Aardvark) {
			
//			System.out.println("Aardvark priority check, pri2waiting is " + pri2waiting);
			
			//Check priority and sleep if higher priority animals are waiting
			//Armadillos and Anteaters don't wait because they have highest priority
			if (pri2waiting != 0) {
				
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
		
		while (!fed) {
			
//			System.out.println(animal.toString() + " trying to eat");

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
					throw new RuntimeException("ERROR, BREAK DID NOT WORK AS EXPECTED");
				}
			}

			//If not fed, animal failed to eat at any hill. Have animal wait until someone finishes
			if (!fed) {
				
//				System.out.println(animal.toString() + " failed to eat. Sleeping.");
				
				synchronized (this) {
					//Someone is waiting ahead of this animal
					if (animal instanceof Anteater) {
						
						pri2waiting++;
					}
					try {
						this.wait();
						
//						System.out.println(animal.toString() + " woke up, trying to eat.");
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			

		}	//End of while loop: Animal was fed
		
//		System.out.println(animal.toString() + " successfully ate. Waking up armadillos. Hunger is now " + animal.getHunger());
		
		synchronized (this) {
			//Wake up sleeping armadillos now that an animal is eating
			this.notifyAll();
		}
		return true;
	}


	@Override
	public void exitAnthill(Animal animal) {	
		
//		System.out.println(animal.toString() + " is leaving anthill");

		//Leave anthill
		synchronized (animalLocator) {
			animalLocator.get(animal).exitAnthill(animal);
			
			//Remove from tracker
			animalLocator.remove(animal);
		}
		
		
		//Decrement priority queue size of applicable
		if (animal instanceof Anteater) {
			pri2waiting--;
		}

		//Wake up all animals waiting for food
		if (pri2waiting != 0) {
			synchronized (this) {
				this.notifyAll();
			}

		} else {
			synchronized (this) {
				this.notifyAll();
			}
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
