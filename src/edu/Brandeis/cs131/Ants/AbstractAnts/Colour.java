package edu.Brandeis.cs131.Ants.AbstractAnts;

public enum Colour {

    RED("RED"),
    ORANGE("ORANGE"),
    YELLOW("YELLOW"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    INDIGO("INDIGO"),
    VIOLET("VIOLET");
    private final String name;

    private Colour(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Colour random() {
        int i = (int) (Math.random() * Colour.values().length);
        return Colour.values()[i];
    }
};