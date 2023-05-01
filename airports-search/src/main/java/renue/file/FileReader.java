package renue.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import renue.trie.Data;

class FileReader {
    private RandomAccessFile RAFile;

    FileReader(String path) {
        try {
            RAFile = new RandomAccessFile(path, "r");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    List<Data> getLinesWithStartEndBytes() {
        List<Data> futureNodes = new ArrayList<>();

        String line = "null";
        int start;
        int end;

        try {
            RAFile.seek(0);

            while (true) {
                start = (int) RAFile.getFilePointer();
                line = RAFile.readLine();
                end = (int) RAFile.getFilePointer();

                if (line == null) {
                    break;
                }

                futureNodes.add(new Data(line, start, end));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return futureNodes;
    }

    String getLineByBytes(int startByte, int endByte) {
        String line = null;
        try {
            RAFile.seek(startByte);
            byte[] bytes = new byte[(int)(endByte - startByte)];
            RAFile.read(bytes);

            line = new String(bytes);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return line;
    }
}
