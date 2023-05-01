package renue;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Deque;

import renue.console.ConsoleHandler;
import renue.file.FileHandler;
import renue.trie.Data;
import renue.trie.Trie;
import renue.utils.Pair;

public class Main {
    public static void main( String[] args ) {
        FileHandler fileHandler = new FileHandler("./airports-search/src/main/java/renue/resources/airports.csv");
        Deque<Data> nodes = fileHandler.getNodes();

        Trie trie = new Trie();

        while (!nodes.isEmpty()) {
            trie.insert(nodes.removeFirst());
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        String name = consoleHandler.getName();

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            for (Pair<Integer, Integer> pair : trie.search(name)) {
                System.out.println(pair.getFirst() + " " + pair.getSecond());
                System.out.println(fileHandler.getAirportInfo(pair.getFirst(), pair.getSecond()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
