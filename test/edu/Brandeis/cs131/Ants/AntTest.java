package edu.Brandeis.cs131.Ants;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.AntFactory;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEvent;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEventType;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AntTest {

    protected AntFactory factory;

    @Before
    public void setUp() {
        factory = AntFactoryProxy.getNewAntFactory();
        Anthill.DEFAULT_LOG.clearLog();
    }
    //Simulates a single pass of the animal's run method

    public void AnimalEats(Animal animal, Anthill hill) {
        int initialHunger = animal.getHunger();
        int numberOfAnts = hill.antsLeft();
        boolean canEat = hill.attemptToEatAt(animal);
        assertTrue(String.format("%s cannot eat", animal), canEat);
        animal.doWhileAtAnthill();
        hill.leaveAnthill(animal);
        assertTrue(String.format("%s hunger did not decrease", animal), animal.getHunger() == (initialHunger - 1));
        assertTrue("Anthill did not lose ants", hill.antsLeft() == (numberOfAnts - 1));
    }

    //Run the animal simulation in the current thread
    public void AnimalEatsTillSatisfied(Animal animal, Anthill hill) {
        assertTrue("Error in test setup, anthill does not have enough ants.", hill.antsLeft() > animal.getHunger());
        animal.addAnthill(hill);
        animal.run();
        assertTrue("Animal not satisfied but finished eating.", !animal.isHungry());
        assertTrue("Animal satisfied, but still hungry", animal.getHunger() == 0);
        AntEvent lastEvent = new AntEvent(AntEventType.ERROR);
        while (!Anthill.DEFAULT_LOG.isEmpty()) {
            lastEvent = Anthill.DEFAULT_LOG.get();
        }
        assertTrue("Anthill log did not record animal becomes satisfied.", new AntEvent(animal, AntEventType.FULL).weakEquals(lastEvent));
    }

    @Test
    public void dummy() {
        //does nothing
    }
}
