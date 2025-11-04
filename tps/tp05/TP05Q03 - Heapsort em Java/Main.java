/**
 *
 * Pontifícia Universidade Católica de Minas Gerais
 *
 * Curso de Ciência da Computação
 * Algoritmos e Estruturas de Dados II
 *
 * TP05Q01 - Heapsort em Java adaptado - v2.0 - 24/10/2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

class Game {
    int id;
    String name;
    String releaseDate;
    int estimatedOwners;
    float price;
    String[] supportedLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    String[] publishers;
    String[] developers;
    String[] categories;
    String[] genres;
    String[] tags;

    Game(int id, String name, String releaseDate, int estimatedOwners, float price,
         String[] supportedLanguages, int metacriticScore, float userScore, int achievements,
         String[] publishers, String[] developers, String[] categories, String[] genres, String[] tags) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.developers = developers;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Converte a data de lançamento para formato dd/mm/yyyy
    private static String converterDataLancamento(String dataStr) {
        if (dataStr == null || dataStr.trim().isEmpty()) return "01/01/0001";

        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            Date data = formatoEntrada.parse(dataStr.trim());
            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSaida.format(data);
        } catch (Exception e) {
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date data = formatoEntrada.parse(dataStr.trim());
                SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
                String formatada = formatoSaida.format(data);
                return formatada.replaceFirst("^\\d{2}/", "01/");
            } catch (Exception e2) {
                if (dataStr.matches("\\d{4}")) return "01/01/" + dataStr;
                return "01/01/0001";
            }
        }
    }

    // Converte o número de donos estimados
    private static int converterDonosEstimados(String str) {
        if (str == null || str.trim().isEmpty()) return 0;
        String limpo = str.replaceAll("[^0-9-]", "");
        String[] partes = limpo.split("-");
        if (partes.length > 0 && !partes[0].isEmpty()) {
            try { return Integer.parseInt(partes[0]); } catch (NumberFormatException e) { return 0; }
        }
        return 0;
    }

    // Converte preço
    private static float converterPreco(String str) {
        if (str == null || str.trim().isEmpty() || str.equalsIgnoreCase("Free to Play")) return 0.0f;
        try { return Float.parseFloat(str.trim().replace(',', '.')); } catch (NumberFormatException e) { return 0.0f; }
    }

    // Converte pontuação Metacritic
    private static int converterScoreMetacritic(String str) {
        if (str == null || str.trim().isEmpty()) return -1;
        try { return Integer.parseInt(str.trim()); } catch (NumberFormatException e) { return -1; }
    }

    // Converte pontuação de usuários
    private static float converterScoreUsuarios(String str) {
        if (str == null || str.trim().isEmpty() || str.equalsIgnoreCase("tbd")) return -1.0f;
        try { return Float.parseFloat(str.trim().replace(',', '.')); } catch (NumberFormatException e) { return -1.0f; }
    }

    // Converte conquistas
    private static int converterConquistas(String str) {
        if (str == null || str.trim().isEmpty()) return 0;
        try { return Integer.parseInt(str.trim()); } catch (NumberFormatException e) { return 0; }
    }

    // Converte campos no formato lista [a, b, c]
    private static String[] converterLista(String str) {
        if (str == null || str.trim().isEmpty() || str.equals("[]")) return new String[0];
        String s = str.trim();
        if (s.startsWith("[")) s = s.substring(1);
        if (s.endsWith("]")) s = s.substring(0, s.length() - 1);

        String[] temp = new String[500];
        int count = 0, i = 0;
        while (i < s.length() && count < 500) {
            while (i < s.length() && (s.charAt(i) == ' ' || s.charAt(i) == ',')) i++;
            if (i >= s.length()) break;
            boolean hasQuote = s.charAt(i) == '\'';
            if (hasQuote) i++;
            int start = i;
            if (hasQuote) while (i < s.length() && s.charAt(i) != '\'') i++;
            else while (i < s.length() && s.charAt(i) != ',') i++;
            if (i > start) { temp[count++] = s.substring(start, i).trim(); }
            if (hasQuote && i < s.length() && s.charAt(i) == '\'') i++;
        }
        String[] result = new String[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    // Converte campos separados por vírgula
    private static String[] converterSeparadoPorVirgula(String str) {
        if (str == null || str.trim().isEmpty()) return new String[0];
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; i++) arr[i] = arr[i].trim();
        return arr;
    }

    // Formata arrays para saída
    private String formatarArray(String[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        String precoStr = String.valueOf(price);
        if (!precoStr.contains(".")) precoStr += ".0";
        return String.format(Locale.US,
                "=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##",
                id, name, releaseDate, estimatedOwners, precoStr,
                formatarArray(supportedLanguages), metacriticScore, userScore, achievements,
                formatarArray(publishers), formatarArray(developers),
                formatarArray(categories), formatarArray(genres), formatarArray(tags));
    }

    public static Game fromCSVLine(String line) {
        String[] fields = dividirLinhaCSV(line);
        if (fields.length < 14) return null;
        try {
            return new Game(
                    Integer.parseInt(fields[0].trim()),
                    fields[1].trim(),
                    converterDataLancamento(fields[2]),
                    converterDonosEstimados(fields[3]),
                    converterPreco(fields[4]),
                    converterLista(fields[5]),
                    converterScoreMetacritic(fields[6]),
                    converterScoreUsuarios(fields[7]),
                    converterConquistas(fields[8]),
                    converterSeparadoPorVirgula(fields[9]),
                    converterSeparadoPorVirgula(fields[10]),
                    converterLista(fields[11]),
                    converterLista(fields[12]),
                    converterLista(fields[13])
            );
        } catch (NumberFormatException e) { return null; }
    }

    // Divide CSV considerando aspas
    private static String[] dividirLinhaCSV(String line) {
        if (line == null || line.isEmpty()) return new String[0];
        String[] temp = new String[20];
        int count = 0;
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') { current.append('"'); i++; }
                else inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                temp[count++] = current.toString().trim();
                current.setLength(0);
            } else current.append(c);
        }
        temp[count++] = current.toString().trim();
        String[] result = new String[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
}

class Library {
    private Game[] games;
    private int count;
    private long comparisons;
    private Game[] allGames;
    private int allGamesCount;
    private long heapComparisons;
    private long movimentacoes;

    Library() {
        games = new Game[100000];
        allGames = new Game[100000];
        count = 0; allGamesCount = 0;
        comparisons = 0; heapComparisons = 0; movimentacoes = 0;
    }

    public void addGame(Game game) {
        if (game != null && count < games.length) games[count++] = game;
    }

    public Game findGameInAll(int id) {
        for (int i = 0; i < allGamesCount; i++) if (allGames[i].id == id) return allGames[i];
        return null;
    }

    public Game findGame(int id) {
        for (int i = 0; i < count; i++) if (games[i].id == id) return games[i];
        return null;
    }

    public void loadFromCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                Game g = Game.fromCSVLine(line);
                if (g != null && allGamesCount < allGames.length) allGames[allGamesCount++] = g;
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void insertGameById(int id) {
        Game g = findGameInAll(id);
        if (g != null) addGame(g);
    }

    public void heapsort() {
        if (count <= 1) return;
        Game[] tmp = new Game[count + 1];
        for (int i = 0; i < count; i++) tmp[i + 1] = games[i];
        for (int heapSize = 2; heapSize <= count; heapSize++) construir(tmp, heapSize);
        int heapSize = count;
        while (heapSize > 1) {
            swapInHeap(tmp, 1, heapSize--);
            reconstruir(tmp, heapSize);
        }
        for (int i = 0; i < count; i++) { games[i] = tmp[i + 1]; movimentacoes++; }
    }

    private void construir(Game[] arr, int heapSize) {
        for (int i = heapSize; i > 1 && compareForHeap(arr[i], arr[i / 2]) > 0; i /= 2) swapInHeap(arr, i, i / 2);
    }

    private void reconstruir(Game[] arr, int heapSize) {
        int i = 1;
        while (i <= heapSize / 2) {
            int filho = getMaiorFilho(arr, i, heapSize);
            if (compareForHeap(arr[i], arr[filho]) < 0) { swapInHeap(arr, i, filho); i = filho; }
            else i = heapSize;
        }
    }

    private int getMaiorFilho(Game[] arr, int i, int heapSize) {
        if (2 * i == heapSize || compareForHeap(arr[2 * i], arr[2 * i + 1]) > 0) return 2 * i;
        else return 2 * i + 1;
    }

    private int compareForHeap(Game a, Game b) {
        heapComparisons++;
        if (a.estimatedOwners != b.estimatedOwners) return Integer.compare(a.estimatedOwners, b.estimatedOwners);
        return Integer.compare(a.id, b.id);
    }

    private void swapInHeap(Game[] arr, int i, int j) {
        Game tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        movimentacoes += 3;
    }

    public boolean binarySearch(String name) {
        comparisons = 0;
        return binarySearchRecursive(name, 0, count - 1);
    }

    private boolean binarySearchRecursive(String name, int left, int right) {
        if (left > right) return false;
        int mid = (left + right) / 2;
        comparisons++;
        int cmp = name.compareTo(games[mid].name);
        if (cmp == 0) return true;
        else if (cmp < 0) return binarySearchRecursive(name, left, mid - 1);
        else return binarySearchRecursive(name, mid + 1, right);
    }

    public long getHeapComparisons() { return heapComparisons; }
    public long getMovimentacoes() { return movimentacoes; }
    public int getComparisons() { return (int)comparisons; }

    public void printAllGames() { for (int i = 0; i < count; i++) System.out.println(games[i]); }
}


class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.loadFromCSV("/tmp/games.csv");

        Scanner scanner = new Scanner(System.in);


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("FIM")) {
                break;
            }
            try {
                int id = Integer.parseInt(line);
                library.insertGameById(id);
            } catch (NumberFormatException e) {

            }
        }


        long startHeap = System.currentTimeMillis();
        library.heapsort();
        long endHeap = System.currentTimeMillis();
        long heapTime = endHeap - startHeap;

        library.printAllGames();

        String matricula = "855796";
        String logFileName = matricula + "_heapsort.txt";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(logFileName));
            writer.printf("%s\t%d\t%d\t%d\n", matricula, library.getHeapComparisons(), library.getMovimentacoes(), heapTime);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        double inicio = System.nanoTime();
        int totalComparisons = 0;

        while (scanner.hasNextLine()) {
            String name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("FIM")) {
                break;
            }

            boolean found = library.binarySearch(name);
            totalComparisons += library.getComparisons();

            if (found) {
                System.out.println(" SIM");
            } else {
                System.out.println(" NAO");
            }
        }

        double fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1000000.0;

        scanner.close();

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("855796_binaria.txt"));
            writer.printf("855796\t%f\t%d\n", tempoExecucao, totalComparisons);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
