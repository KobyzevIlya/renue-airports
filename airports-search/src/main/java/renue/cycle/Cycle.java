package renue.cycle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import renue.console.ConsoleHandler;
import renue.file.FileHandler;
import renue.filter.Filter;
import renue.trie.Data;
import renue.trie.Trie;
import renue.utils.Pair;

public class Cycle {
    private String path;

    public Cycle(String filePath) {
        path = filePath;
    }

    public void run() {
        FileHandler fileHandler = new FileHandler(path);
        Deque<Data> nodes = new ArrayDeque<>();
        nodes.addAll(fileHandler.getNodes());

        Trie trie = new Trie();

        while (!nodes.isEmpty()) {
            trie.insert(nodes.removeFirst());
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();

        String name = "null";
        String filters = "null";

        while (!name.equals("!quit") && !filters.equals("!quit")) {
            filters = consoleHandler.getFilters();
            if (filters.equals("!quit")) {
                break;
            }
            
            name = consoleHandler.getName();
            if (name.equals("!quit")) {
                break;
            }

            List<String[]> airportsInfos = new ArrayList<>();
            long startTime = System.currentTimeMillis();

            List<Pair<Integer, Integer>> airportsLocations = trie.search(name);
            for (Pair<Integer, Integer> airport : airportsLocations) {
                airportsInfos.add(fileHandler.getAirportInfo(airport.getFirst(), airport.getSecond()));
            }
            airportsInfos = Filter.filter(airportsInfos, filters);

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            
            consoleHandler.printAirportsInfos(airportsInfos);
            consoleHandler.printFoundAirportsCount(airportsInfos.size());
            consoleHandler.printElapsedTime(elapsedTime);
        }
    }
}
