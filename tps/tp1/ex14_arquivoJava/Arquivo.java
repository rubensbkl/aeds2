/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q14 - Arquivo - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Arquivo {
    public static void writeFile(int n, String filename, Scanner sc) {
        try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            for (int i = 0; i < n; i++) {
                file.writeDouble(sc.nextDouble());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readReverse(int n, String filename) {
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            for (int i = n - 1; i >= 0; i--) {
                file.seek(i * 8);
                double number = file.readDouble();
                if (number == (int) number) {
                    System.out.println((int) number);
                } else {
                    System.out.println(number);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String filename = "arquivo.txt";
        writeFile(n, filename, sc);
        sc.close();
        readReverse(n, filename);
    }
}
