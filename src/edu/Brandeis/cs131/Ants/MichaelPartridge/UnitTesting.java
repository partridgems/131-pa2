package edu.Brandeis.cs131.Ants.MichaelPartridge;

import edu.Brandeis.cs131.Ants.AbstractAnts.Animal;

public class UnitTesting {

	public static void main(String[] args) {
		Animal animal = new Aardvark("Fred", null);
		type(animal);
		
		
	}
	
	public static void type (Animal a) {
		System.out.println("Animal");
	}
	
	public static void type (Aardvark a) {
		System.out.println("Aardvark");
	}

}
