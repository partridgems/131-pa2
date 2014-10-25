package edu.Brandeis.cs131.Ants.AbstractAnts;

import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntEventType;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;

/**
 * An Anthill is an object which can be eaten at by animals. Animals themselves
 * are responsible for indicating when they want to eat at and when they are
 * ready to leave. Anthills are responsible for indicating if it is safe for an
 * Animal to enter.
 *
 * When an animal wants to enter a anthill, it calls tryToJoin on the anthill
 * instance. If the animal has entered the anthill successfully, tryToJoin
 * returns true. Otherwise, tryToJoin returns false. The animal simulates the
 * time spent at the anthill, and then must call exitAnthill on the same anthill
 * instance it entered.
 */
public abstract class Anthill {

    private final String name;
    private int ants;
    public static AntLog DEFAULT_LOG = new AntLog();
    private final AntLog log;
    /* DO NOT alter code to add or remove items to the log. The log is used to
     ensure rules are adhered to by the test cases. It does not, should not and
     * must not serve as a mechanism for communication between anthills and 
     * animals.*/

    public Anthill(String name, int ants, AntLog log) {
        this.name = name;
        this.ants = ants;
        this.log = log;
    }

    public Anthill(String name, int ants) {
        this(name, ants, Anthill.DEFAULT_LOG);
    }

    public final boolean attemptToEatAt(Animal animal) {
        int commonSig = (int) (Math.random()*Integer.MAX_VALUE);
        log.addToLog(animal, this, AntEventType.ENTER_ATTEMPT, commonSig);
        if (this.tryToEatAt(animal)) {
            log.addToLog(animal, this, AntEventType.ENTER_SUCCESS, commonSig);
            return true;
        } else {
            log.addToLog(animal, this, AntEventType.ENTER_FAILED, commonSig);
            return false;
        }
    }

    /**
     * animal tries to eat at an anthill.
     *
     * @param animal The animal that is attempting to ent at the anthill
     * @return true if the animal was able to start eating at the anthill, false
     * otherwise
     */
    public abstract boolean tryToEatAt(Animal animal);

    public final void leaveAnthill(Animal animal) {
        this.log.addToLog(animal, this, AntEventType.LEAVE_START);
        this.exitAnthill(animal);
        this.log.addToLog(animal, this, AntEventType.LEAVE_END);
    }

    /**
     * animal exits the anthill.
     *
     * @param animal The animal that is leaving the anthill
     */
    public abstract void exitAnthill(Animal animal);

    /**
     * returns the number of ants left in the anthill must be checked before an
     * animal tries to eat at to make sure there are ants left
     */
    public int antsLeft() {
        return ants;
    }

    public void eatAnt() {
        ants--;
    }

    public AntLog getLog() {
        return log;
    }

    /**
     * Returns the name of this anthill
     *
     * @return The name of this anthill
     */
    public String getName() {
        return this.name;
    }

    public String toString() {
        return String.format("%s", this.name);
    }
}
