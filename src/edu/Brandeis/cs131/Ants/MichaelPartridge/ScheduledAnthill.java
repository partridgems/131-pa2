package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.Collection;
import java.util.HashMap;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public class ScheduledAnthill extends Anthill {
	
	Collection<Anthill> hills;
	HashMap<Animal, Anthill> animalLocator;
	
	
	
	protected ScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log) {
		
		super(label, 20, log);
		
		//Maintaining reference to original object instead of creating a new local one
		//in case the original object is used for logging purposes.
		hills = basicAnthills;
		
		//Tracker to locate animals efficiently
		animalLocator = new HashMap<Animal, Anthill>();
		
		
	}

	
	
	
	
	@Override
	public synchronized boolean tryToEatAt(Animal animal) {
		
		boolean fed = false;
		
		while (!fed) {
			for (Anthill anthill : hills) {
				if (anthill.tryToEatAt(animal)) {
					
					//Animal successfully eating
					animalLocator.put(animal, anthill);
					fed = true;
					break;
				}
			}
			
			//If not fed, animal failed to eat at any hill. Have animal wait until someone finishes
			if (!fed) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
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
	

}
