package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;
import edu.Brandeis.cs131.Ants.AbstractAnts.AntFactory;
import edu.Brandeis.cs131.Ants.AbstractAnts.Anthill;
import edu.Brandeis.cs131.Ants.AbstractAnts.Colour;
import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;
import java.util.Collection;

public class ConcreteAntFactory implements AntFactory {

    @Override
    public Anthill createNewBasicAnthill(String label, int numAnts) {
        return new BasicAnthill(label, numAnts);
    }

    @Override
    public Animal createNewAardvark(String label, Colour color) {
        return new Aardvark(label, color);
    }

    @Override
    public Animal createNewAnteater(String label, Colour color) {
    	return new Anteater(label, color);
    }

    @Override
    public Animal createNewArmadillo(String label, Colour color) {
    	return new Armadillo(label, color);
    }

    @Override
    public Animal createNewExterminator(String label, Colour color) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Anthill createNewScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log) {
        return new ScheduledAnthill(label, basicAnthills, log);
    }

    @Override
    public Anthill createNewPreemptiveAnthill(String label, Collection<Anthill> basicAnthills) {
    	return new ScheduledAnthill(label, basicAnthills);
    }
}
