/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritmos e Estruturas de Dados II
 *
 * TP05Q01 - Pesquisa Binária em Java - v1.0 - 24 / 10 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private static String normalizarData(String dataStr) {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            return "01/01/0001";
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            Date data = inputFormat.parse(dataStr.trim());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            return outputFormat.format(data);
        } catch (Exception e) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date data = inputFormat.parse(dataStr.trim());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formatado = outputFormat.format(data);
                return formatado.replaceFirst("^\\d{2}/", "01/");
            } catch (Exception e2) {
                if (dataStr.matches("\\d{4}")) {
                    return "01/01/" + dataStr;
                }
                return "01/01/0001";
            }
        }
    }

    private static int normalizarOwners(String ownersStr) {
        if (ownersStr == null || ownersStr.trim().isEmpty()) {
            return 0;
        }

        String limpo = ownersStr.replaceAll("[^0-9-]", "");
        String[] partes = limpo.split("-");

        if (partes.length > 0 && !partes[0].isEmpty()) {
            try {
                return Integer.parseInt(partes[0]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private static float normalizarPreco(String precoStr) {
        if (precoStr == null || precoStr.trim().isEmpty() || precoStr.equalsIgnoreCase("Free to Play")) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(precoStr.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private static int normalizarMetacritic(String notaStr) {
        if (notaStr == null || notaStr.trim().isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(notaStr.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static float normalizarUserScore(String notaStr) {
        if (notaStr == null || notaStr.trim().isEmpty() || notaStr.equalsIgnoreCase("tbd")) {
            return -1.0f;
        }
        try {
            return Float.parseFloat(notaStr.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    private static int normalizarAchievements(String achStr) {
        if (achStr == null || achStr.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(achStr.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Converte lista do csv para vetor de strings
    private static String[] normalizarLista(String listaStr) {
        if (listaStr == null || listaStr.trim().isEmpty() || listaStr.equals("[]")) {
            return new String[0];
        }

        String limpa = listaStr.trim();
        if (limpa.startsWith("[")) limpa = limpa.substring(1);
        if (limpa.endsWith("]")) limpa = limpa.substring(0, limpa.length() - 1);

        String[] elementosTemp = new String[500];
        int count = 0;
        int i = 0;

        while (i < limpa.length() && count < 500) {
            while (i < limpa.length() && (limpa.charAt(i) == ' ' || limpa.charAt(i) == ',')) {
                i++;
            }

            if (i >= limpa.length()) break;

            boolean temAspas = (limpa.charAt(i) == '\'');
            if (temAspas) i++;

            int inicio = i;

            if (temAspas) {
                while (i < limpa.length() && limpa.charAt(i) != '\'') {
                    i++;
                }
            } else {
                while (i < limpa.length() && limpa.charAt(i) != ',') {
                    i++;
                }
            }

            if (i > inicio) {
                String elemento = limpa.substring(inicio, i).trim();
                if (!elemento.isEmpty()) {
                    elementosTemp[count++] = elemento;
                }
            }

            if (temAspas && i < limpa.length() && limpa.charAt(i) == '\'') {
                i++;
            }
        }

        String[] resultado = new String[count];
        for (int j = 0; j < count; j++) {
            resultado[j] = elementosTemp[j];
        }

        return resultado;
    }

    private static String[] normalizarCampoSeparadoPorVirgula(String campoStr) {
        if (campoStr == null || campoStr.trim().isEmpty()) {
            return new String[0];
        }

        String[] elementos = campoStr.split(",");
        for (int i = 0; i < elementos.length; i++) {
            elementos[i] = elementos[i].trim();
        }
        return elementos;
    }

    private String formatarArray(String[] array) {
        if (array == null || array.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String[] dividirLinhaCsv(String linha) {
        if (linha == null || linha.isEmpty()) return new String[0];

        String[] camposTemp = new String[20];
        int qtdCampos = 0;
        StringBuilder campoAtual = new StringBuilder();
        boolean entreAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (c == '"') {
                if (entreAspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                    campoAtual.append('"');
                    i++;
                } else {
                    entreAspas = !entreAspas;
                }
            } else if (c == ',' && !entreAspas) {
                camposTemp[qtdCampos++] = campoAtual.toString().trim();
                campoAtual.setLength(0);
            } else {
                campoAtual.append(c);
            }
        }
        camposTemp[qtdCampos++] = campoAtual.toString().trim();

        String[] campos = new String[qtdCampos];
        for (int i = 0; i < qtdCampos; i++) {
            campos[i] = camposTemp[i];
        }
        return campos;
    }

    @Override
    public String toString() {
        String precoFormatado = String.valueOf(this.price);
        if (!precoFormatado.contains(".")) {
            precoFormatado += ".0";
        }

        return String.format(Locale.US,
                "=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##",
                id, name, releaseDate, estimatedOwners, precoFormatado,
                formatarArray(supportedLanguages), metacriticScore, userScore, achievements,
                formatarArray(publishers), formatarArray(developers), formatarArray(categories),
                formatarArray(genres), formatarArray(tags));
    }

    // Cria um game a partir do csv
    public static Game fromCsvLine(String linha) {
        String[] campos = dividirLinhaCsv(linha);

        if (campos.length < 14) return null;

        try {
            int id = Integer.parseInt(campos[0].trim());
            String nome = campos[1].trim();
            String dataLancamento = normalizarData(campos[2]);
            int donosEstimados = normalizarOwners(campos[3]);
            float preco = normalizarPreco(campos[4]);
            String[] linguas = normalizarLista(campos[5]);
            int notaMetacritic = normalizarMetacritic(campos[6]);
            float notaUsuario = normalizarUserScore(campos[7]);
            int conquistas = normalizarAchievements(campos[8]);
            String[] publishers = normalizarCampoSeparadoPorVirgula(campos[9]);
            String[] developers = normalizarCampoSeparadoPorVirgula(campos[10]);
            String[] categorias = normalizarLista(campos[11]);
            String[] generos = normalizarLista(campos[12]);
            String[] tags = normalizarLista(campos[13]);

            return new Game(id, nome, dataLancamento, donosEstimados, preco, linguas,
                    notaMetacritic, notaUsuario, conquistas, publishers, developers,
                    categorias, generos, tags);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

class Library {
    private Game[] games;
    private int count;
    private long comparisons;
    private Game[] allGames;
    private int totalGames;

    Library() {
        this.games = new Game[100000];
        this.allGames = new Game[100000];
        this.count = 0;
        this.totalGames = 0;
        this.comparisons = 0;
    }

    public void addGame(Game game) {
        if (game != null && count < games.length) {
            games[count++] = game;
        }
    }

    public Game findGameById(int id) {
        for (int i = 0; i < totalGames; i++) {
            if (allGames[i].id == id) {
                return allGames[i];
            }
        }
        return null;
    }

    public void loadFromCsv(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Game game = Game.fromCsvLine(line);
                if (game != null && totalGames < allGames.length) {
                    allGames[totalGames++] = game;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Quicksort por nome
    private void quicksort(int left, int right) {
        if (left < right) {
            int pivot = partition(left, right);
            quicksort(left, pivot - 1);
            quicksort(pivot + 1, right);
        }
    }

    private int partition(int left, int right) {
        Game pivot = games[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (games[j].name.compareTo(pivot.name) <= 0) {
                i++;
                Game temp = games[i];
                games[i] = games[j];
                games[j] = temp;
            }
        }

        Game temp = games[i + 1];
        games[i + 1] = games[right];
        games[right] = temp;

        return i + 1;
    }

    // Pesquisa binaria recursiva
    private boolean pesquisaBinariaRec(String name, int left, int right) {
        if (left > right) return false;

        int mid = (left + right) / 2;
        comparisons++;

        int cmp = name.compareTo(games[mid].name);
        if (cmp == 0) return true;
        else if (cmp < 0) return pesquisaBinariaRec(name, left, mid - 1);
        else return pesquisaBinariaRec(name, mid + 1, right);
    }

    public void sortGames() {
        quicksort(0, count - 1);
    }

    public boolean pesquisaBinaria(String name) {
        comparisons = 0;
        return pesquisaBinariaRec(name, 0, count - 1);
    }

    public long getComparisons() {
        return comparisons;
    }

    public void insertGameById(int id) {
        Game game = findGameById(id);
        if (game != null) {
            addGame(game);
        }
    }
}

class Main {
    public static void main(String[] args) {
        Library biblioteca = new Library();
        biblioteca.loadFromCsv("/tmp/games.csv");

        Scanner scanner = new Scanner(System.in);

        // Le ids ate encontrar "FIM"
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine().trim();
            if (linha.equalsIgnoreCase("FIM")) break;

            try {
                int id = Integer.parseInt(linha);
                biblioteca.insertGameById(id);
            } catch (NumberFormatException e) {
            }
        }

        biblioteca.sortGames();

        double inicio = System.nanoTime();
        int totalComparacoes = 0;

        // Pesquisa nomes
        while (scanner.hasNextLine()) {
            String nome = scanner.nextLine().trim();
            if (nome.equalsIgnoreCase("FIM")) break;

            boolean encontrado = biblioteca.pesquisaBinaria(nome);
            totalComparacoes += biblioteca.getComparisons();

            if (encontrado) System.out.println(" SIM");
            else System.out.println(" NAO");
        }

        double fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1000000.0;

        scanner.close();

        // Gera arquivo de log
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("855796_binaria.txt"));
            writer.printf("855796\t%f\t%d\n", tempoExecucao, totalComparacoes);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
