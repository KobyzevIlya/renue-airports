package renue.trie;

public class Node {
    private String data;
    private int startByte;
    private int endByte;

    public Node(String data, int startByte, int endByte) {
        this.data = data;
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public String getData() {
        return data;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getEndByte() {
        return endByte;
    }

    public void setData(String data) {
        this.data = data;
    }
}
