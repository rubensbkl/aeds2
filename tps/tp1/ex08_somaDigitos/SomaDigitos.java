
/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q08 - Soma de Dígitos - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class SomaDigitos {
    public static int soma(int input) {
        if (input == 0) {
            return 0;
        }
        return (input % 10) + soma(input / 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        do {
            int num = Integer.parseInt(input);
            System.out.println(soma(num));
            input = scanner.nextLine();
        }
        while (!input.equals("FIM"));
        scanner.close();
    }

}
