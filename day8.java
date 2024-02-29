import java.util.*;

public class day1 {
    public static void main(String[] args) {
        String expression = "2 + 3 * 4 - 6 / 2";
        System.out.println("Result: " + parse(expression));
    }

    public static double parse(String expression) {
        Deque<Double> operands = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    processOperation(operands, operators);
                }
                operators.push(c);
            } else {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    operand.append(expression.charAt(i++));
                }
                --i;
                operands.push(Double.parseDouble(operand.toString()));
            }
        }

        while (!operators.isEmpty()) {
            processOperation(operands, operators);
        }

        return operands.pop();
    }

    private static void processOperation(Deque<Double> operands, Deque<Character> operators) {
        double rightOperand = operands.pop();
        double leftOperand = operands.pop();
        char operator = operators.pop();
        switch (operator) {
            case '+':
                operands.push(leftOperand + rightOperand);
                break;
            case '-':
                operands.push(leftOperand - rightOperand);
                break;
            case '*':
                operands.push(leftOperand * rightOperand);
                break;
            case '/':
                operands.push(leftOperand / rightOperand);
                break;
        }
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
}
