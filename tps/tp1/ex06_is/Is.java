/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q06 - Is - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

public class Is {

    public static boolean isVogal(String input) {
        if(input.length() == 0) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            char character = Character.toLowerCase(input.charAt(i));
            if (!(character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u')) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isConsoante(String input) {
        if(input.length() == 0) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            char character = Character.toLowerCase(input.charAt(i));

            if (!Character.isLetter(character)) {
                return false;
            }

            if (character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u') {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteiro(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isReal(String input) {
        boolean dots = false;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                if ((input.charAt(i) == '.' || input.charAt(i) == ',') && !dots) {
                    dots = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        String input = MyIO.readLine();
        do {
            String res1 = isVogal(input) ? "SIM" : "NAO";
            String res2 = isConsoante(input) ? "SIM" : "NAO";
            String res3 = isInteiro(input) ? "SIM" : "NAO";
            String res4 = isReal(input) ? "SIM" : "NAO";

            MyIO.println(res1 + " " + res2 + " " + res3 + " " + res4);
            input = MyIO.readLine();
        }
        while (!input.equals("FIM"));
    }
}
