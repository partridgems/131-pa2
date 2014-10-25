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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SimulationAnthillTest {

    private AntFactory factory;
    public static boolean DEBUG_MODE = false;

    @Before
    public void setUp() {
        factory = AntFactoryProxy.getNewAntFactory();
        Anthill.DEFAULT_LOG.clearLog();
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
        private Map<Integer, AntEvent> potential_entry_events;
        private Map<Anthill, Collection<Animal>> exitSet;
        private Set<String> errors;

        public AntLogVerifier(AntLog log) {
            this.log = log;
            this.anthills = new HashMap<Anthill, Collection<Animal>>();
            this.potential_entry_events = new HashMap<Integer, AntEvent>();
            this.exitSet = new HashMap<Anthill, Collection<Animal>>();
            this.satisfiedAnimals = new ArrayList<Animal>();
            this.errors = new HashSet<String>();
        }

        @Override
        public void run() {
            AntEvent currentEvent;
            do {
                currentEvent = log.get();
                Animal curAnimal = currentEvent.getAnimal();
                Anthill curAnthill = currentEvent.getAnthill();
                if (curAnthill != null) {
                    if (exitSet.get(curAnthill) == null) {
                        exitSet.put(curAnthill, new ArrayList<Animal>());
                    }
                    if (anthills.get(curAnthill) == null) {
                        anthills.put(curAnthill, new ArrayList<Animal>());
                    }
                }
                if(SimulationAnthillTest.DEBUG_MODE && (!currentEvent.getEvent().equals(AntEventType.ENTER_ATTEMPT) && !currentEvent.getEvent().equals(AntEventType.ENTER_FAILED) && !currentEvent.getEvent().equals(AntEventType.LEAVE_START))){
                    System.out.println(currentEvent.toString());
                }
                switch (currentEvent.getEvent()) {
                    case ENTER_ATTEMPT:
                        potential_entry_events.put(currentEvent.getSignifier(), currentEvent);
                        break;
                    case ENTER_SUCCESS:
                        potential_entry_events.remove(currentEvent.getSignifier());
                        checkEnterConditions(curAnimal, curAnthill);
                        anthills.get(curAnthill).add(curAnimal);
                        break;
                    case ENTER_FAILED:
                        potential_entry_events.remove(currentEvent.getSignifier());
                        break;
                    case LEAVE_START:
                        checkLeaveConditions(curAnimal, curAnthill);
                        anthills.get(curAnthill).remove(curAnimal);
                        exitSet.get(curAnthill).add(curAnimal);
                    case LEAVE_END:
                        exitSet.get(curAnthill).remove(curAnimal);
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


            if (isAnteater(newAnimal)) {
                this.errors.addAll(verifyAnteaterEntry(newAnimal, toAnthill.toString(), anthills.get(toAnthill)));
            }
            if (isAardvark(newAnimal)) {
                this.errors.addAll(verifyAardvarkEntry(newAnimal, toAnthill.toString(), anthills.get(toAnthill)));
            }
            if (isArmadillo(newAnimal)) {
                //Armadillos complicate everything!
                //Armadillos are much more dependat on current occupants of an anthill then the other animals
                //As such we evaluate all 4 possible states of the anthill based on the log
                //And if any of them are valid, we presume this event happened in that timeslot
                Collection<Animal> known_occupants = anthills.get(toAnthill);
                Collection<Animal> potential_entry = this.buildPotentialEntrants(toAnthill);
                Collection<Animal> potential_exit = new HashSet<Animal>();
                Collection<Animal> potential_both = new HashSet<Animal>();

                potential_exit.addAll(known_occupants);
                potential_entry.addAll(known_occupants);
                potential_both.addAll(potential_entry);

                potential_exit.addAll(exitSet.get(toAnthill));
                potential_both.addAll(exitSet.get(toAnthill));

                Collection<String> known_set_errors = verifyArmadilloEntry(newAnimal, toAnthill.toString(), known_occupants);
                Collection<String> enter_set_errors = verifyArmadilloEntry(newAnimal, toAnthill.toString(), potential_entry);
                Collection<String> exit_set_errors = verifyArmadilloEntry(newAnimal, toAnthill.toString(), potential_exit);
                Collection<String> both_set_errors = verifyArmadilloEntry(newAnimal, toAnthill.toString(), potential_both);
                if (!known_set_errors.isEmpty() && !enter_set_errors.isEmpty() && !exit_set_errors.isEmpty() && !both_set_errors.isEmpty()) {
                    this.errors.addAll(known_set_errors);
                    this.errors.addAll(enter_set_errors);
                    this.errors.addAll(exit_set_errors);
                    this.errors.addAll(both_set_errors);
                }
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

        private Collection<String> verifyAardvarkEntry(Animal aardvark, String anthill, Collection<Animal> occupants) {
            Collection<String> errors = new ArrayList<String>();
            if (occupants != null && !occupants.isEmpty()) {
                int aardvarkCount = 0;
                for (Animal din : occupants) {
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
            return errors;
        }

        private Collection<String> verifyAnteaterEntry(Animal anteater, String anthill, Collection<Animal> occupants) {
            Collection<String> errors = new ArrayList<String>();
            if (occupants != null && !occupants.isEmpty()) {
                for (Animal din : occupants) {
                    if (!isArmadillo(din)) {
                        errors.add(String.format("%s entered %s with %s.", anteater, anthill, din));
                    }
                    if (anteater.getColour().equals(din.getColour())) {
                        errors.add(String.format("%s entered %s with animals of the same color.", anteater, anthill));
                    }
                }
            }
            return errors;
        }

        private Collection<String> verifyArmadilloEntry(Animal armadillo, String anthill, Collection<Animal> occupants) {
            Collection<String> errors = new ArrayList<String>();
            if (occupants == null || occupants.isEmpty()) {
                errors.add(String.format("%s entered %s when it is empty. Armadillo must have company.", armadillo, anthill));
            }
            for (Animal din : occupants) {
                if (armadillo.getColour().equals(din.getColour())) {
                    errors.add(String.format("%s entered %s with animals of the same color.", armadillo, anthill));
                }
                if (isArmadillo(din)) {
                    errors.add(String.format("%s entered %s with another armadillo (%s).", armadillo, anthill, din));
                }
            }
            return errors;
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
            return builder.toString();
        }

        private Set<Animal> buildPotentialEntrants(Anthill hill) {
            Set<Animal> fluxEntrants = new HashSet<Animal>();
            for (AntEvent event : this.potential_entry_events.values()) {
                if (event.getAnthill().getName().equals(hill.getName())) {
                    if (waitAndRetrieveEnterResult(event)) {
                        fluxEntrants.add(event.getAnimal());
                    }
                }
            }
            return fluxEntrants;
        }

        private boolean waitAndRetrieveEnterResult(AntEvent event) {
            AntEvent success = new AntEvent(event.getAnimal(), event.getAnthill(), AntEventType.ENTER_SUCCESS, event.getSignifier());
            AntEvent failure = new AntEvent(event.getAnimal(), event.getAnthill(), AntEventType.ENTER_FAILED, event.getSignifier());
            while (true) {
                if (this.log.contains(success)) {
                    return true;
                }
                if (this.log.contains(failure)) {
                    return false;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
