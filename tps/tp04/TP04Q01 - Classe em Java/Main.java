/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q01 - Classe em Java - v1.0 - 13 / 10 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    private static String normalizeReleaseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return "01/01/0001";
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            Date date = inputFormat.parse(dateStr.trim());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            return outputFormat.format(date);
        } catch (Exception e) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date date = inputFormat.parse(dateStr.trim());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formatted = outputFormat.format(date);
                return formatted.replaceFirst("^\\d{2}/", "01/");
            } catch (Exception e2) {
                if (dateStr.matches("\\d{4}")) {
                    return "01/01/" + dateStr;
                }
                return "01/01/0001";
            }
        }
    }

    private static int normalizeEstimatedOwners(String ownerStr) {
        if (ownerStr == null || ownerStr.trim().isEmpty()) {
            return 0;
        }
        
        String cleaned = ownerStr.replaceAll("[^0-9-]", "");
        String[] parts = cleaned.split("-");
        
        if (parts.length > 0 && !parts[0].isEmpty()) {
            try {
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private static float normalizePrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty() || priceStr.equalsIgnoreCase("Free to Play")) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(priceStr.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private static int normalizeMetacriticScore(String scoreStr) {
        if (scoreStr == null || scoreStr.trim().isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(scoreStr.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static float normalizeUserScore(String scoreStr) {
        if (scoreStr == null || scoreStr.trim().isEmpty() || scoreStr.equalsIgnoreCase("tbd")) {
            return -1.0f;
        }
        try {
            return Float.parseFloat(scoreStr.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    private static int normalizeAchievements(String achStr) {
        if (achStr == null || achStr.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(achStr.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static String[] normalizeListField(String listStr) {
        if (listStr == null || listStr.trim().isEmpty() || listStr.equals("[]")) {
            return new String[0];
        }
        
        String cleaned = listStr.trim();
        if (cleaned.startsWith("[")) cleaned = cleaned.substring(1);
        if (cleaned.endsWith("]")) cleaned = cleaned.substring(0, cleaned.length() - 1);
        
        String[] tempElements = new String[500];
        int count = 0;
        int i = 0;
        
        while (i < cleaned.length() && count < 500) {
            while (i < cleaned.length() && (cleaned.charAt(i) == ' ' || cleaned.charAt(i) == ',')) {
                i++;
            }
            
            if (i >= cleaned.length()) break;

            boolean hasQuote = (cleaned.charAt(i) == '\'');
            if (hasQuote) i++;
            
            int start = i;

            if (hasQuote) {
                while (i < cleaned.length() && cleaned.charAt(i) != '\'') {
                    i++;
                }
            } else {
                while (i < cleaned.length() && cleaned.charAt(i) != ',') {
                    i++;
                }
            }

            if (i > start) {
                String element = cleaned.substring(start, i).trim();
                if (!element.isEmpty()) {
                    tempElements[count++] = element;
                }
            }
            
            if (hasQuote && i < cleaned.length() && cleaned.charAt(i) == '\'') {
                i++;
            }
        }
        
        String[] result = new String[count];
        for (int j = 0; j < count; j++) {
            result[j] = tempElements[j];
        }
        
        return result;
    }
    
    private static String[] normalizeCommaSeparated(String fieldStr) {
        if (fieldStr == null || fieldStr.trim().isEmpty()) {
            return new String[0];
        }
        
        String[] elements = fieldStr.split(",");
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].trim();
        }
        return elements;
    }

    private String formatArray(String[] array) {
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

    @Override
    public String toString() {
        String formattedPrice = String.valueOf(this.price);
        if (!formattedPrice.contains(".")) {
            formattedPrice += ".0";
        }
        
        return String.format(Locale.US,
            "=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##",
            id, name, releaseDate, estimatedOwners, formattedPrice,
            formatArray(supportedLanguages), metacriticScore, userScore, achievements,
            formatArray(publishers), formatArray(developers), formatArray(categories),
            formatArray(genres), formatArray(tags)
        );
    }

    public static Game fromCSVLine(String line) {
        String[] fields = splitCSVLine(line);

        if (fields.length < 14) { 
            return null;
        }

        try {
            int id = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            String releaseDate = normalizeReleaseDate(fields[2]);
            int estimatedOwners = normalizeEstimatedOwners(fields[3]);
            float price = normalizePrice(fields[4]);
            String[] supportedLanguages = normalizeListField(fields[5]);
            int metacriticScore = normalizeMetacriticScore(fields[6]);
            float userScore = normalizeUserScore(fields[7]);
            int achievements = normalizeAchievements(fields[8]);
            String[] publishers = normalizeCommaSeparated(fields[9]);
            String[] developers = normalizeCommaSeparated(fields[10]);
            String[] categories = normalizeListField(fields[11]);
            String[] genres = normalizeListField(fields[12]);
            String[] tags = normalizeListField(fields[13]);

            return new Game(id, name, releaseDate, estimatedOwners, price, supportedLanguages,
                          metacriticScore, userScore, achievements, publishers, developers,
                          categories, genres, tags);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private static String[] splitCSVLine(String line) {
        if (line == null || line.isEmpty()) return new String[0];

        String[] tempFields = new String[20];
        int fieldCount = 0;
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentField.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                tempFields[fieldCount++] = currentField.toString().trim();
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        tempFields[fieldCount++] = currentField.toString().trim();
        
        String[] fields = new String[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            fields[i] = tempFields[i];
        }
        return fields;
    }
}

class Library {
    private Game[] games;
    private int count;

    Library() {
        this.games = new Game[100000];
        this.count = 0;
    }

    public void addGame(Game game) {
        if (game != null && count < games.length) {
            games[count++] = game;
        }
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
                Game game = Game.fromCSVLine(line);
                if (game != null) {
                    addGame(game);
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
}

class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.loadFromCSV("/tmp/games.csv");

        Scanner scanner = new Scanner(System.in);
        int[] ids = new int[1000];
        int count = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("FIM")) {
                break;
            }
            try {
                ids[count++] = Integer.parseInt(line);
            } catch (NumberFormatException e) {
            }
        }
        scanner.close();

        library.printByIds(ids, count);
    }
}