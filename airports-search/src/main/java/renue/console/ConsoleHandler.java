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

    public String getFilters() {
        String filters;
        do {
            ConsoleWriter.printFiltersAsk();
            filters = consoleReader.getLine();
        } while (!isValidFilter(filters));

        return filters;
    }

    public static boolean isValidFilter(String filter) {
        if (filter == null || filter.isEmpty()) {
            return true;
        }

        filter = filter.trim();

        if (!filter.matches("[\\w\\[\\]><=!'&|()\\s]+")) {
            return false;
        }

        String[] tokens = filter.split("\\s+");

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (token.equals("&") || token.equals("||")) {
                if (i == 0 || i == tokens.length - 1 || tokens[i - 1].equals("(") || tokens[i + 1].equals(")")) {
                    return false;
                }
            } else if (token.equals("(")) {
                if (i == tokens.length - 1 || tokens[i + 1].equals(")")
                        || (i > 0 && tokens[i - 1].matches("[\\w\\]]+"))) {
                    return false;
                }
            } else if (token.equals(")")) {
                if (i == 0 || tokens[i - 1].equals("(")
                        || (i < tokens.length - 1 && tokens[i + 1].matches("[\\w\\[]+"))) {
                    return false;
                }
            } else if (token.matches("\\w+\\[\\d+\\][><=!]+('[\\w\\s]+'|\\d+)")) {
                String[] parts = token.split("\\[|\\]|(?<=[<>!=])|(?<=[^<>!=]\\s)'");
                if (parts.length == 4) {
                    if (!parts[0].equals("column") || !parts[1].matches("[1-9]|1[0-4]") ||
                            (!parts[2].equals("=") && !parts[2].equals(">") && !parts[2].equals("<")) ||
                            (!parts[3].matches("^'\\w+'$") && !parts[3].matches("\\d+"))) {
                        return false;
                    }
                } else if (parts.length == 5) {
                    if (!parts[0].equals("column") || !parts[1].matches("[1-9]|1[0-4]") ||
                            (!parts[2].equals("<") && !parts[3].equals(">")) ||
                            (!parts[4].matches("^'\\w+'$") && !parts[4].matches("\\d+"))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        int balance = 0;
        for (String token : tokens) {
            if (token.equals("(")) {
                balance++;
            } else if (token.equals(")")) {
                balance--;
                if (balance < 0) {
                    return false;
                }
            }
        }
        return balance == 0;
    }
}
