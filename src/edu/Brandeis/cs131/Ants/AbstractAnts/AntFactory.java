package edu.Brandeis.cs131.Ants.AbstractAnts;

import edu.Brandeis.cs131.Ants.AbstractAnts.Log.AntLog;
import java.util.Collection;

public interface AntFactory {

    public abstract Anthill createNewBasicAnthill(String label, int numAnts);

    public abstract Anthill createNewScheduledAnthill(String label, Collection<Anthill> basicAnthills, AntLog log);

    public abstract Anthill createNewPreemptiveAnthill(String label, Collection<Anthill> basicAnthills);

    public abstract Animal createNewAardvark(String label, Colour color);

    public abstract Animal createNewAnteater(String label, Colour color);

    public abstract Animal createNewArmadillo(String label, Colour color);

    public abstract Animal createNewExterminator(String label, Colour color);
}
