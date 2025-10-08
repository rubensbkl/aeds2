/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q05 - Algebra Booleana - v1.0 - 09 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class AlgebraBooleana {

    // Simplifica a expressão para facilitar a interpretação
    public static String simplifyExpression(String input) {
        int varQtd = Character.getNumericValue(input.charAt(0));
        String expression = input.replace(" ", "");

        expression = expression.replace("and", "a")
                               .replace("or", "o")
                               .replace("not", "n");
        
        for (int i = 0; i < varQtd; i++) {
            char variable = (char) ('A' + i);
            char value = expression.charAt(i + 1);
            expression = expression.replace(variable, value);
        }
        return expression.substring(varQtd + 1);
    }

    // Resolve as subexpressões entre parênteses
    public static String expressionValue(String expression) {
        char operator = expression.charAt(0);
        boolean result;

        switch (operator) {
            case 'a': // AND
                result = !expression.contains("0");
                break;
            case 'o': // OR
                result = expression.contains("1");
                break;
            case 'n': // NOT
                result = expression.charAt(2) == '0';
                break;
            default:
                return "";
        }
        return result ? "1" : "0";
    }

    // Resolve a equação substituindo subexpressões entre parênteses
    public static boolean solveEquation(String input) {
        String expression = simplifyExpression(input);
        while (expression.contains("(")) {
            int left = expression.lastIndexOf("(");
            int right = expression.indexOf(")", left);
            
            String subExpression = expression.substring(left - 1, right + 1);
            String subValue = expressionValue(subExpression);
            
            expression = expression.substring(0, left - 1) + subValue + expression.substring(right + 1);
        }
        return expression.equals("1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("0")) {
            System.out.println(solveEquation(input) ? "1" : "0");
            input = scanner.nextLine();
        }
        scanner.close();
    }
}
