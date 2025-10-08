/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q04 - Alteração Aleatória - v1.0 - 28 / 08 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

 
import java.util.Random;
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

    // Faz a substituição de todos os caracteres de uma string por outro
    public static String replaceChar(String input, char a, char b) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == a) {
                result = result + b;
            } else {
                result = result + c;
            }
        }
        return result;
    }

    // Faz alterações na string passada de forma aleatória
    public static String randomize(String input, Random gerador) {
        char char1 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        char char2 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));

        return replaceChar(input, char1, char2);
    }

    public static void main(String[] args) {
        Random gerador = new Random();
        gerador.setSeed(4);

        String input = MyIO.readLine();
        while (!equals(input, "FIM")) {
            MyIO.println(randomize(input, gerador));
            input = MyIO.readLine();
        }
    }
}
