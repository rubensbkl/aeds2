package tps.tp1.ex18_ciframentoCesarRecursivo;
/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q18 - Ciframento de César (Recursivo) - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class CiframentoCesarRecursivo {

    public static String encrypt(String input, int index) {
        if (index == input.length()) {
            return "";
        }
        return (char) (input.charAt(index) + 3) + encrypt(input, index + 1);
    }

    public static void main(String[] args) {
        String input = MyIO.readLine();
        do {
            MyIO.println(encrypt(input, 0));
            input = MyIO.readLine();
        }
        while (!input.equals("FIM"));
    }
}
