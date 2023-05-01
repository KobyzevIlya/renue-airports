package renue.file;

import java.util.List;

import renue.trie.Node;

public class FileHandler {
    private FileReader fileReader;

    public FileHandler(String filePath) {
        fileReader = new FileReader(filePath);
    }

    public List<Node> getNodes() {
        List<Node> nodes = fileReader.getLinesWithStartEndBytes();
        
        for (Node node : nodes) {
            node.setData(getAirtportName(node.getData()));
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
