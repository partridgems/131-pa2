package edu.Brandeis.cs131.Ants.AbstractAnts.Log;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;

public class DummyLog extends AntLog {

    public final void addToLog(Animal animal, Anthill anthill, AntEventType type) {
        //do nothing
    }

    public void addToLog(Animal animal, AntEventType type) {
        //do nothing
    }

    public void addToLog(AntEventType type) {
        //do nothing
    }
}
