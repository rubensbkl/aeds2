/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP02Q01 - Algebra Booleana - v1.0 - 04 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class Main {

    // Verifica se duas strings são iguais
    public static boolean equals(String a, String b) {
        int a_len = a.length();
        int b_len = b.length();
        if (a_len != b_len) {
            return false;
        }
        for (int i = 0; i < a_len; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // Substitui um caractere por outro em uma string
    public static String replace(String input, char oldChar, char newChar) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == oldChar) {
                result += newChar;
            } else {
                result += c;
            }
        }
        return result;
    }

    // Seleciona uma substring de uma string
    public static String substring(String input, int start, int end) {
        String result = "";
        for (int i = start; i < end && i < input.length(); i++) {
            result += input.charAt(i);
        }
        return result;
    }

    // Verifica se a string contém um caractere
    public static boolean contains(String input, char c) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == c) return true;
        }
        return false;
    }

    // Simplifica a expressão para facilitar a interpretação
    public static String simplifyExpression(String input) {
        int varQtd = input.charAt(0) - '0';
        String expression = "";

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ') expression += c;
        }

        String temp = "";
        for (int i = 0; i < expression.length(); i++) {
            if (i + 2 < expression.length() && 
                expression.charAt(i) == 'a' && expression.charAt(i+1) == 'n' && expression.charAt(i+2) == 'd') {
                temp += 'a';
                i += 2;
            } else if (i + 1 < expression.length() && 
                expression.charAt(i) == 'o' && expression.charAt(i+1) == 'r') {
                temp += 'o';
                i += 1;
            } else if (i + 2 < expression.length() &&
                expression.charAt(i) == 'n' && expression.charAt(i+1) == 'o' && expression.charAt(i+2) == 't') {
                temp += 'n';
                i += 2;
            } else {
                temp += expression.charAt(i);
            }
        }
        expression = temp;

        // Substitui variáveis pelos valores
        for (int i = 0; i < varQtd; i++) {
            char variable = (char) ('A' + i);
            char value = expression.charAt(i + 1);
            expression = replace(expression, variable, value);
        }

        return substring(expression, varQtd + 1, expression.length());
    }

    // Resolve subexpressões entre parênteses
    public static String expressionValue(String expression) {
        char operator = expression.charAt(0);
        boolean result = false;

        switch (operator) {
            case 'a': // AND
                result = !contains(expression, '0');
                break;
            case 'o': // OR
                result = contains(expression, '1');
                break;
            case 'n': // NOT
                result = expression.charAt(2) == '0';
                break;
        }
        return result ? "1" : "0";
    }

    // Substitui subexpressões entre parênteses
    public static boolean solveEquation(String input) {
        String expression = simplifyExpression(input);

        while (contains(expression, '(')) {
            int left = -1;
            int right = -1;

            for (int i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == '(') left = i;
            }

            for (int i = left; i < expression.length(); i++) {
                if (expression.charAt(i) == ')') {
                    right = i;
                    break;
                }
            }

            String subExpression = "";
            if (left > 0) subExpression += expression.charAt(left - 1);
            for (int i = left; i <= right; i++) subExpression += expression.charAt(i);

            String subValue = expressionValue(subExpression);

            String newExpr = "";
            for (int i = 0; i < left - 1; i++) newExpr += expression.charAt(i);
            newExpr += subValue;
            for (int i = right + 1; i < expression.length(); i++) newExpr += expression.charAt(i);

            expression = newExpr;
        }

        return equals(expression, "1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!equals(input, "0")) {
            System.out.println(solveEquation(input) ? "1" : "0");
            input = scanner.nextLine();
        }
        scanner.close();
    }
}
