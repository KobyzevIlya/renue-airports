package renue.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import renue.trie.Data;

class FileReader {
    private String path;

    FileReader(String path) {
        this.path = path;
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
                end += line.getBytes("UTF-8").length + System.lineSeparator().length(); // TODO check +1 or +line separator
                futureNodes.add(new Data(line, start, end));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return futureNodes;
    }

    String getLineByBytes(int startByte, int endByte) {
        String line = null;
        try (RandomAccessFile RAFile = new RandomAccessFile(path, "r");) {
            RAFile.seek(startByte);
            byte[] bytes = new byte[(int)(endByte - startByte)];
            RAFile.read(bytes);

            line = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return line;
    }
}
