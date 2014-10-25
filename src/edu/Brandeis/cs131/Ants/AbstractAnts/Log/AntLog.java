package edu.Brandeis.cs131.Ants.AbstractAnts.Log;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AntLog {

    private final BlockingQueue<AntEvent> log = new LinkedBlockingQueue<AntEvent>();

    public AntLog() {
    }

    public void addToLog(Animal animal, Anthill anthill, AntEventType type, int sig) {
        try {
            log.put(new AntEvent(animal, anthill, type, sig));
        } catch (InterruptedException ex) {
            add_error_msg();
            ex.printStackTrace();
        }
    }
    
    public void addToLog(Animal animal, Anthill anthill, AntEventType type) {
        try {
            log.put(new AntEvent(animal, anthill, type));
        } catch (InterruptedException ex) {
            add_error_msg();
            ex.printStackTrace();
        }
    }

    public void addToLog(Animal animal, AntEventType type) {
        try {
            log.put(new AntEvent(animal, type));
        } catch (InterruptedException ex) {
            add_error_msg();
            ex.printStackTrace();
        }
    }

    public void addToLog(AntEventType type) {
        try {
            log.put(new AntEvent(type));
        } catch (InterruptedException ex) {
            add_error_msg();
            ex.printStackTrace();
        }
    }

    public final boolean isEmpty() {
        return log.isEmpty();
    }

    private void add_error_msg() {
        try {
            log.put(new AntEvent(AntEventType.ERROR));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public final void clearLog() {
        log.clear();
    }

    public final AntEvent get() {
        AntEvent next = new AntEvent(AntEventType.ERROR);
        try {
            next = log.take();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return next;
    }
    
    public boolean contains(AntEvent event){
        return log.contains(event);
    }
    
    public AntEvent peek(){
        return log.peek();
    }
    
    
}
