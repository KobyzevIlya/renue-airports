package renue.cycle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import renue.console.ConsoleHandler;
import renue.file.FileHandler;
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

        String name = null;
        String filters = null;

        while (name != "!quit" && filters != "quit") {
            name = consoleHandler.getName();
            if (name == "!quit") {
                break;
            }

            long startTime = System.currentTimeMillis();

            List<Pair<Integer, Integer>> airportsInfos = trie.search(name);
            for (Pair<Integer, Integer> info : airportsInfos) {
                System.out.print(fileHandler.getAirportInfo(info.getFirst(), info.getSecond()));
            } 

            long endTime = System.currentTimeMillis();

            long elapsedTime = endTime - startTime;

            System.out.println("\n" + "Количество найденных строк: " + airportsInfos.size());
            System.out.println("Время, затраченное на поиск: " + elapsedTime + " мс" + "\n");
        }
    }
}
