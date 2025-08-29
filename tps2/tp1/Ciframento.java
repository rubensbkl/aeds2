/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q03 - Ciframento de César - v1.0 - 27 / 08 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class Ciframento {

    public static String encrypt(String input) {

        for (int i = 0; i < input.length(); i++) {
            input.charAt(i) = input.charAt(i) + 3;
        }
        return input;
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

