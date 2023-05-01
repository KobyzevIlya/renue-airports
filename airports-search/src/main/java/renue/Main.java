package renue;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import renue.file.FileHandler;
import renue.trie.Node;

public class Main {
    public static void main( String[] args ) {
        FileHandler fileHandler = new FileHandler("./src/main/java/renue/resources/airports.csv");
        List<Node> nodes = fileHandler.getNodes();

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));

            for (Node node : nodes) {
                System.out.println(node.getData() + " " + node.getStartByte() + " " + node.getEndByte());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
