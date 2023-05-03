package renue.console;

import java.util.List;

import renue.filter.Filter;

public class ConsoleHandler {
    private ConsoleReader consoleReader;
    private ConsoleWriter consoleWriter;

    public ConsoleHandler() {
        consoleReader = new ConsoleReader();
        consoleWriter = new ConsoleWriter();
    }

    public String getName() {
        consoleWriter.printNameAsk();
        String name = consoleReader.getLine();
        return name;
    }

    public String getFilters() {
        String filters;
        do {
            consoleWriter.printFiltersAsk();
            filters = consoleReader.getLine();
        } while (!Filter.isValidFilter(filters));

        return filters;
    }

    public void printAirportsInfos(List<String[]> airportsInfos) {
        for (String[] str : airportsInfos) {
            consoleWriter.printString(String.join(",", str));;
        }
    }

    public void printFoundAirportsCount(int count) {
        consoleWriter.printAirportsCount(count);
    }

    public void printElapsedTime(long time) {
        consoleWriter.printElapsedTime(time);
    }
}
