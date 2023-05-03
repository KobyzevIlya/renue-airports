package renue;

import renue.cycle.Cycle;

public class Main {
    public static void main( String[] args ) {
        Cycle cycle = new Cycle("./src/main/resources/airports.csv");
        cycle.run();
    }
}
