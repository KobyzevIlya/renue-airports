package renue.file;

import java.util.List;

import renue.trie.Data;

public class FileHandler {
    private FileReader fileReader;

    public FileHandler(String filePath) {
        fileReader = new FileReader(filePath);
    }

    public List<Data> getNodes() {
        List<Data> nodes = fileReader.getLinesWithStartEndBytes();
        
        for (Data node : nodes) {
            node.setName(getAirtportName(node.getName()));
        }

        return nodes;
    }

    public String[] getAirportInfo(int start, int end) {
        return fileReader.getLineByBytes(start, end).split(",");
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
