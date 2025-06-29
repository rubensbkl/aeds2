/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP03Q09 - Pilha com Alocacao Flexivel - v1.0 - 08 / 06 / 2025
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

// Celula da pilha
class Celula {
    public Shows elemento;
    public Celula prox;

    public Celula() {
        this(null);
    }

    public Celula(Shows elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

// Pilha com alocação flexível
class PilhaFlexivel {
    private Celula topo;

    public PilhaFlexivel() {
        topo = null;
    }

    public void inserir(Shows x) {
        Celula nova = new Celula(x);
        nova.prox = topo;
        topo = nova;
    }

    public Shows remover() {
        if (topo == null) {
            throw new RuntimeException("Erro ao remover!");
        }
        Shows resp = topo.elemento;
        topo = topo.prox;
        return resp;
    }

    public void mostrar() {
        int pos = tamanho() - 1;
        for (Celula atual = topo; atual != null; atual = atual.prox, pos--) {
            System.out.println("[" + pos + "] " + atual.elemento);
        }
    }

    public int tamanho() {
        int contador = 0;
        for (Celula atual = topo; atual != null; atual = atual.prox) {
            contador++;
        }
        return contador;
    }

    public Celula getTopo() {
        return topo;
    }
}

// Leitor de CSV
class ReadCSV {

    public static List<Shows> readCSV(String caminho) {
        List<Shows> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // pula cabeçalho
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = parseLinhaCSV(linha);
                lista.add(new Shows(campos));
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return lista;
    }

    public static String[] parseLinhaCSV(String linha) {
        List<String> resultado = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean entreAspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '"') {
                entreAspas = !entreAspas;
            } else if (c == ',' && !entreAspas) {
                resultado.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }

        resultado.add(atual.toString());
        return resultado.toArray(new String[0]);
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

    public static Shows searchShowByTitleSequential(List<Shows> lista, String titulo) {
        for (Shows s : lista) {
            comparacoes++;
            if (s != null && s.getTitle() != null &&
                s.getTitle().trim().equalsIgnoreCase(titulo.trim())) {
                return s;
            }
        }
        return null;
    }
}

// Classe principal
public class PilhaAlocacaoFlexivel {

    public static void main(String[] args) {
        List<Shows> todosShows = ReadCSV.readCSV("/tmp/disneyplus.csv");
        PilhaFlexivel pilha = new PilhaFlexivel();

        Scanner sc = new Scanner(System.in);
        String entrada;

        while (!(entrada = sc.nextLine()).equals("FIM")) {
            Shows show = ShowSearch.searchShowById(todosShows, entrada);
            if (show != null) {
                pilha.inserir(show);
            } else {
                System.out.println("Show ID not found: " + entrada);
            }
        }

        int operacoes = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < operacoes; i++) {
            String linha = sc.nextLine();
            String[] partes = linha.split(" ");
            if (partes[0].equals("I")) {
                Shows show = ShowSearch.searchShowById(todosShows, partes[1]);
                if (show != null) {
                    pilha.inserir(show);
                }
            } else if (partes[0].equals("R")) {
                Shows removido = pilha.remover();
                System.out.println("(R) " + removido.getTitle());
            }
        }

        pilha.mostrar();
        sc.close();
    }
}
