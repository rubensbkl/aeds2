import java.util.Scanner;

public class SequenciaEspelho {

    public static void sequenciaEspelho(int a, int b) {
        StringBuilder result = new StringBuilder();
        for (int i = a; i <= b; i++) {
            System.out.print(i);
            result.append(i);
        }
        for (int i = result.length() - 1; i >= 0; i--) {
            System.out.print(result.charAt(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int a = sc.nextInt();
            if (!sc.hasNextInt())
                break;
            int b = sc.nextInt();
            sequenciaEspelho(a, b);
        }

        sc.close();

    }

}
