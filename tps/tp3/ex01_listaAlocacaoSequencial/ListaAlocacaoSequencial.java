/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP03Q01 - Lista com ALocacao Sequencial - v1.0 - 08 / 06 / 2025
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

// Lista sequencial de Shows
class ListaShows {
    private int tamanho;
    private Shows[] array;

    public ListaShows(int capacidade) {
        tamanho = 0;
        array = new Shows[capacidade];
    }

    public int getN() {
        return tamanho;
    }

    public Shows getShow(int i) {
        return array[i];
    }

    public void inserirInicio(Shows s) {
        if (tamanho >= array.length) throw new ArrayStoreException("Lista cheia");

        for (int i = tamanho; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = s;
        tamanho++;
    }

    public void inserirFim(Shows s) {
        if (tamanho >= array.length) throw new ArrayStoreException("Lista cheia");
        array[tamanho++] = s;
    }

    public void inserir(Shows s, int pos) {
        if (tamanho >= array.length || pos < 0 || pos > tamanho)
            throw new ArrayStoreException("Posição inválida ou lista cheia");

        for (int i = tamanho; i > pos; i--) {
            array[i] = array[i - 1];
        }
        array[pos] = s;
        tamanho++;
    }

    public Shows removerInicio() {
        if (tamanho == 0) throw new ArrayStoreException("Lista vazia");
        Shows removido = array[0];

        for (int i = 0; i < tamanho - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--tamanho] = null;
        return removido;
    }

    public Shows removerFim() {
        if (tamanho == 0) throw new ArrayStoreException("Lista vazia");
        Shows removido = array[--tamanho];
        array[tamanho] = null;
        return removido;
    }

    public Shows remover(int pos) {
        if (tamanho == 0 || pos < 0 || pos >= tamanho)
            throw new ArrayStoreException("Posição inválida ou lista vazia");

        Shows removido = array[pos];
        for (int i = pos; i < tamanho - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--tamanho] = null;
        return removido;
    }
}

// Leitura de CSV
class ReadCSV {

    public static List<Shows> readCSV(String caminho) {
        List<Shows> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // ignora cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = parseLinha(linha);
                lista.add(new Shows(campos));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return lista;
    }

    public static String[] parseLinha(String linha) {
        List<String> campos = new ArrayList<>();
        boolean entreAspas = false;
        StringBuilder atual = new StringBuilder();

        for (char c : linha.toCharArray()) {
            if (c == '"') {
                entreAspas = !entreAspas;
            } else if (c == ',' && !entreAspas) {
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

// Busca de Shows
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

    public static Shows searchShowByTitleSequential(List<Shows> lista, String titulo) {
        for (Shows s : lista) {
            comparacoes++;
            if (s != null && s.getTitle() != null && s.getTitle().trim().equalsIgnoreCase(titulo.trim())) {
                return s;
            }
        }
        return null;
    }
}

// Classe principal
public class ListaAlocacaoSequencial {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Shows> base = ReadCSV.readCSV("/tmp/disneyplus.csv");
        ListaShows lista = new ListaShows(500);

        while (true) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;

            Shows s = ShowSearch.searchShowById(base, entrada);
            if (s != null) {
                lista.inserirFim(s);
            } else {
                System.err.println("Show não encontrado: " + entrada);
            }
        }

        int qtdOperacoes = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < qtdOperacoes; i++) {
            String comando = sc.nextLine();
            processarComando(comando, lista, base);
        }

        for (int i = 0; i < lista.getN(); i++) {
            System.out.println(lista.getShow(i));
        }

        sc.close();
    }

    private static void processarComando(String linha, ListaShows lista, List<Shows> base) {
        String[] partes = linha.split(" ");

        switch (partes[0]) {
            case "II":
                lista.inserirInicio(ShowSearch.searchShowById(base, partes[1]));
                break;
            case "IF":
                lista.inserirFim(ShowSearch.searchShowById(base, partes[1]));
                break;
            case "I*":
                int posI = Integer.parseInt(partes[1]);
                lista.inserir(ShowSearch.searchShowById(base, partes[2]), posI);
                break;
            case "RI":
                System.out.println("(R) " + lista.removerInicio().getTitle());
                break;
            case "RF":
                System.out.println("(R) " + lista.removerFim().getTitle());
                break;
            case "R*":
                int posR = Integer.parseInt(partes[1]);
                System.out.println("(R) " + lista.remover(posR).getTitle());
                break;
            default:
                System.err.println("Comando desconhecido: " + partes[0]);
        }
    }
}
