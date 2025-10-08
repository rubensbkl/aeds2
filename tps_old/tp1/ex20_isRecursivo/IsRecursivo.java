/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q20 - Is (Recursivo) - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class IsRecursivo {

    public static boolean isVogal(String input, int index) {
        if (index == input.length()) {
            return true;
        }
        char character = Character.toLowerCase(input.charAt(index));
        if (!(character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u')) {
            return false;
        }
        return isVogal(input, index + 1);
    }
    
    public static boolean isConsoante(String input, int index) {
        if (index == input.length()) {
            return true;
        }
        char character = Character.toLowerCase(input.charAt(index));
        if (!Character.isLetter(character) || character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u') {
            return false;
        }
        return isConsoante(input, index + 1);
    }

    public static boolean isInteiro(String input, int index) {
        if (index == input.length()) {
            return true;
        }
        if (!Character.isDigit(input.charAt(index))) {
            return false;
        }
        return isInteiro(input, index + 1);
    }
    
    public static boolean isReal(String input, int index, boolean dots) {
        if (index == input.length()) {
            return true;
        }
        char character = input.charAt(index);
        if (!Character.isDigit(character)) {
            if ((character == '.' || character == ',') && !dots) {
                return isReal(input, index + 1, true);
            }
            return false;
        }
        return isReal(input, index + 1, dots);
    }
    
    public static void main(String[] args) {
        String input = MyIO.readLine();
        do {
            String r1 = isVogal(input, 0) ? "SIM" : "NAO";
            String r2 = isConsoante(input, 0) ? "SIM" : "NAO";
            String r3 = isInteiro(input, 0) ? "SIM" : "NAO";
            String r4 = isReal(input, 0, false) ? "SIM" : "NAO";

            MyIO.println(r1 + " " + r2 + " " + r3 + " " + r4);
            input = MyIO.readLine();
        }
        while (!input.equals("FIM"));
    }
}
