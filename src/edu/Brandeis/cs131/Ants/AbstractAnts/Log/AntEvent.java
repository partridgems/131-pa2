package edu.Brandeis.cs131.Ants.AbstractAnts.Log;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;

public class AntEvent {

    private final Animal animal;
    private final Anthill anthill;
    private final AntEventType event;
    private final int signifier;
    //there is no guarantee the signifier is 100% unique
    //but given the size of our tests vs the possible values for the signifier we forsee little clashing

    public AntEvent(Animal animal, Anthill anthill, AntEventType event, int signifier) {
        this.animal = animal;
        this.anthill = anthill;
        this.event = event;
        this.signifier = signifier;
    }

    public AntEvent(Animal animal, Anthill anthill, AntEventType event) {
        this(animal, anthill, event, (int) System.currentTimeMillis());
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
    
    public int getSignifier() {
        return signifier;
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
            return event.getSignifier() == this.signifier && this.weakEquals(event);
        } else {
            return false;
        }

    }

    public boolean weakEquals(AntEvent event) {
        //A weaker version of equals,  checks the anthill, animal, and event type are the same.
        //Useful for checking if an event was logged, when the exact logging details are unknown.
        return this.toString().equals(event.toString());
    }
}
