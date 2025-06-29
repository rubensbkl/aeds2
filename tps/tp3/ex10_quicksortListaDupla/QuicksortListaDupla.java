/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP03Q10 - Quicksort com Lista Duplamente Encadeada - v1.0 - 08 / 06 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;

// Classe Shows
class Shows {
    private String id;
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

    public Shows() {}

    public Shows(String[] dados) {
        this.id = dados[0];
        this.type = dados[1];
        this.title = dados[2];
        this.director = dados[3].isEmpty() ? "NaN" : dados[3];
        this.cast = dados[4].isEmpty() ? new String[0] : dados[4].split(", ");
        this.country = dados[5].isEmpty() ? "NaN" : dados[5];

        if (!dados[6].isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                this.date_added = sdf.parse(dados[6]);
            } catch (ParseException e) {
                this.date_added = null;
            }
        }

        this.release_year = dados[7].isEmpty() ? 0 : Integer.parseInt(dados[7]);
        this.rating = dados[8];
        this.duration = dados[9];
        this.listed_in = dados[10].isEmpty() ? new String[0] : dados[10].split(", ");
    }

    public String getShow_id() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getDateAdded() {
        return date_added;
    }

    @Override
    public String toString() {
        Arrays.sort(cast);
        Arrays.sort(listed_in);

        StringBuilder output = new StringBuilder();
        output.append("=> ").append(id).append(" ## ")
              .append(title).append(" ## ")
              .append(type).append(" ## ")
              .append(director).append(" ## ")
              .append(cast.length == 0 ? "[NaN]" : Arrays.toString(cast)).append(" ## ")
              .append(country).append(" ## ");

        if (date_added == null) {
            output.append("NaN ## ");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            output.append(sdf.format(date_added)).append(" ## ");
        }

        output.append(release_year).append(" ## ")
              .append(rating).append(" ## ")
              .append(duration).append(" ## ")
              .append(listed_in.length == 0 ? "[NaN]" : Arrays.toString(listed_in)).append(" ##");

        return output.toString();
    }
}

// Celula duplamente encadeada
class Celula {
    public Shows elemento;
    public Celula prox;
    public Celula ant;

    public Celula() {
        this(null);
    }

    public Celula(Shows elemento) {
        this.elemento = elemento;
        this.prox = null;
        this.ant = null;
    }
}

// Lista Duplamente Encadeada  
class ListaDuplamenteEncadeada {

    private Celula primeiro;
    private Celula ultimo;

    public Celula getPrimeiro() {
        return primeiro;
    }

    public ListaDuplamenteEncadeada() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(Shows x) {
        Celula nova = new Celula(x);
        ultimo.prox = nova;
        nova.ant = ultimo;
        ultimo = nova;
    }

    public Shows remover() {
        if (primeiro == ultimo) {
            throw new RuntimeException("Erro ao remover! Lista vazia.");
        }
        Shows resp = ultimo.elemento;
        ultimo = ultimo.ant;
        ultimo.prox = null;
        return resp;
    }

    public void mostrar() {
        Celula i = primeiro.prox;
        int index = 0;
        while (i != null) {
            System.out.println("" + i.elemento);
            i = i.prox;
            index++;
        }
    }

    public void mostrarReverse() {
        Celula i = ultimo;
        int index = tamanho() - 1;
        while (i != primeiro) {
            System.out.println("[" + index + "] " + i.elemento);
            i = i.ant;
            index--;
        }
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            tamanho++;
        }
        return tamanho;
    }

    public Celula getTopo() {
        return ultimo;
    }
}

// Leitor de CSV
class ReadCSV {

	public static List<Shows> readCSV(String pathToFile) {
        List<Shows> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = parseLinhaCSV(linha);
                Shows s = new Shows(campos);
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

// Busca por Shows
class ShowSearch {

    public static int comparacoes = 0;

    public static Shows searchShowById(List<Shows> lista, String id) {

        for (Shows s : lista) {
            if (s.getShow_id().equals(id)) {
                return s;
            }
        }
        return null;
    }

	public static Shows searchShowByTitleSequential(List<Shows> lista, String title) {
		for (Shows s : lista) {
			comparacoes++;
			if (s != null && s.getTitle() != null && s.getTitle().trim().equalsIgnoreCase(title.trim())) {
				return s;
			}
		}
		return null;
	}
}
// End class Show Search

// Inicio ShowsOrdenacao
class ShowsOrdenacao {

    public static void quickSort(ListaDuplamenteEncadeada lista) {
        if (lista.getTopo() != null && lista.getTopo() != lista.getPrimeiro()) {
            quickSortRecursive(lista, lista.getPrimeiro().prox, lista.getTopo());
        }
    }

    private static void quickSortRecursive(ListaDuplamenteEncadeada lista, Celula low, Celula high) {
        if (high != null && low != high && low != high.prox) {
            Celula pi = partition(lista, low, high);
            quickSortRecursive(lista, low, pi.ant);
            quickSortRecursive(lista, pi.prox, high);
        }
    }

    private static Celula partition(ListaDuplamenteEncadeada lista, Celula low, Celula high) {
        Shows pivot = high.elemento;
        Celula i = low.ant;

        for (Celula j = low; j != high; j = j.prox) {
            Log.countComp(1);

            int comparacao = compararShows(j.elemento, pivot);

            if (comparacao < 0) {
                i = (i == null) ? low : i.prox;
                swap(i, j);
                Log.countMove(3);
            }
        }

        i = (i == null) ? low : i.prox;
        swap(i, high);
        Log.countMove(3);
        return i;
    }

    private static void swap(Celula a, Celula b) {
        Shows temp = a.elemento;
        a.elemento = b.elemento;
        b.elemento = temp;
    }

    private static int compararShows(Shows a, Shows b) {
        
        // Tratamento de nulls
        if (a.getTitle() == null) a = new Shows(new String[] {"", "", "", "", "", "", "", "0", "", "", ""});
        if (b.getTitle() == null) b = new Shows(new String[] {"", "", "", "", "", "", "", "0", "", "", ""});

        Date dataA = a.getDateAdded();
        Date dataB = b.getDateAdded();

        if (dataA == null && dataB == null) {
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        } else if (dataA == null) {
            return 1; // Nulls vão para o final
        } else if (dataB == null) {
            return -1;
        }

        int cmp = dataA.compareTo(dataB);
        if (cmp != 0) {
            return cmp;
        } else {
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        }
    }
}

class Log {
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

// Classe principal
public class QuicksortListaDupla {

    public static void main(String[] args) {

        List<Shows> shows = ReadCSV.readCSV("/tmp/disneyplus.csv");
        ListaDuplamenteEncadeada listaShows = new ListaDuplamenteEncadeada();

        Scanner sc = new Scanner(System.in);
        String stringEntrada;

        while (!(stringEntrada = sc.nextLine()).equals("FIM")) {
            Shows foundShow = ShowSearch.searchShowById(shows, stringEntrada);
            if (foundShow != null) {
                listaShows.inserir(foundShow);
            } else {
                System.out.println("Show ID not found: " + stringEntrada);
            }
        }


        Log.startTimer();

        ShowsOrdenacao.quickSort(listaShows);

        Log.endTimer();

        listaShows.mostrar();

        Log.createLog("855796", "quicksort2");

        sc.close();
    }
}
