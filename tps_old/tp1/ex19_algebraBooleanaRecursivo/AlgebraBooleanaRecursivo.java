/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q19 - Algebra Booleana (Recursivo) - v1.0 - 09 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class AlgebraBooleanaRecursivo {

    // Remove espaços e substitui operadores por símbolos mais curtos
    public static String simplifyExpression(String input) {
        int varCount = Character.getNumericValue(input.charAt(0));
        String expression = input.replace(" ", "");

        expression = expression.replace("and", "a")
                               .replace("or", "o")
                               .replace("not", "n");
        
        for (int i = 0; i < varCount; i++) {
            char variable = (char) ('A' + i);
            char value = expression.charAt(i + 1);
            expression = expression.replace(variable, value);
        }
        return expression.substring(varCount + 1);
    }

    // Avalia a expressão lógica
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

    // Resolve a equação de forma recursiva
    public static String recursiveEquation(String expression) {
        if (!expression.contains("(")) {
            return expression;
        }
        
        int left = expression.lastIndexOf("(");
        int right = expression.indexOf(")", left);
        
        String subExpression = expression.substring(left - 1, right + 1);
        String subValue = expressionValue(subExpression);
        
        String output = expression.substring(0, left - 1) + subValue + expression.substring(right + 1);
        return recursiveEquation(output);
    }

    public static boolean solveEquation(String input) {
        String expression = simplifyExpression(input);
        String result = recursiveEquation(expression);
        return result.equals("1");
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
