/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP02Q03 - Inversão de Strings Recursivo - v1.0 - 15 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class Main {

  // Verifica se duas strings são iguais
  public static boolean equals(String a, String b) {
    int a_len = a.length();
    int b_len = b.length();
    if (a_len != b_len)
      return false;

    for (int i = 0; i < a_len; i++) {
      if (a.charAt(i) != b.charAt(i))
        return false;
    }
    return true;
  }

  // Inverte a string input usando recursão e salva na output
  public static String inverterString(String input, int size, String output) {
    if (size < 0) {
      return output;
    }
    output += input.charAt(size);
    return inverterString(input, size - 1, output);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    while (!equals(input, "FIM")) {
      System.out.println(inverterString( input, input.length() - 1, ""));
      input = sc.nextLine();
    }
    sc.close();
  }
}
