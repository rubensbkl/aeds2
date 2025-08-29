/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q04 - Alteração Aleatória - v1.0 - 27 / 08 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Random;

public class Alteracao {
    public static String randomize(String input, Random gerador) {
        char char1 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        char char2 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));

        String output = input.replace(char1, char2);

        return output;
    }

    public static void main(String[] args) {
        Random gerador = new Random();
        gerador.setSeed(4);

        String input = MyIO.readLine();
        do {
            MyIO.println(randomize(input, gerador));
            input = MyIO.readLine();
        }
        while (!input.equals("FIM"));
    }
}
