package tps.tp1.ex11_substringSemRepeticao;
/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q11 - Substring Mais Longa Sem Repetição - v1.0 - 09 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class SubstringSemRepeticao {
    
    public static int substringCount(String s) {
        int[] reps = new int[256];
        int maxLength = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            reps[s.charAt(right)]++;

            while (reps[s.charAt(right)] > 1) {
                reps[s.charAt(left)]--;
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equals("FIM")) {
                break;
            }
            System.out.println(substringCount(input));
        }

        scanner.close();
    }
}
