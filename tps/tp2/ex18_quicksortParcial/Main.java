import java.io.*;
import java.text.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Show> base = LeitorCSV.read("/tmp/disneyplus.csv");
        List<Show> selecionados = new ArrayList<>();

        String entrada;
        while (!(entrada = sc.nextLine()).equals("FIM")) {
            for (Show s : base) {
                if (s.getId().equals(entrada)) {
                    selecionados.add(s);
                    break;
                }
            }
        }

        long inicio = System.currentTimeMillis();
        Ordenacao.quickSort(selecionados);
        long fim = System.currentTimeMillis();

        for (int i = 0; i < Math.min(10, selecionados.size()); i++) {
            System.out.println(selecionados.get(i));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("855796_quicksort.txt"))) {
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

        public Date getDateAdded() {
            return dateAdded;
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

        public static void quickSort(List<Show> lista) {
            comparacoes = 0;
            movimentacoes = 0;
            quickSort(lista, 0, lista.size() - 1);
        }

        private static void quickSort(List<Show> lista, int esq, int dir) {
            if (esq < dir) {
                int p = particionar(lista, esq, dir);
                quickSort(lista, esq, p - 1);
                quickSort(lista, p + 1, dir);
            }
        }

        private static int particionar(List<Show> lista, int esq, int dir) {
            Show pivo = lista.get(dir);
            int i = esq - 1;

            for (int j = esq; j < dir; j++) {
                comparacoes++;
                int cmp = comparar(lista.get(j), pivo);
                if (cmp <= 0) {
                    i++;
                    trocar(lista, i, j);
                    movimentacoes += 3;
                }
            }

            trocar(lista, i + 1, dir);
            movimentacoes += 3;
            return i + 1;
        }

        private static int comparar(Show a, Show b) {
            Date d1 = a.getDateAdded();
            Date d2 = b.getDateAdded();

            if (d1 == null && d2 == null)
                return a.getTitle().compareToIgnoreCase(b.getTitle());
            if (d1 == null)
                return 1;
            if (d2 == null)
                return -1;

            int cmpData = d1.compareTo(d2);
            if (cmpData != 0)
                return cmpData;

            return a.getTitle().compareToIgnoreCase(b.getTitle());
        }

        private static void trocar(List<Show> lista, int i, int j) {
            Show temp = lista.get(i);
            lista.set(i, lista.get(j));
            lista.set(j, temp);
        }
    }
}
