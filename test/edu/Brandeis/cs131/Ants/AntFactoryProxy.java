package edu.Brandeis.cs131.Ants;

import edu.Brandeis.cs131.Ants.AbstractAnts.AntFactory;
import edu.Brandeis.cs131.Ants.MichaelPartridge.ConcreteAntFactory;

public class AntFactoryProxy {

    public static final String aardName = "AARDVARK";
    public static final String anteName = "ANTEATER";
    public static final String armName = "ARMADILLO";
    public static final String exterName = "EXTERMINATOR";
    //Names used in testing
    public static final String[] gbNames = {"VENKMAN", "STANTZ", "SPENGLER", "ZEDDEMORE", "BARRETT", "TULLY", "MELNITZ", "PECK", "LENNY", "GOZER", "SLIMER", "STAY PUFT", "GATEKEEPER", "KEYMASTER"};
    public static final String[] mrNames = {"CATSKILL", "ROCKY", "APPALACHIAN", "OLYMPIC", "HIMALAYA", "GREAT DIVIDING", "TRANSANTRIC", "URAL", "ATLAS", "ALTAI", "CARPATHIAN", "KJOLEN", "BARISAN", "COAST", "QIN", "WESTERN GHATS"};

    public static AntFactory getNewAntFactory() {
        return new ConcreteAntFactory();
    }
}
