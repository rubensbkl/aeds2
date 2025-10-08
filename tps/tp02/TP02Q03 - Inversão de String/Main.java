/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP02Q03 - Inversão de String - v1.0 - 04 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

 
import libs.MyIO;


public class Main {

    // Verifica se duas strings são iguais
    public static boolean equals(String a, String b) {
        int a_len = a.length();
        int b_len = b.length();
        if (a_len != b_len) return false;

        for (int i = 0; i < a_len; i++) {
            if (a.charAt(i) != b.charAt(i)) return false;
        }
        return true;
    }
    
    // Inverte a string input
    public static String inverterString(String input) {
        String inversa = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            inversa += input.charAt(i); 
        }
        return inversa;
    }

    public static void main(String[] args) {
        String input = MyIO.readLine();
        while (!equals(input, "FIM")) {
            MyIO.println(inverterString(input));
            input = MyIO.readLine();
        }
    }
}

