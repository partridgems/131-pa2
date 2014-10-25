package edu.Brandeis.cs131.Ants.MichaelPartridge;

import java.util.Arrays;

public class UnitTesting {

	public static void main(String[] args) {

		Boolean b = false;
		synchronized (b) {
			try {
				b.wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
