import java.io.*;
import java.text.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Show> base = LeitorCSV.read("/tmp/disneyplus.csv");
        List<Show> selecionados = new ArrayList<>();
        String entrada;

        // Leitura de IDs
        while (!(entrada = sc.nextLine()).equals("FIM")) {
            for (Show s : base) {
                if (s.getId().equals(entrada)) {
                    selecionados.add(s);
                    break;
                }
            }
        }

        long inicio = System.currentTimeMillis();
        Ordenacao.mergeSort(selecionados);
        long fim = System.currentTimeMillis();

        // Impressão ordenada
        for (Show s : selecionados) {
            System.out.println(s);
        }

        // Log
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("855796_mergesort.txt"))) {
            bw.write("855796\t" + (fim - inicio) + "ms\t" + Ordenacao.comparacoes + "\t" + Ordenacao.movimentacoes);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log.");
        }

        sc.close();
    }

    static class Show {
        private String id;
        private String type;
        private String title;
        private String director;
        private String[] cast;
        private String country;
        private Date dateAdded;
        private int releaseYear;
        private String rating;
        private String duration;
        private String[] genres;

        public Show(String[] campos) {
            try {
                this.id = campos[0];
                this.type = campos[1];
                this.title = campos[2];
                this.director = campos[3].isEmpty() ? "NaN" : campos[3];
                this.cast = campos[4].isEmpty() ? new String[0] : campos[4].split(", ");
                this.country = campos[5].isEmpty() ? "NaN" : campos[5];
                if (!campos[6].isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                    String rawDate = campos[6].replaceAll("(?<=\\d)(st|nd|rd|th)", "");
                    this.dateAdded = sdf.parse(rawDate);
                }
                this.releaseYear = campos[7].isEmpty() ? 0 : Integer.parseInt(campos[7]);
                this.rating = campos[8];
                this.duration = campos[9];
                this.genres = campos[10].isEmpty() ? new String[0] : campos[10].split(", ");
            } catch (Exception e) {
                System.err.println("Erro ao criar Show: " + e.getMessage());
            }
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public String getDirector() {
            return director;
        }

        public String getDuration() {
            return duration;
        }

        @Override
        public String toString() {
            Arrays.sort(cast);
            Arrays.sort(genres);

            StringBuilder sb = new StringBuilder();
            sb.append("=> ").append(id).append(" ## ");
            sb.append(title).append(" ## ");
            sb.append(type).append(" ## ");
            sb.append(director).append(" ## ");
            sb.append(cast.length == 0 ? "[NaN]" : Arrays.toString(cast)).append(" ## ");
            sb.append(country).append(" ## ");
            sb.append(
                    dateAdded == null ? "NaN" : new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(dateAdded))
                    .append(" ## ");
            sb.append(releaseYear).append(" ## ");
            sb.append(rating).append(" ## ");
            sb.append(duration).append(" ## ");
            sb.append(genres.length == 0 ? "[NaN]" : Arrays.toString(genres)).append(" ##");
            return sb.toString();
        }
    }

    static class LeitorCSV {
        public static List<Show> read(String caminho) {
            List<Show> lista = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                br.readLine(); 
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] campos = parseLinhaCSV(linha);
                    if (campos.length >= 11 && !campos[0].equalsIgnoreCase("show_id")) {
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
            boolean dentroAspas = false;
            StringBuilder atual = new StringBuilder();

            for (char c : linha.toCharArray()) {
                if (c == '"') {
                    dentroAspas = !dentroAspas;
                } else if (c == ',' && !dentroAspas) {
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

    static class Ordenacao {

        public static int comparacoes = 0;
        public static int movimentacoes = 0;
    
        public static void mergeSort(List<Main.Show> lista) {
            if (lista == null || lista.size() <= 1) return;
            mergeSort(lista, 0, lista.size() - 1);
        }
    
        private static void mergeSort(List<Main.Show> lista, int esq, int dir) {
            if (esq < dir) {
                int meio = (esq + dir) / 2;
                mergeSort(lista, esq, meio);
                mergeSort(lista, meio + 1, dir);
                intercalar(lista, esq, meio, dir);
            }
        }
    
        private static void intercalar(List<Main.Show> lista, int esq, int meio, int dir) {
            List<Main.Show> aux = new ArrayList<>(lista);
    
            int i = esq, j = meio + 1, k = esq;
    
            while (i <= meio && j <= dir) {
                comparacoes++;
                String d1 = lista.get(i).getDuration();
                String d2 = lista.get(j).getDuration();
    
                int cmpDur = d1.compareTo(d2);
                int cmpTitle = lista.get(i).getTitle().compareToIgnoreCase(lista.get(j).getTitle());
    
                if (cmpDur < 0 || (cmpDur == 0 && cmpTitle < 0)) {
                    aux.set(k, lista.get(i));
                    i++;
                } else {
                    aux.set(k, lista.get(j));
                    j++;
                }
                k++;
            }
    
            while (i <= meio) {
                aux.set(k, lista.get(i));
                i++; k++;
            }
    
            while (j <= dir) {
                aux.set(k, lista.get(j));
                j++; k++;
            }
    
            for (int m = esq; m <= dir; m++) {
                lista.set(m, aux.get(m));
                movimentacoes++;
            }
        }
    }
}
