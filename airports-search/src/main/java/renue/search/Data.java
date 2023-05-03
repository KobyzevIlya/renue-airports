package renue.search;

public class Data {
    private String name;
    private int startByte;
    private int endByte;

    public Data(String name, int startByte, int endByte) {
        this.name = name;
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public String getName() {
        return name;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getEndByte() {
        return endByte;
    }

    public void setName(String name) {
        this.name = name;
    }
}
