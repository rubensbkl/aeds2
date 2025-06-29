/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q05 - Tabela Hash Direta com Reserva - v1.0 - 26 / 06 / 2025
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

class TabelaHashReserva {
    private String[] tabela;
    private int tamanhoNormal;
    private int tamanhoReserva;
    int total;
    int reserva = 0;

    public TabelaHashReserva(){
        this(21, 9);
    }
    
    public TabelaHashReserva(int tamanhoNormal, int tamanhoReserva) {
        this.tamanhoNormal = tamanhoNormal;
        this.tamanhoReserva = tamanhoReserva;
        this.total = tamanhoNormal + tamanhoReserva;
        this.tabela = new String[total];
        for (int i = 0; i < total; i++) {
            tabela[i] = null;
        }
        reserva = 0;
    }

    public int hash(String titulo) {
        int hash = 0;
        for (char c : titulo.toCharArray()) {
            hash += c;
        }
        return hash % this.tamanhoNormal;
    }

    public boolean inserir(String tituloShow) {
        boolean resp = false;
        if (tituloShow != null) {
            int pos = hash(tituloShow);
            if (this.tabela[pos] == null) {
                this.tabela[pos] = tituloShow;
                resp = true;
            } else if (this.reserva < this.tamanhoReserva) {
                tabela[this.tamanhoNormal + this.reserva] = tituloShow;
                this.reserva++;
                resp = true;
            }
        }
        return resp;
    }

    public void pesquisar(String titulo) {
        int pos = hash(titulo);

        Logs.countComp(1);
        if (tabela[pos] != null && tabela[pos].equals(titulo)) {
            System.out.println(" (Posicao: " + pos + ") SIM");
            return;
        }

        for (int i = 0; i < this.reserva; i++) {
            Logs.countComp(1);
            if (tabela[this.tamanhoNormal + i] != null && tabela[this.tamanhoNormal + i].equals(titulo)) {
                System.out.println(" (Posicao: " + (this.tamanhoNormal + i) + ") SIM");
                return;
            }
        }

        System.out.println(" (Posicao: " + pos + ") NAO");
    }
}

public class Main {
    public static void main(String[] args) {
        List<Show> shows = ReadCSV.readCSV("/tmp/disneyplus.csv");
        TabelaHashReserva hashReserva = new TabelaHashReserva();

        Scanner sc = new Scanner(System.in);
        String stringEntrada;

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            Show foundShow = ShowSearch.searchShowById(shows, stringEntrada);
            if (foundShow != null) {
                hashReserva.inserir(foundShow.getTitle());
            } else {
                System.out.println("Show ID not found: " + stringEntrada);
            }
        }

        Logs.startTimer();

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            String tituloShow = stringEntrada;
            hashReserva.pesquisar(tituloShow);
        }

        Logs.endTimer();

        Logs.createLog("855796", "hashReserva");

        sc.close();
    }
}