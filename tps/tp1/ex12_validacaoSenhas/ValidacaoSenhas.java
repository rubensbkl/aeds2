/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q12 - Validação de Senhas - v1.0 - 09 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.Scanner;

public class ValidacaoSenhas {

    public static boolean passwordValidate(String senha) {
        if (senha.length() < 8) {
            return false;
        }

        boolean hasMaiuscula = false;
        boolean hasMinuscula = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        String specials = "!@#$%^&*()-_+=<>?/";

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);
            if (Character.isUpperCase(c)) {
                hasMaiuscula = true;
            } else if (Character.isLowerCase(c)) {
                hasMinuscula = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (specials.contains(String.valueOf(c))) {
                hasSpecial = true;
            }
        }

        return hasMaiuscula && hasMinuscula && hasNumber && hasSpecial;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password;
        while (!(password = scanner.nextLine().trim()).equals("FIM")) {
            MyIO.println(passwordValidate(password) ? "SIM" : "NÃO");
        }

        scanner.close();
    }
}
