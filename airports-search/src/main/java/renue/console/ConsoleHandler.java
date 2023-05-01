package renue.console;

public class ConsoleHandler {
    private ConsoleReader consoleReader;
    private ConsoleWriter consoleWriter;

    public ConsoleHandler() {
        consoleReader = new ConsoleReader();
        consoleWriter = new ConsoleWriter();
    }

    public String getName() {
        ConsoleWriter.printNameAsk();
        String name = consoleReader.getLine();
        return name;
    }
}
