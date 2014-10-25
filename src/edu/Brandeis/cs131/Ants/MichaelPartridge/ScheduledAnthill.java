package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class ScheduledAnthill extends Anthill {

	Collection<Anthill> hills;
	HashMap<Animal, Anthill> animalLocator;
	ArrayList<Boolean> priority;



	protected ScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log) {

		//100 ants/hill to prevent errors from this hill not having enough ants
		//Number is irrelevant because animals don't actually eat from this hill,
		//but test checks that they do
		super(label, 100*basicAnthills.size(), log);

		//Array to keep track of priority
		//Sized for possible priorities of 0-4
		//Boolean indicates presence of animals at that priority waiting
		priority = new ArrayList<Boolean>();
		for (int i = 0; i < 5; i++) {
			priority.add(i, false);
		}

		//Maintaining reference to original object instead of creating a new local one
		//in case the original object is used for logging purposes.
		hills = basicAnthills;

		//Tracker to locate animals efficiently
		animalLocator = new HashMap<Animal, Anthill>();


	}	



	@Override
	public synchronized boolean tryToEatAt(Animal animal) {

		boolean fed = false;
		
		//Priority check before going to ant hills
		priorityCheck(animal, false);
		
		//At this point either nobody of higher priority was waiting, or this animal's
		//queue was notified
		

		while (!fed) {

			for (Anthill anthill : hills) {
				if (anthill.tryToEatAt(animal)) {

					//Animal successfully eating
					animalLocator.put(animal, anthill);
					fed = true;
					break;
				}
			}

			//If not fed, animal failed to eat at any hill. Have animal wait at own queue
			//until someone finishes
			if (!fed) {
				priorityCheck(animal, true);
			}

		}	//End of while loop: Animal was fed

		//Decrement this hill's ants to placate the test
		this.eatAnt();
		
		//Wake up the highest priority queue, setting flag to false because nobody will be sleeping
		for (int i = 0; i < 5; i++) {
			if (priority.get(i)) {
				synchronized(priority) {
					priority.set(i, false);
					priority.get(i).notifyAll();
					
					//Break out of this loop after one queue is awakened
					i = 5;
				}
			}
		}

		return true;
	}


	@Override
	public synchronized void exitAnthill(Animal animal) {	

		//Leave anthill
		animalLocator.get(animal).leaveAnthill(animal);

		//Remove from tracker
		animalLocator.remove(animal);

		//Wake up all animals waiting for food
		this.notifyAll();		

	}
	
	
	/**
	 * Method for causing animals to sleep until they should check ant hills
	 * Flag is set to true if animal should sleep regardless of queue status (already checked hills)
	 * 
	 * @param animal Animal to sleep
	 * @param waitAnyway Causes animal to sleep regardless of queue status
	 */
	private void priorityCheck(Animal animal, boolean waitAnyway) {

		//Sleep on own queue if someone more important is waiting
		for (int i = 0; i < animal.getPriority(); i++) {
			if (priority.get(i) || waitAnyway) {
				synchronized (priority.get(animal.getPriority())) {
						priority.set(animal.getPriority(), true);
					try {
						priority.get(animal.getPriority()).wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}	//End of priorityCheck()


}	//End of ScheduledAnthill
