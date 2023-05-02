package renue.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import renue.trie.Data;

class FileReader {
    private RandomAccessFile RAFile;
    private String path;

    FileReader(String path) {
        try {
            RAFile = new RandomAccessFile(path, "r");
            this.path = path;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    List<Data> getLinesWithStartEndBytes() {
        List<Data> futureNodes = new ArrayList<>();

        String line = "null";
        int start = 0;
        int end = 0;

        try (FileInputStream fileInputStream = new FileInputStream(path);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            while ((line = bufferedReader.readLine()) != null) {
                start = end;
                end += line.getBytes("UTF-8").length + 1;
                futureNodes.add(new Data(line, start, end));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // try {
        //     RAFile.seek(0);

        //     while (true) {
        //         start = (int) RAFile.getFilePointer();
        //         line = RAFile.readLine();
        //         end = (int) RAFile.getFilePointer();

        //         if (line == null) {
        //             break;
        //         }

        //         futureNodes.add(new Data(line, start, end));
        //     }
        // } catch (IOException exception) {
        //     exception.printStackTrace();
        // }

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
