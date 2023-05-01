package renue.file;

import java.util.ArrayDeque;
import java.util.Deque;

import renue.trie.Data;

public class FileHandler {
    private FileReader fileReader;

    public FileHandler(String filePath) {
        fileReader = new FileReader(filePath);
    }

    public Deque<Data> getNodes() {
        Deque<Data> nodes = new ArrayDeque<>();
        nodes.addAll(fileReader.getLinesWithStartEndBytes());
        
        for (Data node : nodes) {
            node.setName(getAirtportName(node.getName()));
        }

        return nodes;
    }

    private String getAirtportName(String csvString) {
        String[] parts = csvString.split(",");
        if (parts.length >= 2) {
            String airportName = parts[1];
            airportName = airportName.replaceAll("^\"|\"$", "");

            return airportName;
        }

        return "null";
    }
}
