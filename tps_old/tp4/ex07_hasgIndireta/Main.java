/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q07 - Tabela Hash Indireta com Lista Simples - v1.0 - 26 / 06 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;

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

    public Show() {}

    public Show(String[] campos) {
        this.show_id = campos[0];
        this.type = campos[1];
        this.title = campos[2];
        this.director = campos[3].isEmpty() ? "NaN" : campos[3];
        this.cast = campos[4].isEmpty() ? new String[0] : campos[4].split(", ");
        this.country = campos[5].isEmpty() ? "NaN" : campos[5];

        if (campos[6].isEmpty()) {
            this.date_added = null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                this.date_added = sdf.parse(campos[6]);
            } catch (ParseException e) {
                this.date_added = null;
            }
        }

        this.release_year = campos[7].isEmpty() ? 0 : Integer.parseInt(campos[7]);
        this.rating = campos[8];
        this.duration = campos[9];
        this.listed_in = campos[10].isEmpty() ? new String[0] : campos[10].split(", ");
    }

    public String getShow_id() {
        return show_id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateAdded() {
        return date_added;
    }

    @Override
    public String toString() {
        Arrays.sort(cast);
        Arrays.sort(listed_in);

        StringBuilder sb = new StringBuilder();
        sb.append("=> ").append(show_id).append(" ## ");
        sb.append(title).append(" ## ");
        sb.append(type).append(" ## ");
        sb.append(director).append(" ## ");
        sb.append(cast.length == 0 ? "[NaN]" : Arrays.toString(cast)).append(" ## ");
        sb.append(country).append(" ## ");

        if (date_added == null) {
            sb.append("NaN ## ");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            sb.append(sdf.format(date_added)).append(" ## ");
        }

        sb.append(release_year).append(" ## ");
        sb.append(rating).append(" ## ");
        sb.append(duration).append(" ## ");
        sb.append(listed_in.length == 0 ? "[NaN]" : Arrays.toString(listed_in)).append(" ##");

        return sb.toString();
    }
}

class ReadCSV {
    public static List<Show> readCSV(String pathToFile) {
        List<Show> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = parseLinhaCSV(linha);
                Show s = new Show(campos);
                lista.add(s);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler CSV: " + e.getMessage());
        }

        return lista;
    }

    public static String[] parseLinhaCSV(String linha) {
        List<String> campos = new ArrayList<>();
        boolean aspas = false;
        StringBuilder atual = new StringBuilder();

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"') {
                aspas = !aspas;
            } else if (c == ',' && !aspas) {
                campos.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }
        campos.add(atual.toString()); 

        return campos.toArray(new String[0]);
    }
}

class ShowSearch {
    public static int comparacoes = 0;

    public static Show searchShowById(List<Show> lista, String id) {
        for (Show s : lista) {
            if (s.getShow_id().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static Show searchShowByTitleSequential(List<Show> lista, String title) {
        for (Show s : lista) {
            comparacoes++;
            if (s != null && s.getTitle() != null && s.getTitle().trim().equalsIgnoreCase(title.trim())) {
                return s;
            }
        }
        return null;
    }
}


class Logs {
    private static long startTime = 0;
    private static long endTime = 0;
    private static int totalComp = 0;
    private static int totalMove = 0;

    static void countComp(int x){
        totalComp += x;
    }

    static void countMove(int x){
        totalMove += x;
    }

    public static void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public static void endTimer() {
        endTime = System.currentTimeMillis();
    }

    static long getTime() {
        return endTime - startTime;
    }

    public static void createLog(final String matricula, final String metodo) {
        try {
            FileWriter logArq = new FileWriter(matricula + "_" + metodo +".txt");
            logArq.write(matricula + "\t" + getTime() + "\t" + totalComp + "\t" + totalMove + "\n");
            logArq.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao criar txt");
        }
    }
}

class Celula {
    public Show elemento;
    public Celula prox;
    
    public Celula() {
        this(null);
    }
    
    public Celula(Show elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class ListaSimplesEncadeada {
    private Celula primeiro;
    private Celula ultimo;
    
    public ListaSimplesEncadeada() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(Show x) {
        Celula nova = new Celula(x);
        ultimo.prox = nova;
        ultimo = nova;
    }

    public boolean pesquisar(String titulo) {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            Logs.countComp(1);
            if (i.elemento != null && i.elemento.getTitle() != null && 
                i.elemento.getTitle().equals(titulo)) {
                return true;
            }
        }
        return false;
    }
    
    public void mostrar() {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.println(i.elemento);
        }
    }
    
    public boolean vazia() {
        return primeiro == ultimo;
    }
}

class TabelaHashIndireta {
    private ListaSimplesEncadeada[] tabela;
    private int tamanho;

    public TabelaHashIndireta() {
        this(21);
    }
    
    public TabelaHashIndireta(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new ListaSimplesEncadeada[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new ListaSimplesEncadeada();
        }
    }

    public int hash(String titulo) {
        int hash = 0;
        for (char c : titulo.toCharArray()) {
            hash += (int) c;
        }
        return hash % this.tamanho;
    }

    public void inserir(Show show) {
        if (show != null && show.getTitle() != null) {
            int pos = hash(show.getTitle());
            tabela[pos].inserir(show);
        }
    }

    public void pesquisar(String titulo) {
        int pos = hash(titulo);
        Logs.countComp(1);
        
        if (tabela[pos].pesquisar(titulo)) {
            System.out.println(" (Posicao: " + pos + ") SIM");
        } else {
            System.out.println(" (Posicao: " + pos + ") NAO");
        }
    }
    
    public void mostrarTabela() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print("Posição " + i + ": ");
            if (tabela[i].vazia()) {
                System.out.println("Vazia");
            } else {
                System.out.println();
                tabela[i].mostrar();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<Show> shows = ReadCSV.readCSV("/tmp/disneyplus.csv");
        TabelaHashIndireta hashIndireta = new TabelaHashIndireta();

        Scanner sc = new Scanner(System.in);
        String stringEntrada;

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            Show foundShow = ShowSearch.searchShowById(shows, stringEntrada);
            if (foundShow != null) {
                hashIndireta.inserir(foundShow);
            } else {
                System.out.println("Show ID not found: " + stringEntrada);
            }
        }

        Logs.startTimer();

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            String tituloShow = stringEntrada;
            hashIndireta.pesquisar(tituloShow);
        }

        Logs.endTimer();

        Logs.createLog("855796", "hashIndireta");

        sc.close();
    }
}