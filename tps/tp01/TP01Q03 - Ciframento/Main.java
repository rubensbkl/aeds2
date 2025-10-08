/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q03 - Ciframento - v1.0 - 28 / 08 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */


import libs.MyIO;


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

    // Encripta a string passada
    public static String encrypt(String input) {
        String encrypted = ""; 

        for (int i = 0; i < input.length(); i++) {
            char c = (char) (input.charAt(i) + 3); 
            encrypted = encrypted + c;
        }

        return encrypted;
    }

    public static void main(String[] args) {
        String input = MyIO.readLine();
        while (!equals(input, "FIM")) {
            MyIO.println(encrypt(input));
            input = MyIO.readLine();
        }
    }
}