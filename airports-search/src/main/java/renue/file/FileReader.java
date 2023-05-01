package renue.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import renue.trie.Node;

class FileReader {
    private RandomAccessFile RAFile;

    FileReader(String path) {
        try {
            RAFile = new RandomAccessFile(path, "r");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    List<Node> getLinesWithStartEndBytes() {
        List<Node> futureNodes = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(RAFile.getFD()), "UTF-8"));

            int byteCount = 0;

            String line;
            int start;
            int end;
            while ((line = bufferedReader.readLine()) != null) {
                int lineByteCount = line.getBytes().length + System.lineSeparator().length();

                start = byteCount + 1;
                end = lineByteCount + byteCount;
                byteCount += lineByteCount;

                futureNodes.add(new Node(line, start, end));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        
        return futureNodes;
    }
}
