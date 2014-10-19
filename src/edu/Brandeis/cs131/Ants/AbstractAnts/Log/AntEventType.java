package edu.Brandeis.cs131.Ants.AbstractAnts.Log;

public enum AntEventType {

    ENTER("entered"),
    LEAVE("left"),
    FULL("is full"),
    ERROR("error in log"),
    END_TEST("end of test"),
    INTERRUPTED("interrupted");
    private final String name;

    private AntEventType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
