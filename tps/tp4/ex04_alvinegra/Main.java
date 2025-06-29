/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q04 - Arvore Alvinegra em Java - v1.0 - 26 / 06 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;

enum Cor { VERMELHO, PRETO; }

class Show {
    private String show_id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    public Show(String[] campos) {
        this.show_id = campos[0];
        this.type = campos[1];
        this.title = campos[2];
        this.director = campos[3].isEmpty() ? "NaN" : campos[3];
        this.cast = campos[4].isEmpty() ? new String[0] : campos[4].split(", ");
        this.country = campos[5].isEmpty() ? "NaN" : campos[5];

        try {
            this.date_added = campos[6].isEmpty() ? null :
                new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(campos[6]);
        } catch (ParseException e) {
            this.date_added = null;
        }

        this.release_year = campos[7].isEmpty() ? 0 : Integer.parseInt(campos[7]);
        this.rating = campos[8];
        this.duration = campos[9];
        this.listed_in = campos[10].isEmpty() ? new String[0] : campos[10].split(", ");
    }

    public String getShow_id() { return show_id; }
    public String getTitle() { return title; }
    public Date getDateAdded() { return date_added; }

    @Override
    public String toString() {
        Arrays.sort(cast);
        Arrays.sort(listed_in);
        StringBuilder sb = new StringBuilder();
        sb.append("=> ").append(show_id).append(" ## ")
          .append(title).append(" ## ")
          .append(type).append(" ## ")
          .append(director).append(" ## ")
          .append(cast.length == 0 ? "[NaN]" : Arrays.toString(cast)).append(" ## ")
          .append(country).append(" ## ");
        sb.append(date_added == null ? "NaN ## " : new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(date_added) + " ## ")
          .append(release_year).append(" ## ")
          .append(rating).append(" ## ")
          .append(duration).append(" ## ")
          .append(listed_in.length == 0 ? "[NaN]" : Arrays.toString(listed_in)).append(" ##");
        return sb.toString();
    }
}

class No {
    Show show;
    No esq, dir, pai;
    Cor cor;

    public No(Show show) {
        this.show = show;
        this.cor = Cor.VERMELHO;
        this.esq = null;
        this.dir = null;
        this.pai = null;
    }
}

class ArvoreRedBlack {
    private No raiz;
    private final No nil;

    public ArvoreRedBlack() {
        nil = new No(null);
        nil.cor = Cor.PRETO;
        raiz = nil;
    }

    public void inserir(Show show) {
        No novo = new No(show);
        novo.esq = nil;
        novo.dir = nil;
        novo.pai = null;

        No y = null;
        No x = raiz;

        while (x != nil) {
            y = x;
            int cmp = novo.show.getTitle().compareTo(x.show.getTitle());
            if (cmp < 0) x = x.esq;
            else if (cmp > 0) x = x.dir;
            else return;
        }

        novo.pai = y;
        if (y == null) {
            raiz = novo;
        } else if (novo.show.getTitle().compareTo(y.show.getTitle()) < 0) {
            y.esq = novo;
        } else {
            y.dir = novo;
        }

        novo.esq = nil;
        novo.dir = nil;
        novo.cor = Cor.VERMELHO;

        inserirFixup(novo);
    }

