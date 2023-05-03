package renue.console;

class ConsoleWriter {
    void printNameAsk() {
        System.out.println("Input airport name (or part of it)");
    }

    void printFiltersAsk() {
        System.out.println("Input filters. Format: column[<column number [1-14]>]<comparison operation><comparison value>");
    }

    void printString(String s) {
        System.out.print(s);
    }

    void printAirportsCount(int count) {
        System.out.println("Количество найденных строк: " + count);
    }

    void printElapsedTime(long time) {
        System.out.println("Время, затраченное на поиск: " + time + " мс");
    }
}
