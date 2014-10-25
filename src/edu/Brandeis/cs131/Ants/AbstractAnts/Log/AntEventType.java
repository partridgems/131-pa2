package edu.Brandeis.cs131.Ants.AbstractAnts.Log;

public enum AntEventType {
    ENTER_ATTEMPT("trying to enter"),
    ENTER_SUCCESS("entered successfully"),
    ENTER_FAILED("failed to enter"),
    LEAVE_START("leaving"),
    LEAVE_END("left"),
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
