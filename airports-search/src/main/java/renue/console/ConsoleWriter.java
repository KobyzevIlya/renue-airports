package renue.console;

class ConsoleWriter {
    public static void printNameAsk() {
        System.out.println("Input airport name (or part of it)");
    }

    public static void printFiltersAsk() {
        System.out.println("Input filters. Format: column[<column number [1-14]>]<comparison operation><comparison value>");
    }
}
