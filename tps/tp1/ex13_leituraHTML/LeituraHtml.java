/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q13 - Leitura de Página HTML - v1.0 - 14 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LeituraHtml {
    static int[] vogais;

    static final char[] vogalEspecial = {
        225, // á 
        233, // é 
        237, // í 
        243, // ó 
        250, // ú 
        224, // à 
        232, // è 
        236, // ì 
        242, // ò
        249, // ù
        227, // ã 
        245, // õ
        226, // â
        234, // ê
        238, // î
        244, // ô
        251  // û
    };

    public static void printVariaveis() {
        char[] vogaisNormais = {'a', 'e', 'i', 'o', 'u'};
        for (int i = 0; i < vogaisNormais.length; i++) {
            System.out.printf("%c(%s) ", vogaisNormais[i], vogais[i]);
        }
        for (int i = 0; i < vogalEspecial.length; i++) {
            System.out.printf("%c(%s) ", vogalEspecial[i], vogais[i + 5]);
        }
    }

    public static boolean isEspecialVogal(char character) {
        for (char c : vogalEspecial) {
            if (character == c) {
                return true;
            }
        }
        return false;
    }

    public static void countVogais(String html) {
        vogais = new int[23];
        for (int i = 0; i < html.length(); ++i) {
            char character = html.charAt(i);
            if (character == 'a')
                vogais[0]++;
            else if (character == 'e')
                vogais[1]++;
            else if (character == 'i')
                vogais[2]++;
            else if (character == 'o')
                vogais[3]++;
            else if (character == 'u')
                vogais[4]++;
            else {
                int index = new String(vogalEspecial).indexOf(character);
                if (index != -1) {
                    vogais[index + 5]++;
                }
            }
                
        }
    }

    public static int countConsoantes(String html) {
        int count = 0;
        for (int i = 0; i < html.length(); ++i) {
            char character = html.charAt(i);
            if ("bcdfghjklmnpqrstvwxyz".indexOf(character) >= 0) {
                count++;
            }
        }
        return count;
    }

    public static int countBR(String html) {
        int count = 0, index = 0;
        while ((index = html.indexOf("<br>", index)) != -1) {
            count++;
            index += 4;
        }
        return count;
    }

    public static int countTable(String html) {
        int count = 0;
        for (int i = 0; i < html.length() - 7; i = i + 1) {
            if (html.substring(i, i + 7).equals("<table>")) {
                count++;
            }
        }
        return (count);
    }


    public static void main(String[] args) {
        String name;
        String link;
        String html = "";
        Scanner sc = new Scanner(System.in);
        int qtd_consoantes = 0, qtd_br = 0, qtd_table = 0;

        do {
            name = sc.nextLine();
            if (!name.equals("FIM")) {
                link = sc.nextLine();
                html = getHtml(link);
                countVogais(html);
                qtd_consoantes = countConsoantes(html);
                qtd_br = countBR(html);
                qtd_table = countTable(html);

                vogais[0] -= qtd_table;
                vogais[1] -= qtd_table;
                qtd_consoantes -= (2 * qtd_br + 3 * qtd_table);

                printVariaveis();
                System.out.printf("consoante(%s) ", qtd_consoantes);
                System.out.printf("<br>(%s) ", qtd_br);
                System.out.printf("<table>(%s) ", qtd_table);
                System.out.printf("%s\n", name);
            }
        } while (!name.equals("FIM"));
    }

    public static String getHtml(String endereco){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream(); 
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            assert is != null;
            is.close();
        } catch (IOException ioe) {}

        return resp;
    }
}