package tps.tp1.ex16_palindromoRecursivoJava;
/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q16 - Palíndromo (Recursivo) - v1.0 - 09 / 02 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class PalindromoRecursivo {

    public static boolean isPalindromoRec(String input, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (input.charAt(left) != input.charAt(right)) {
            return false;
        }
        return isPalindromoRec(input, left + 1, right - 1);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String input;
        
        while (!(input = scanner.nextLine()).equals("FIM")) {
            System.out.println(isPalindromoRec(input, 0, input.length() - 1) ? "SIM" : "NAO");
        }
        scanner.close();
    }
}