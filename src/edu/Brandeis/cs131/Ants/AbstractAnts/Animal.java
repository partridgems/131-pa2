package edu.Brandeis.cs131.Ants.AbstractAnts;

import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEventType;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;
import java.util.*;

/**
 * An Animal is a Runnable which eats at anthills. You must subclass Animal to
 * customize its behavior (e.g., Aardvark and Anteater).
 *
 * When you start a thread which runs an Animal, the Animal will immediately
 * begin trying to eat at the anthill or anthills passed into its constructor by
 * calling tryToJoin on each Anthill instance. As long as tryToJoin returns
 * false (indicating that the Animal did not eat at that Anthill), the Animal
 * will keep trying. This is called busy-waiting.
 *
 */
public abstract class Animal implements Runnable {

    private final String name;
    private final Colour color;
    private Collection<Anthill> anthills;
    private final int priority;
    private final int speed;
    private int hunger;
    private AntLog log;
    /* DO NOT alter code to add or remove items to the log. The log is used to
     ensure rules are adhered to by the test cases. It does not, should not and
     * must not serve as a mechanism for communication between anthills and 
     * animals.*/

    public Animal(String name, Colour color, int priority, int speed, int hunger, AntLog log) {
        this.name = name;
        this.color = color;
        this.anthills = new ArrayList<Anthill>();
        this.priority = priority;
        this.speed = speed;
        this.hunger = hunger;
        this.log = log;
        
        if (this.speed < 0 || this.speed > 9) {
            throw new RuntimeException("Animal has invalid speed");
        }
    }
    
    public Animal(String name, Colour color, int priority, int speed, int hunger) {
        this(name, color, priority, speed, hunger, Anthill.DEFAULT_LOG);
    }

    public final String getName() {
        return name;
    }

    public final Colour getColour() {
        return color;
    }

    public final int getHunger() {
        return hunger;
    }

    public final int getPriority() {
        return priority;
    }

    public final int getSpeed() {
        return speed;
    }

    public final boolean isHungry() {
        return this.hunger > 0;
    }

 
    @Override
    public String toString() {
        return String.format("%s ANIMAL %s", this.color, this.name);
    }

    public final void addAnthill(Anthill newHill) {
        this.anthills.add(newHill);
    }

    public final void addAnthill(Collection<Anthill> newHill) {
        this.anthills.addAll(newHill);
    }

    /**
     * Find and eat from one of the anthills.
     *
     * When a thread is run, it keeps looping through its collection of
     * available anthills until it succeeds in starting to eat at one of them.
     * Then, it will call doWhileAtAnthill (to simulate doing some work of
     * eating, i.e., that it takes time to eat one of the ants), then leave that
     * anthill.
     * This process repeats till the animal is no longer hungry.
     */
    public final void run() {
        while (this.isHungry()) {
            for (Anthill anthill : anthills) {
                if (anthill.attemptToEatAt(this)) {
                    doWhileAtAnthill();
                    anthill.leaveAnthill(this);
                }
            }
        }
        this.log.addToLog(this, AntEventType.FULL);
    }

    /**
     * This is what your Animal does while inside the anthill to simulate taking
     * time to eat an ant. The faster your Animal is, the less time this will
     * take.
     */
    public void doWhileAtAnthill() {
        try {
            Thread.sleep((10 - speed) * 100);
            this.hunger--;
        } catch (InterruptedException e) {
            System.err.println("Interrupted Animal " + toString());
        }
    }
}
