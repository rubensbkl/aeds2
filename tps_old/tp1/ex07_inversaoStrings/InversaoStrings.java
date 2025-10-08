/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q07 - Inversão de Strings - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class InversaoStrings {
    public static String inverterString(String input) {
        StringBuilder inverseString = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            inverseString.append(input.charAt(i));
        }
        return inverseString.toString();
    }

    public static void main(String[] args) {
        String input = MyIO.readLine();
        do {
            MyIO.println(inverterString(input));
            input = MyIO.readLine();
        }
        while (!input.equals("FIM"));
    }
}
