/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q09 - Verificação de Anagrama - v1.0 - 09 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Scanner;
    
public class VerificacaoAnagrama {
    
    public static String normalize(String word) {
        return Normalizer.normalize(word, Normalizer.Form.NFD)
                         .replaceAll("\\p{M}", "")
                         .toLowerCase();
    }
    
    public static boolean isAnagrama(String word1, String word2) {
        word1 = normalize(word1).trim();
        word2 = normalize(word2).trim();
            
        if (word1.length() != word2.length()) {
            return false;
        }
        
        char[] word1Array = word1.toCharArray();
        char[] word2Array = word2.toCharArray();
        Arrays.sort(word1Array);
        Arrays.sort(word2Array);
            
        return Arrays.equals(word1Array, word2Array);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        while (!input.equals("FIM")) {
            String[] words = input.split(" - ");
                
            if (words.length == 2) {
                MyIO.println(isAnagrama(words[0], words[1]) ? "SIM" : "NÃO");
            }
    
            input = scanner.nextLine();
        }
        scanner.close();
    }
}
    
 