    private void inserirFixup(No z) {
        while (z.pai != null && z.pai.cor == Cor.VERMELHO) {
            if (z.pai == z.pai.pai.esq) {
                No y = z.pai.pai.dir;
                if (y != null && y.cor == Cor.VERMELHO) {
                    z.pai.cor = Cor.PRETO;
                    y.cor = Cor.PRETO;
                    z.pai.pai.cor = Cor.VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.dir) {
                        z = z.pai;
                        rotacionarEsquerda(z);
                    }
                    z.pai.cor = Cor.PRETO;
                    z.pai.pai.cor = Cor.VERMELHO;
                    rotacionarDireita(z.pai.pai);
                }
            } else {
                No y = z.pai.pai.esq;
                if (y != null && y.cor == Cor.VERMELHO) {
                    z.pai.cor = Cor.PRETO;
                    y.cor = Cor.PRETO;
                    z.pai.pai.cor = Cor.VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esq) {
                        z = z.pai;
                        rotacionarDireita(z);
                    }
                    z.pai.cor = Cor.PRETO;
                    z.pai.pai.cor = Cor.VERMELHO;
                    rotacionarEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = Cor.PRETO;
    }

    private void rotacionarEsquerda(No x) {
        No y = x.dir;
        x.dir = y.esq;
        if (y.esq != nil) {
            y.esq.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esq) {
            x.pai.esq = y;
        } else {
            x.pai.dir = y;
        }
        y.esq = x;
        x.pai = y;
    }

    private void rotacionarDireita(No x) {
        No y = x.esq;
        x.esq = y.dir;
        if (y.dir != nil) {
            y.dir.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.dir) {
            x.pai.dir = y;
        } else {
            x.pai.esq = y;
        }
        y.dir = x;
        x.pai = y;
    }

    public void pesquisar(String titulo) {
        StringBuilder caminho = new StringBuilder("raiz ");
        boolean encontrado = pesquisar(raiz, titulo, caminho);
        System.out.println("=>" + caminho + (encontrado ? " SIM" : " NAO"));
    }

    private boolean pesquisar(No no, String titulo, StringBuilder caminho) {
        if (no == nil || no == null) return false;
        Logs.countComp(1);
        int cmp = titulo.compareTo(no.show.getTitle());
        if (cmp == 0) return true;
        caminho.append(cmp < 0 ? " esq" : " dir");
        return pesquisar(cmp < 0 ? no.esq : no.dir, titulo, caminho);
    }
}

class ReadCSV {
    public static List<Show> readCSV(String path) {
        List<Show> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = parseLinhaCSV(linha);
                if (campos.length >= 11 && !campos[7].equals("release_year")) {
                    lista.add(new Show(campos));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler CSV: " + e.getMessage());
        }
        return lista;
    }

    private static String[] parseLinhaCSV(String linha) {
        List<String> campos = new ArrayList<>();
        boolean aspas = false;
        StringBuilder atual = new StringBuilder();
        for (char c : linha.toCharArray()) {
            if (c == '"') aspas = !aspas;
            else if (c == ',' && !aspas) {
                campos.add(atual.toString());
                atual.setLength(0);
            } else atual.append(c);
        }
        campos.add(atual.toString());
        return campos.toArray(new String[0]);
    }
}

class ShowSearch {
    public static Show searchById(List<Show> lista, String id) {
        for (Show s : lista) {
            if (s.getShow_id().equals(id)) return s;
        }
        return null;
    }
}

class Logs {
    private static long startTime;
    private static long endTime;
    private static int totalComp = 0;
    private static int totalMove = 0;

    public static void countComp(int x) { totalComp += x; }
    public static void countMove(int x) { totalMove += x; }
    public static void startTimer() { startTime = System.currentTimeMillis(); }
    public static void endTimer() { endTime = System.currentTimeMillis(); }
    public static long getTime() { return endTime - startTime; }

    public static void createLog(String matricula, String nome) {
        try (FileWriter fw = new FileWriter(matricula + "_" + nome + ".txt")) {
            fw.write(matricula + "\t" + getTime() + "\t" + totalComp + "\t" + totalMove + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao criar log: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<Show> shows = ReadCSV.readCSV("/tmp/disneyplus.csv");
        ArvoreRedBlack arvore = new ArvoreRedBlack();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;
            Show s = ShowSearch.searchById(shows, entrada);
            if (s != null) arvore.inserir(s);
        }

        Logs.startTimer();
        while (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;
            arvore.pesquisar(entrada);
        }
        Logs.endTimer();

        Logs.createLog("855796", "avinegra");
        sc.close();
    }
}
