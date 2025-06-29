/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q02 - Arvore Binaria de Arvore Binarias em Java - v1.0 - 26 / 06 / 2025
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

    public int getReleaseYear() {
        return release_year;
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

class NoSecundario {
    String titulo;
    NoSecundario esq, dir;

    public NoSecundario(String titulo) {
        this.titulo = titulo;
        this.esq = null;
        this.dir = null;
    }
}

class NoPrincipal {
    int chave;
    NoPrincipal esq, dir;
    AB_Secundaria arvoreSecundaria;

    public NoPrincipal(int chave) {
        this.chave = chave;
        this.esq = null;
        this.dir = null;
        this.arvoreSecundaria = null;
    }
}

class AB_Secundaria {
    private NoSecundario raiz;

    public AB_Secundaria() {
        this.raiz = null;
    }

    public void inserir(String titulo) {
        raiz = inserir(titulo, raiz);
    }

    private NoSecundario inserir(String titulo, NoSecundario no) {
        if (no == null) {
            no = new NoSecundario(titulo);
        } else if (titulo.compareTo(no.titulo) < 0) {
            no.esq = inserir(titulo, no.esq);
        } else if (titulo.compareTo(no.titulo) > 0) {
            no.dir = inserir(titulo, no.dir);
        }
        return no;
    }

    public boolean pesquisar(String titulo) {
        return pesquisar(titulo, raiz);
    }

    private boolean pesquisar(String titulo, NoSecundario no) {
        if (no == null) {
            return false;
        }

        Logs.countComp(1);

        if (no.titulo.equals(titulo)) {
            return true;
        } else if (titulo.compareTo(no.titulo) < 0) {
            System.out.print("esq ");
            return pesquisar(titulo, no.esq);
        } else {
            System.out.print("dir ");
            return pesquisar(titulo, no.dir);
        }
    }

    public NoSecundario getRaiz() {
        return raiz;
    }
}

class AB_Principal {
    private NoPrincipal raiz;
    private List<Show> shows;

    public AB_Principal(List<Show> shows) {
        this.raiz = null;
        this.shows = shows;
    }

    public void inserirInicio(int chave) {
        raiz = inserirInicio(chave, raiz);
    }

    private NoPrincipal inserirInicio(int chave, NoPrincipal no) {
        if (no == null) {
            no = new NoPrincipal(chave);
        } else if (chave < no.chave) {
            no.esq = inserirInicio(chave, no.esq);
        } else if (chave > no.chave) {
            no.dir = inserirInicio(chave, no.dir);
        }
        return no;
    }

    public void inserir(int chave, String titulo) {
        NoPrincipal no = buscarNo(chave, raiz);
        if (no != null) {
            if (no.arvoreSecundaria == null) {
                no.arvoreSecundaria = new AB_Secundaria();
            }
            no.arvoreSecundaria.inserir(titulo);
        }
    }

    private NoPrincipal buscarNo(int chave, NoPrincipal no) {
        if (no == null) {
            return null;
        } else if (chave == no.chave) {
            return no;
        } else if (chave < no.chave) {
            return buscarNo(chave, no.esq);
        } else {
            return buscarNo(chave, no.dir);
        }
    }

    public boolean pesquisarTitulo(String titulo) {
        System.out.print("raiz ");
        boolean encontrado = buscarTitulo(titulo, raiz);
        System.out.println(encontrado ? "SIM" : "NAO");
        return encontrado;
    }
    
    private boolean buscarTitulo(String titulo, NoPrincipal no) {
        if (no == null) {
            return false;
        }
        
        boolean encontrado = false;

        if (no.esq != null) {
            System.out.print("esq ");
            encontrado = buscarTitulo(titulo, no.esq);
            if (encontrado) return true;
        }
        
        if (no.arvoreSecundaria != null) {
            System.out.print(" ESQ ");
            encontrado = no.arvoreSecundaria.pesquisar(titulo);
            if (encontrado) return true;
            System.out.print(" DIR ");
        }
        
        if (no.dir != null) {
            System.out.print("dir ");
            encontrado = buscarTitulo(titulo, no.dir);
            if (encontrado) return true;
        }
        
        return false;
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

    public static Show searchShowById(List<Show> lista, String id) {
        for (Show s : lista) {
            if (s.getShow_id().equals(id)) {
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

public class Main {

    public static void main(String[] args) {

        List<Show> shows = ReadCSV.readCSV("/tmp/disneyplus.csv");
        AB_Principal abPrincipal = new AB_Principal(shows);

        int[] inicial = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        for (int i : inicial) {
            abPrincipal.inserirInicio(i);
        }

        Scanner sc = new Scanner(System.in);
        String stringEntrada;

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            Show foundShow = ShowSearch.searchShowById(shows, stringEntrada);
            if (foundShow != null) {
                int chave = foundShow.getReleaseYear() % 15;
                abPrincipal.inserir(chave, foundShow.getTitle());
            } else {
                System.out.println("Show ID not found: " + stringEntrada);
            }
        }

        Logs.startTimer();

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            String tituloShow = stringEntrada;
            abPrincipal.pesquisarTitulo(tituloShow);
        }

        Logs.endTimer();

        Logs.createLog("855796", "arvoreArvore");

        sc.close();
    }
}