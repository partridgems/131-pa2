package edu.Brandeis.cs131.Ants;

import edu.Brandeis.cs131.Ants.AbstractAnts.AntFactory;
import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEvent;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEventType;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BehaviorTest extends AntTest {

    private AntFactory factory;

    @Before
    public void setUp() {
        factory = AntFactoryProxy.getNewAntFactory();
        Anthill.DEFAULT_LOG.clearLog();
        //System.out.printf("%s - %s \n", factory.getClass().getCanonicalName(), this.getClass().getName());
    }

    /**
     * Animal RollCall checks the basic functions of an animal. Note if the test
     * does not pass neither will any other test *
     */
    @Test
    public void Animal_RollCall() {

        for (Colour color : Colour.values()) {
            Animal aardvark = factory.createNewAardvark(AntFactoryProxy.gbNames[0], color);
            Animal anteater = factory.createNewAnteater(AntFactoryProxy.gbNames[1], color);
            Animal armadillo = factory.createNewArmadillo(AntFactoryProxy.gbNames[2], color);

            assertTrue("Aardvark is the wrong color", aardvark.getColour().equals(color));
            assertTrue("Anteater is the wrong color", anteater.getColour().equals(color));
            assertTrue("Armadilo is the wrong color", armadillo.getColour().equals(color));

            assertTrue("Aardvark has the wrong name", aardvark.getName().equals(AntFactoryProxy.gbNames[0]));
            assertTrue("Anteater has the wrong name", anteater.getName().equals(AntFactoryProxy.gbNames[1]));
            assertTrue("Armadilo has the wrong name", armadillo.getName().equals(AntFactoryProxy.gbNames[2]));

            assertTrue("Aardvark has the wrong priority", aardvark.getPriority() == 1);
            assertTrue("Anteater has the wrong priority", anteater.getPriority() == 2);
            assertTrue("Armadilo has the wrong priority", armadillo.getPriority() == 2);

            assertTrue("Aardvark has the wrong speed", aardvark.getSpeed() == 8);
            assertTrue("Anteater has the wrong speed", anteater.getSpeed() == 4);
            assertTrue("Armadilo has the wrong speed", armadillo.getSpeed() == 6);

            assertTrue("Aardvark has the wrong initial hunger", aardvark.getHunger() == 3);
            assertTrue("Anteater has the wrong initial hunger", anteater.getHunger() == 3);
            assertTrue("Armadilo has the wrong initial hunger", armadillo.getHunger() == 2);

            assertTrue("Aardvark toString does not function as expected", String.format("%s %s %s", color, AntFactoryProxy.aardName, AntFactoryProxy.gbNames[0]).equals(aardvark.toString()));
            assertTrue("Anteater toString does not function as expected", String.format("%s %s %s", color, AntFactoryProxy.anteName, AntFactoryProxy.gbNames[1]).equals(anteater.toString()));
            assertTrue("Armadilo toString does not function as expected", String.format("%s %s %s", color, AntFactoryProxy.armName, AntFactoryProxy.gbNames[2]).equals(armadillo.toString()));

        }
    }

    @Test
    public void Anthill_Basic() {
        int numberOfAnts = 15;
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], numberOfAnts);
        assertTrue("Anthill not initialized with correct number of ants", hill.antsLeft() == numberOfAnts);
        assertTrue("Anthill has the wrong name", AntFactoryProxy.mrNames[0].equals(hill.getName()));
        assertTrue("Anthill toString does not function as expected", String.format("%s", AntFactoryProxy.mrNames[0]).equals(hill.toString()));
    }

    @Test
    public void Aardvark_Eats() {
        Animal animal = factory.createNewAardvark(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEats(animal, hill);
        AntEvent logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record aardvark entering anthill", new AntEvent(animal, hill, AntEventType.ENTER_ATTEMPT).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record aardvark entering anthill", new AntEvent(animal, hill, AntEventType.ENTER_SUCCESS).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record aardvark leaving anthill", new AntEvent(animal, hill, AntEventType.LEAVE_START).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record aardvark leaving anthill", new AntEvent(animal, hill, AntEventType.LEAVE_END).weakEquals(logEvent));
    }

    @Test
    public void Anteater_Eats() {
        Animal animal = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEats(animal, hill);
        AntEvent logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater entering anthill", new AntEvent(animal, hill, AntEventType.ENTER_ATTEMPT).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater entering anthill", new AntEvent(animal, hill, AntEventType.ENTER_SUCCESS).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater leaving anthill", new AntEvent(animal, hill, AntEventType.LEAVE_START).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater leaving anthill", new AntEvent(animal, hill, AntEventType.LEAVE_END).weakEquals(logEvent));
    }

    @Test
    public void Armadillo_Eats() {
        Animal anteater = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.BLUE);
        Animal armadillo = factory.createNewArmadillo(AntFactoryProxy.gbNames[1], Colour.RED);
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        boolean canEat = hill.attemptToEatAt(anteater);
        assertTrue(String.format("%s cannot eat", anteater), canEat);
        anteater.doWhileAtAnthill();
        AnimalEats(armadillo, hill);
        hill.leaveAnthill(anteater);
        AntEvent logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater entering anthill", new AntEvent(anteater, hill, AntEventType.ENTER_ATTEMPT).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater entering anthill", new AntEvent(anteater, hill, AntEventType.ENTER_SUCCESS).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record armadillo entering anthill", new AntEvent(armadillo, hill, AntEventType.ENTER_ATTEMPT).weakEquals(logEvent));;
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record armadillo entering anthill", new AntEvent(armadillo, hill, AntEventType.ENTER_SUCCESS).weakEquals(logEvent));;
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record armadillo leaving anthill", new AntEvent(armadillo, hill, AntEventType.LEAVE_START).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record armadillo leaving anthill", new AntEvent(armadillo, hill, AntEventType.LEAVE_END).weakEquals(logEvent));        
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater leaving anthill", new AntEvent(anteater, hill, AntEventType.LEAVE_START).weakEquals(logEvent));
        logEvent = Anthill.DEFAULT_LOG.get();
        assertTrue("Anthill log did not record anteater leaving anthill", new AntEvent(anteater, hill, AntEventType.LEAVE_END).weakEquals(logEvent));
    }

    @Test
    public void Aardvark_Satisfied() {
        Animal animal = factory.createNewAardvark(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEatsTillSatisfied(animal, hill);
    }

    @Test
    public void Anteater_Satisfied() {
        Animal animal = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEatsTillSatisfied(animal, hill);
    }

    @Test
    public void Armadillo_Satisfied() {
        Animal anteater = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.BLUE);
        Animal armadillo = factory.createNewArmadillo(AntFactoryProxy.gbNames[1], Colour.RED);
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        boolean canEat = hill.attemptToEatAt(anteater);
        assertTrue(String.format("%s cannot eat", anteater), canEat);
        anteater.doWhileAtAnthill();
        AnimalEatsTillSatisfied(armadillo, hill);
        hill.leaveAnthill(anteater);
    }

    @Test
    public void Colour_Constraint() {
        Animal anteater = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.BLUE);
        Animal armadillo = factory.createNewArmadillo(AntFactoryProxy.gbNames[1], Colour.BLUE);
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        boolean canEat = hill.attemptToEatAt(anteater);
        assertTrue(String.format("%s cannot eat", anteater), canEat);
        canEat = hill.attemptToEatAt(armadillo);
        assertTrue(String.format("%s is eating with %s. Violates colour constraint", armadillo, anteater), !canEat);
    }

    @Test
    public void Multiple_Aardvarks() {
        Animal peter = factory.createNewAardvark(AntFactoryProxy.gbNames[0], Colour.RED);
        Animal ray = factory.createNewAardvark(AntFactoryProxy.gbNames[1], Colour.BLUE);
        Animal walter = factory.createNewAardvark(AntFactoryProxy.gbNames[7], Colour.GREEN);
        Anthill hill = factory.createNewBasicAnthill(AntFactoryProxy.mrNames[0], 15);
        boolean canEat = hill.attemptToEatAt(peter);
        assertTrue(String.format("%s cannot eat", ray), canEat);
        canEat = hill.attemptToEatAt(ray);
        assertTrue(String.format("%s is not eating with %s.", peter, ray), canEat);
        canEat = hill.attemptToEatAt(walter);
        assertTrue(String.format("%s is eating with %s and %s violates number constraint.", walter, peter, ray), !canEat);
        peter.doWhileAtAnthill();
        hill.leaveAnthill(peter);
        ray.doWhileAtAnthill();
        hill.leaveAnthill(ray);
        canEat = hill.attemptToEatAt(walter);
        assertTrue(String.format("%s cannot eat, %s and %s did not leave anthill.", walter, peter, ray), canEat);
    }
}
