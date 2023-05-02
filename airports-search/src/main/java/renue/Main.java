package renue;

import renue.cycle.Cycle;

public class Main {
    public static void main( String[] args ) {
        Cycle cycle = new Cycle("./airports-search/src/main/resources/airports.csv");
        cycle.run();
    }
}
