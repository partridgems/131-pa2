package edu.Brandeis.cs131.Ants;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.AntFactory;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEvent;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEventType;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.DummyLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SimulationAnthillTest {

    protected AntFactory factory;

    @Before
    public void setUp() {
        factory = AntFactoryProxy.getNewAntFactory();
        Anthill.DEFAULT_LOG.clearLog();
        //System.out.printf("%s - %s \n", factory.getClass().getCanonicalName(), this.getClass().getName());
    }

    @Test
    public void Basic_Anthill_Test() {
        AntLogVerifier verifier = new AntLogVerifier(Anthill.DEFAULT_LOG);
        Thread verifierThread = new Thread(verifier);
        verifierThread.start();
        Collection<Anthill> anthills = new ArrayList<Anthill>();
        Collection<Thread> animalThread = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            anthills.add(factory.createNewBasicAnthill(AntFactoryProxy.mrNames[i], 100));
        }
        for (int i = 0; i < 50; i++) {
            Animal aardvark = factory.createNewAardvark(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            aardvark.addAnthill(anthills);
            Thread aardvarkThread = new Thread(aardvark);
            aardvarkThread.start();
            animalThread.add(aardvarkThread);
        }
        for (int i = 0; i < 50; i++) {
            Animal anteater = factory.createNewAnteater(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            anteater.addAnthill(anthills);
            Thread anteaterThread = new Thread(anteater);
            anteaterThread.start();
            animalThread.add(anteaterThread);
        }
        for (int i = 0; i < 15; i++) {
            Animal armadillo = factory.createNewArmadillo(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            armadillo.addAnthill(anthills);
            Thread armadilloThread = new Thread(armadillo);
            armadilloThread.start();
            animalThread.add(armadilloThread);
        }
        for (int i = 0; i < 100; i++) {
            Animal aardvark = factory.createNewAardvark(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            aardvark.addAnthill(anthills);
            Thread aardvarkThread = new Thread(aardvark);
            aardvarkThread.start();
            animalThread.add(aardvarkThread);
        }
        try {
            for (Thread t : animalThread) {
                t.join();
            }
            Anthill.DEFAULT_LOG.addToLog(AntEventType.END_TEST);
            verifierThread.join();
        } catch (InterruptedException ex) {
            assertTrue("Interruption exception occurred.", false);
        }
        assertTrue(verifier.printErrors(), !verifier.hasErrors());
    }

    @Test
    public void Scheduled_Anthill_Test() {
        AntLogVerifier verifier = new AntLogVerifier(Anthill.DEFAULT_LOG);
        DummyLog scheduler_log = new DummyLog();
        Thread verifierThread = new Thread(verifier);
        verifierThread.start();
        Collection<Anthill> anthills = new ArrayList<Anthill>();
        Collection<Thread> animalThread = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            anthills.add(factory.createNewBasicAnthill(AntFactoryProxy.mrNames[i], 100));
        }
        Anthill scheduledAnthill = factory.createNewScheduledAnthill("Scheduled", anthills, scheduler_log);
        for (int i = 0; i < 50; i++) {
            Animal aardvark = factory.createNewAardvark(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            aardvark.addAnthill(scheduledAnthill);
            Thread aardvarkThread = new Thread(aardvark);
            aardvarkThread.start();
            animalThread.add(aardvarkThread);
        }
        for (int i = 0; i < 50; i++) {
            Animal anteater = factory.createNewAnteater(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            anteater.addAnthill(scheduledAnthill);
            Thread anteaterThread = new Thread(anteater);
            anteaterThread.start();
            animalThread.add(anteaterThread);
        }
        for (int i = 0; i < 15; i++) {
            Animal armadillo = factory.createNewArmadillo(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            armadillo.addAnthill(scheduledAnthill);
            Thread armadilloThread = new Thread(armadillo);
            armadilloThread.start();
            animalThread.add(armadilloThread);
        }
        for (int i = 0; i < 100; i++) {
            Animal aardvark = factory.createNewAardvark(Integer.toString(i), Colour.values()[i % Colour.values().length]);
            aardvark.addAnthill(scheduledAnthill);
            Thread aardvarkThread = new Thread(aardvark);
            aardvarkThread.start();
            animalThread.add(aardvarkThread);
        }
        try {
            for (Thread t : animalThread) {
                t.join();
            }
            Anthill.DEFAULT_LOG.addToLog(AntEventType.END_TEST);
            verifierThread.join();
        } catch (InterruptedException ex) {
            assertTrue("Interruption exception occurred.", false);
        }
        assertTrue(verifier.printErrors(), !verifier.hasErrors());
    }

    private class AntLogVerifier implements Runnable {

        private final AntLog log;
        private final Collection<Animal> satisfiedAnimals;
        private Map<Anthill, Collection<Animal>> anthills;
        private List<String> errors;

        public AntLogVerifier(AntLog log) {
            this.log = log;
            this.anthills = new HashMap<Anthill, Collection<Animal>>();
            this.satisfiedAnimals = new ArrayList<Animal>();
            this.errors = new ArrayList<String>();
        }

        @Override
        public void run() {
            AntEvent currentEvent;
            do {
                currentEvent = log.get();
                Animal curAnimal = currentEvent.getAnimal();
                Anthill curAnthill = currentEvent.getAnthill();
                switch (currentEvent.getEvent()) {
                    case ENTER:
                        checkEnterConditions(curAnimal, curAnthill);
                        if (anthills.get(curAnthill) == null) {
                            anthills.put(curAnthill, new ArrayList<Animal>());
                        }
                        anthills.get(curAnthill).add(curAnimal);
                        break;
                    case LEAVE:
                        checkLeaveConditions(curAnimal, curAnthill);
                        anthills.get(curAnthill).remove(curAnimal);
                        break;
                    case FULL:
                        satisfiedAnimals.add(curAnimal);
                        break;
                    case ERROR:
                        errors.add("An error occurred during the simulation");
                        break;
                    case INTERRUPTED:
                        break;

                }
            } while (!currentEvent.getEvent().equals(AntEventType.END_TEST));
        }

        private void checkEnterConditions(Animal newAnimal, Anthill toAnthill) {
            if (satisfiedAnimals.contains(newAnimal)) {
                errors.add(String.format("%s entered %s when the animal is full.", newAnimal, toAnthill));
            }
            if (isArmadillo(newAnimal)) {
                checkArmadilloEntry(newAnimal, toAnthill);
            }
            if (isAnteater(newAnimal)) {
                checkAnteaterEntry(newAnimal, toAnthill);
            }
            if (isAardvark(newAnimal)) {
                checkAaardvarkEntry(newAnimal, toAnthill);
            }
        }

        private void checkLeaveConditions(Animal newAnimal, Anthill anthill) {
            if (satisfiedAnimals.contains(newAnimal)) {
                errors.add(String.format("%s was satisfied before leaving %s.", newAnimal, anthill));
            }
            Collection<Animal> currentOccupants = anthills.get(anthill);
            if (currentOccupants == null || currentOccupants.isEmpty() || !currentOccupants.contains(newAnimal)) {
                errors.add(String.format("%s left %s before entering.", newAnimal, anthill));
            }
        }

        private void checkAaardvarkEntry(Animal aardvark, Anthill anthill) {
            Collection<Animal> currentOccupants = anthills.get(anthill);
            if (currentOccupants != null && !currentOccupants.isEmpty()) {
                int aardvarkCount = 0;
                for (Animal din : currentOccupants) {
                    if (isAnteater(din)) {
                        errors.add(String.format("%s entered %s with %s.", aardvark, anthill, din));
                    }
                    if (isAardvark(din)) {
                        aardvarkCount++;
                        if (aardvarkCount > 1) {
                            errors.add(String.format("%s entered %s with multiple aardvarks present already.", aardvark, anthill, din));
                        }
                    }
                    if (aardvark.getColour().equals(din.getColour())) {
                        errors.add(String.format("%s entered %s with animals of the same color.", aardvark, anthill));
                    }
                }
            }
        }

        private void checkAnteaterEntry(Animal anteater, Anthill anthill) {
            Collection<Animal> currentOccupants = anthills.get(anthill);
            if (currentOccupants != null && !currentOccupants.isEmpty()) {
                for (Animal din : currentOccupants) {
                    if (!isArmadillo(din)) {
                        errors.add(String.format("%s entered %s with %s.", anteater, anthill, din));
                    }
                    if (anteater.getColour().equals(din.getColour())) {
                        errors.add(String.format("%s entered %s with animals of the same color.", anteater, anthill));
                    }
                }
            }
        }

        private void checkArmadilloEntry(Animal armadillo, Anthill anthill) {
            Collection<Animal> currentOccupants = anthills.get(anthill);
            if (currentOccupants == null || currentOccupants.isEmpty()) {
                errors.add(String.format("%s entered %s when it is empty. Armadillo must have company.", armadillo, anthill));
            }
            for (Animal din : currentOccupants) {
                if (armadillo.getColour().equals(din.getColour())) {
                    errors.add(String.format("%s entered %s with animals of the same color.", armadillo, anthill));
                }
                if (isArmadillo(din)) {
                    errors.add(String.format("%s entered %s with another armadillo (%s).", armadillo, anthill, din));
                }
            }
        }

        private boolean isAardvark(Animal animal) {
            return animal.toString().contains(AntFactoryProxy.aardName);

        }

        private boolean isAnteater(Animal animal) {
            return animal.toString().contains(AntFactoryProxy.anteName);

        }

        private boolean isArmadillo(Animal animal) {
            return animal.toString().contains(AntFactoryProxy.armName);
        }

        private boolean isExterminator(Animal animal) {
            return animal.toString().contains(AntFactoryProxy.exterName);
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public String printErrors() {
            StringBuilder builder = new StringBuilder();
            for (String er : errors) {
                builder.append(er);
                builder.append("\n");
            }
            System.out.println(builder);
            return builder.toString();
        }
    }
}
