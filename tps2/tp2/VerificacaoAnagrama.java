/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP02Q05 - Verificação de Anagrama - v1.0 - 04 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class VerificacaoAnagrama {

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

    // Converte letras maiúsculas para minúsculas
    public static char toLowerCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32);
        }
        return c;
    }

    // Normaliza string (remove espaços e converte para minúsculas)
    public static String normalize(String word) {
        String result = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c != ' ') {
                result += toLowerCase(c);
            }
        }
        return result;
    }

    // Retorna true se word1 é anagrama de word2
    public static boolean isAnagrama(String word1, String word2) {
        word1 = normalize(word1);
        word2 = normalize(word2);

        if (word1.length() != word2.length()) return false;

        int[] count = new int[256];

        for (int i = 0; i < word1.length(); i++) {
            count[word1.charAt(i)]++;
        }

        for (int i = 0; i < word2.length(); i++) {
            count[word2.charAt(i)]--;
        }

        for (int i = 0; i < 256; i++) {
            if (count[i] != 0) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        String input = scanner.nextLine();
        while (!(equals(input, "FIM"))) {
            int sepIndex = -1;
            for (int i = 0; i < input.length() - 2; i++) {
                if (input.charAt(i) == ' ' && input.charAt(i+1) == '-' && input.charAt(i+2) == ' ') {
                    sepIndex = i;
                    break;
                }
            }

            if (sepIndex != -1) {
                String word1 = "";
                String word2 = "";

                for (int i = 0; i < sepIndex; i++) word1 += input.charAt(i);
                for (int i = sepIndex + 3; i < input.length(); i++) word2 += input.charAt(i);

                MyIO.println(isAnagrama(word1, word2) ? "SIM" : "NÃO");
            }

            input = scanner.nextLine();
        }

        scanner.close();
    }
}
