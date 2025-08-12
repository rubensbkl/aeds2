import java.util.Scanner;

public class Palindromo {

    public static boolean isPalindromo(String input ) {
        int length = input.length();
        for (int i = 0; i < length / 2; i++) {
            if (input.charAt(i) != input.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void main (String args[]) {
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (!(input = scanner.nextLine()).equals("FIM")) {
            System.out.println(isPalindromo(input) ? "SIM" : "NAO");
        }
        scanner.close();
    }

}

