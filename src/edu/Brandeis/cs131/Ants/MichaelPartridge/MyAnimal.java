package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

public abstract class MyAnimal extends Animal {

	public MyAnimal(String name, Colour color, int priority, int speed,
			int hunger, AntLog log) {
		super(name, color, priority, speed, hunger, log);
	}

	public MyAnimal(String name, Colour color, int priority, int speed,
			int hunger) {
		super(name, color, priority, speed, hunger);
	}


	@Override
	public void doWhileAtAnthill() {
		boolean fed = false;
		
		while (!fed) {
			try {
				this.wait((10 - this.getSpeed()) * 100);
				this.eatAnt();
				fed = true;
			} catch (InterruptedException e) {
				
				//Animal interrupted by Exterminator
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
			}	//End of interruption catch block
			
			/*At this point, if the animal is not fed due to being interrupted by the exterminator,
			 * the animal will loop back and try waiting again
			 */
		}	//End of while(not fed)
		
	}


}
