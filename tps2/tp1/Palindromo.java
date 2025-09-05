/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q01 - Palíndromo - v1.0 - 28 / 08 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class Palindromo {

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

    // Verifica se a string passada é palíndromo
    public static boolean isPalindromo(String input) {
        int length = input.length();
        for (int i = 0; i < length / 2; i++) {
            if (input.charAt(i) != input.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!(equals(input, "FIM"))) {
            System.out.println(isPalindromo(input) ? "SIM" : "NAO");
            input = sc.nextLine();
        }
        sc.close();
    }

}