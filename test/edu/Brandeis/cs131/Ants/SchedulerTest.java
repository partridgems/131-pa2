package edu.Brandeis.cs131.Ants;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SchedulerTest extends AntTest {

    private String scheduledName = "SCHEDULED";

    private Anthill setupSimpleScheduledAnthill(String name, int ants) {
        Collection<Anthill> hills = new ArrayList<Anthill>();
        hills.add(factory.createNewBasicAnthill(name, ants));
        return factory.createNewScheduledAnthill(scheduledName, hills, new AntLog());
    }

    @Test
    public void Aardvark_Eats() {
        Animal animal = factory.createNewAardvark(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = setupSimpleScheduledAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEats(animal, hill);
    }

    @Test
    public void Anteater_Eats() {
        Animal animal = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = setupSimpleScheduledAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEats(animal, hill);
    }

    @Test
    public void Armadillo_Eats() {
        Animal anteater = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.BLUE);
        Animal armadillo = factory.createNewArmadillo(AntFactoryProxy.gbNames[1], Colour.RED);
        Anthill hill = setupSimpleScheduledAnthill(AntFactoryProxy.mrNames[0], 15);
        boolean canEat = hill.attemptToEatAt(anteater);
        assertTrue(String.format("%s cannot eat", anteater), canEat);
        anteater.doWhileAtAnthill();
        AnimalEats(armadillo, hill);
        hill.leaveAnthill(anteater);
    }

    @Test
    public void Aardvark_Satisfied() {
        Animal animal = factory.createNewAardvark(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = setupSimpleScheduledAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEatsTillSatisfied(animal, hill);
    }

    @Test
    public void Anteater_Satisfied() {
        Animal animal = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.random());
        Anthill hill = setupSimpleScheduledAnthill(AntFactoryProxy.mrNames[0], 15);
        AnimalEatsTillSatisfied(animal, hill);
    }

    @Test
    public void Armadillo_Satisfied() {
        Animal anteater = factory.createNewAnteater(AntFactoryProxy.gbNames[0], Colour.BLUE);
        Animal armadillo = factory.createNewArmadillo(AntFactoryProxy.gbNames[1], Colour.RED);
        Anthill hill = setupSimpleScheduledAnthill(AntFactoryProxy.mrNames[0], 15);
        boolean canEat = hill.attemptToEatAt(anteater);
        assertTrue(String.format("%s cannot eat", anteater), canEat);
        anteater.doWhileAtAnthill();
        AnimalEatsTillSatisfied(armadillo, hill);
        hill.leaveAnthill(anteater);
    }
}
