package renue;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Deque;

import renue.file.FileHandler;
import renue.trie.Data;
import renue.trie.Trie;
import renue.utils.Pair;

public class Main {
    public static void main( String[] args ) {
        FileHandler fileHandler = new FileHandler("./src/main/java/renue/resources/airports.csv");
        Deque<Data> nodes = fileHandler.getNodes();

        Trie trie = new Trie();

        while (!nodes.isEmpty()) {
            trie.insert(nodes.removeFirst());
        }

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            for (Pair<Integer, Integer> pair : trie.search("Bor")) {
                System.out.println(pair.getFirst() + " " + pair.getSecond());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
