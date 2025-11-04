/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritmos e Estruturas de Dados II
 *
 * TP06Q04 - Fila de Registros em Java - v1.0 - 03 / 11 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
                "%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##",
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
    public Game[] games;
    public int count;
    private int comparisons; 
    private Game[] allGames;
    private int allGamesCount;

    private long mergeComparisons;
    private long mergeMovimentacoes;

    Library() {
        this.games = new Game[100000];
        this.count = 0;
        this.comparisons = 0;
        this.allGames = new Game[100000];
        this.allGamesCount = 0;
        this.mergeComparisons = 0;
        this.mergeMovimentacoes = 0;
    }

    public void addGame(Game game) {
        if (game != null && count < games.length) {
            games[count++] = game;
        }
    }

    public Game findGameInAll(int id) {
        for (int i = 0; i < allGamesCount; i++) {
            if (allGames[i].id == id) {
                return allGames[i];
            }
        }
        return null;
    }

    public Game findGame(int id) {
        for (int i = 0; i < count; i++) {
            if (games[i].id == id) {
                return games[i];
            }
        }
        return null;
    }

    public void loadFromCSV(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Game game = Game.fromCsvLine(line);
                if (game != null && allGamesCount < allGames.length) {
                    allGames[allGamesCount++] = game;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printByIds(int[] ids, int idsCount) {
        for (int i = 0; i < idsCount; i++) {
            Game game = findGame(ids[i]);
            if (game != null) {
                System.out.println(game.toString());
            }
        }
    }

    public void insertGameById(int id) {
        Game game = findGameInAll(id);
        if (game != null) {
            addGame(game);
        }
    }


    private int compareForMerge(Game a, Game b) {
        mergeComparisons++;
        if (a.price != b.price) {
            return Float.compare(a.price, b.price);
        } else {
            return Integer.compare(a.id, b.id);
        }
    }

    public void mergesort() {
        if (count <= 1) return;
        mergesortRec(0, count - 1);
    }

    private void mergesortRec(int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesortRec(esq, meio);
            mergesortRec(meio + 1, dir);
            intercalar(esq, meio, dir);
        }
    }

    private void intercalar(int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        Game[] a1 = new Game[n1 + 1];
        Game[] a2 = new Game[n2 + 1];


        for (int i = 0; i < n1; i++) {
            a1[i] = games[esq + i];
            mergeMovimentacoes++;
        }
        for (int j = 0; j < n2; j++) {
            a2[j] = games[meio + 1 + j];
            mergeMovimentacoes++;
        }

        Game sentinela = new Game(Integer.MAX_VALUE, "", "", Integer.MAX_VALUE, Float.POSITIVE_INFINITY,
                new String[0], -1, -1.0f, 0, new String[0], new String[0], new String[0], new String[0], new String[0]);

        a1[n1] = sentinela;
        a2[n2] = sentinela;

        int i = 0, j = 0;
        for (int k = esq; k <= dir; k++) {
            if (compareForMerge(a1[i], a2[j]) <= 0) {
                games[k] = a1[i++];
                mergeMovimentacoes++;
            } else {
                games[k] = a2[j++];
                mergeMovimentacoes++;
            }
        }
    }



    public void printAllGames() {
        for (int i = 0; i < count; i++) {
            System.out.println(games[i].toString());
        }
    }


    public boolean binarySearch(String name) {
        comparisons = 0;
        return binarySearchRecursive(name, 0, count - 1);
    }

    private boolean binarySearchRecursive(String name, int left, int right) {
        if (left > right) {
            return false;
        }

        int mid = (left + right) / 2;
        comparisons++;

        int cmp = name.compareTo(games[mid].name);

        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return binarySearchRecursive(name, left, mid - 1);
        } else {
            return binarySearchRecursive(name, mid + 1, right);
        }
    }

    public int getComparisons() {
        return comparisons;
    }

    public long getMergeComparisons() {
        return mergeComparisons;
    }

    public long getMergeMovimentacoes() {
        return mergeMovimentacoes;
    }


    public void printFiveCheapestAndMostExpensive() {
        int limit = Math.min(5, count);

        System.out.println("| 5 preços mais caros |");
        for (int i = 0; i < limit; i++) {
            System.out.printf(Locale.US, "%.1f\n", games[i].price);
        }

        System.out.println("| 5 preços mais baratos |");
        for (int i = count - 1; i >= Math.max(0, count - limit); i--) {
            System.out.printf(Locale.US, "%.1f\n", games[i].price);
        }
    }
}

class CelulaGame {
    public Game elemento;
    public CelulaGame prox;

    public CelulaGame() {
        this(null);
    }

    public CelulaGame(Game elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class Fila {
    private CelulaGame primeiro;
    private CelulaGame ultimo;

    public Fila() {
        primeiro = new CelulaGame();
        ultimo = primeiro;
    }

    public void inserir(Game x) {
        ultimo.prox = new CelulaGame(x);
        ultimo = ultimo.prox;
    }

    public Game remover() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover!");
        }

        CelulaGame tmp = primeiro;
        primeiro = primeiro.prox;
        Game resp = primeiro.elemento;
        tmp.prox = null;
        tmp = null;
        return resp;
    }

    public void mostrar() {
        int index = 0;
        for (CelulaGame i = primeiro.prox; i != null; i = i.prox) {
            System.out.println("[" + index + "] => " + i.elemento.toString());
            index++;
        }
    }

}

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Library biblioteca = new Library();
        biblioteca.loadFromCSV("/tmp/games.csv");

        Fila fila = new Fila();

        while (sc.hasNextLine()) {
            String entrada = sc.nextLine().trim();
            if (entrada.equals("FIM")) break;

            int id = Integer.parseInt(entrada);
            Game game = biblioteca.findGameInAll(id);
            fila.inserir(game);
        }

        int n = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < n; i++) {
            String comando = sc.nextLine().trim();
            if (comando.charAt(0) == 'I') {
                int id = Integer.parseInt(comando.substring(2));
                Game game = biblioteca.findGameInAll(id);
                fila.inserir(game);
            } else if (comando.charAt(0) == 'R') {
                Game removido = fila.remover();
                System.out.println("(R) " + removido.name);
            }
        }

        fila.mostrar();
        sc.close();
    }

}
