package edu.Brandeis.cs131.Ants.AbstractAnts.Log;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import java.util.Date;

public class AntEvent {

    private final Animal animal;
    private final Anthill anthill;
    private final AntEventType event;
    private final Date time;

    public AntEvent(Animal animal, Anthill anthill, AntEventType event) {
        this.animal = animal;
        this.anthill = anthill;
        this.event = event;
        this.time = new Date();
    }

    public AntEvent(Animal animal, AntEventType event) {
        this(animal, null, event);
    }

    public AntEvent(AntEventType event) {
        this(null, null, event);
    }

    public Animal getAnimal() {
        return animal;
    }

    public Anthill getAnthill() {
        return anthill;
    }

    public AntEventType getEvent() {
        return event;
    }

    public Date getEventTime() {
        return time;
    }

    @Override
    public String toString() {
        switch (event) {
            case END_TEST:
            case ERROR:
                return event.toString();
            case FULL:
                return String.format("%s %s", animal, event);
            default:
                return String.format("%s %s %s", animal, event, anthill);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AntEvent) {
            AntEvent event = (AntEvent) o;
            return this.toString().equals(event.toString());
        } else {
            return false;
        }
    }
}
