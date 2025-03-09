/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q03 - Ciframento de César - v1.0 - 07 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class Ciframento {

    public static String encrypt(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append((char) (c + 3));
        }
        return output.toString();
    }

    public static void main(String[] args) {
        String input = MyIO.readLine();
        do {
            MyIO.println(encrypt(input));
            input = MyIO.readLine();
        }
        while (!input.equals("FIM"));
    }
}