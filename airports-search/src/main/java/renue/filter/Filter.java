package renue.filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Filter {
    private Filter() {}
    
    private static List<String> toReversePolish(String expression) {
        List<String> outputQueue = new ArrayList<>();
        Deque<String> operatorStack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == '&' || c == '|') {
                if (sb.length() > 0) {
                    outputQueue.add(sb.toString());
                    sb.setLength(0);
                }
                while (!operatorStack.isEmpty()) {
                    char top = operatorStack.peek().charAt(0);
                    if (top == '(' || (c == '|' && top == '&')) {
                        break;
                    }
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(Character.toString(c));
            } else if (c == '(') {
                if (sb.length() > 0) {
                    outputQueue.add(sb.toString());
                    sb.setLength(0);
                }
                operatorStack.push(Character.toString(c));
            } else if (c == ')') {
                if (sb.length() > 0) {
                    outputQueue.add(sb.toString());
                    sb.setLength(0);
                }
                while (!operatorStack.isEmpty() && operatorStack.peek().charAt(0) != '(') {
                    outputQueue.add(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().charAt(0) == '(') {
                    operatorStack.pop();
                }
            } else {
                sb.append(c);
            }
        }

        if (sb.length() > 0) {
            outputQueue.add(sb.toString());
        }
        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean evaluateReversePolish(List<String> rpn, String[] row) {
        Deque<Boolean> valueStack = new ArrayDeque<>();
        for (String token : rpn) {
            if (token.equals("&")) {
                boolean b1 = valueStack.pop();
                boolean b2 = valueStack.pop();
                valueStack.push(b1 && b2);
            } else if (token.equals("|")) {
                boolean b1 = valueStack.pop();
                boolean b2 = valueStack.pop();
                valueStack.push(b1 || b2);
            } else if (isNumeric(token)) {
                valueStack.push(Double.parseDouble(token) != 0.0);
            } else if (token.matches("\\w+\\[\\d+\\][><=!]+('[\\w\\s]+'|\\d+)")) {
                String[] parts = token.split("\\[|\\]|(?<=[<>!=])|(?<=[^<>!=]\\s)'");
                int rowNumber = Integer.parseInt(parts[1]);
                String operator;
                String value;
                if (parts.length == 4) {
                    operator = parts[2];
                    value = parts[3];
                } else {
                    operator = parts[2] + parts[3];
                    value = parts[4];
                }
                valueStack.push(compare(row[rowNumber - 1], operator, value));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        return valueStack.pop();
    }

    public static List<String[]> filter(List<String[]> rows, String expression) {
        List<String> rpn = toReversePolish(expression);
        List<String[]> filteredRows = new ArrayList<>();
        for (String[] row : rows) {
            boolean result = evaluateReversePolish(rpn, row);
            if (result) {
                filteredRows.add(row);
            }
        }
        return filteredRows;
    }

    private static boolean compare(String value1, String operator, String value2) {
        if (value1 == null) {
            return false;
        }
        if ((value1.startsWith("\"") || value1.startsWith("'")) && (value1.endsWith("\"") || value1.endsWith("'"))) {
            value1 = value1.substring(1, value1.length() - 1);
        }
        if ((value2.startsWith("\"") || value2.startsWith("'")) && (value2.endsWith("\"") || value2.endsWith("'"))) {
            value2 = value2.substring(1, value2.length() - 1);
        }
        switch (operator) {
            case ">":
                return Double.parseDouble(value1) > Double.parseDouble(value2);
            case "<":
                return Double.parseDouble(value1) < Double.parseDouble(value2);
            case "=":
                return value1.equals(value2);
            case "<>":
                return !value1.equals(value2);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
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
