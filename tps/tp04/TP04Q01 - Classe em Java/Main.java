import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher; 
import java.util.Scanner; 
import java.util.Locale; // <<< 1. ADICIONE ESTA IMPORTAÇÃO

class Game {
    // ATRIBUTOS DO JOGO (mantidos)
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

    // Construtor (mantido)
    Game(
        int id, String name, String releaseDate, int estimatedOwners, float price,
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

    // --- Métodos de Normalização (RESTAURADOS) ---

    private static String normalizeReleaseDate(String dateStr) {
        final String DEFAULT_DATE = "01/01/0001";
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return DEFAULT_DATE;
        }

        // Formatos comuns no CSV, forçando Locale.ENGLISH para leitura correta dos nomes dos meses.
        String[] possibleFormats = {"MMM dd, yyyy", "MMM yyyy"};
        for (String format : possibleFormats) {
            try {
                // *** MUDANÇA AQUI: FORÇANDO Locale.ENGLISH ***
                SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                inputFormat.setLenient(true); 
                Date date = inputFormat.parse(dateStr.trim());
                
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                
                // Regra: se o formato é apenas mês e ano (MMM yyyy), o dia deve ser 01
                if (format.equals("MMM yyyy")) {
                     String formatted = outputFormat.format(date);
                     // Garante que o dia seja 01, mesmo que o lenient o tenha preenchido com outro valor
                     return formatted.replaceFirst("^\\d{2}/", "01/");
                }
                
                return outputFormat.format(date);
            } catch (ParseException e) {
                // Tenta o próximo formato
            }
        }
        
        // Regra: Se a data for apenas o ano (ex: "2010")
        if (dateStr.matches("\\d{4}")) {
            return "01/01/" + dateStr;
        }

        return DEFAULT_DATE;
    }

    private static int normalizeEstimatedOwners(String ownerStr) {
        if (ownerStr == null || ownerStr.trim().isEmpty()) {
            return 0;
        }
        // Remove vírgulas, sinais de mais (+) e quaisquer caracteres não-dígito, exceto hífen
        String cleaned = ownerStr.replaceAll("[^0-9-]", "");
        
        // Se for uma faixa (ex: 75000-150000), pega o primeiro valor (o mínimo)
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
        if (priceStr == null || priceStr.trim().isEmpty() || priceStr.trim().equalsIgnoreCase("Free to Play")) {
            return 0.0f;
        }
        try {
            // Se o campo de preço usa vírgula como separador decimal, substitui por ponto
            String cleanedPrice = priceStr.trim().replace(',', '.');
            return Float.parseFloat(cleanedPrice);
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
        if (scoreStr == null || scoreStr.trim().isEmpty() || scoreStr.trim().equalsIgnoreCase("tbd")) {
            return -1.0f;
        }
        try {
             // O user score pode estar como porcentagem no arquivo, mas deve ser armazenado como float.
             // Substitui vírgula por ponto, se for o caso.
            String cleanedScore = scoreStr.trim().replace(',', '.');
            return Float.parseFloat(cleanedScore);
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
    
    /**
     * Extrai e normaliza arrays de strings de campos com formato de lista (ex: idiomas, categorias, tags),
     * removendo colchetes, aspas simples e separando por vírgula.
     */
    private static String[] normalizeListField(String listStr) {
        if (listStr == null || listStr.trim().isEmpty() || listStr.equals("[]")) {
            return new String[0];
        }
        
        // Remove aspas simples e colchetes externos, e substitui a separação padrão (', ')
        String cleaned = listStr.trim()
                                .replaceAll("^\\[\\s*'", "") // Remove [' no início
                                .replaceAll("'\\s*\\]$", "") // Remove '] no final
                                .replaceAll("',\\s*'", ","); // Substitui ', ' por ,
        
        // Se ainda restar [' e '] é porque há problemas de aspas duplas, mas vamos assumir o padrão
        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
             cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        
        // Remove aspas simples restantes
        //cleaned = cleaned.replace("'", "");

        if (cleaned.isEmpty()) {
             return new String[0];
        }
        
        // Separa por vírgula e remove espaços ao redor
        String[] elements = cleaned.split(",");
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].trim();
        }
        return elements;
    }
    
    /**
     * Extrai e normaliza arrays de strings de campos com múltiplos valores separados por vírgula 
     * (ex: Publishers, Developers).
     */
    private static String[] normalizeCommaSeparated(String fieldStr) {
        if (fieldStr == null || fieldStr.trim().isEmpty()) {
            return new String[0];
        }
        // Separa por vírgula e remove espaços
        String[] elements = fieldStr.split(",");
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].trim();
        }
        return elements;
    }

    // Método formatArray (mantido)
    private String formatArray(String[] array) {
        if (array == null || array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Método toString (mantido)
    @Override
    public String toString() {
        // Formata o preço separadamente
        String formattedPrice;
        if (this.price == 0.0f) {
            formattedPrice = String.format(Locale.US, "%.1f", this.price); // Ex: 0.0
        } else {
            formattedPrice = String.format(Locale.US, "%.2f", this.price); // Ex: 19.99
        }
        
        // O userScore permanece em %.1f (é o sétimo campo numérico)
        
        return String.format(
            Locale.US,
            "=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##",
            id,
            name,
            releaseDate,
            estimatedOwners,
            formattedPrice, // Usamos %s para a string formatada
            formatArray(supportedLanguages),
            metacriticScore,
            userScore,
            achievements,
            formatArray(publishers),
            formatArray(developers),
            formatArray(developers),
            formatArray(categories),
            formatArray(genres),
            formatArray(tags)
        );
    }

    // Método fromCSVLine (mantido)
    public static Game fromCSVLine(String line) {
        List<String> fields = splitCSVLine(line);

        if (fields.size() < 14) { 
            // Nao imprima no stdout/pubout, use o stderr para debug
            System.err.println("Erro de parsing: linha com campos insuficientes (" + fields.size() + "): " + line);
            return null;
        }

        try {
            int id = Integer.parseInt(fields.get(0).trim());
            String name = fields.get(1).trim();
            String releaseDate = normalizeReleaseDate(fields.get(2));
            int estimatedOwners = normalizeEstimatedOwners(fields.get(3));
            float price = normalizePrice(fields.get(4));
            String[] supportedLanguages = normalizeListField(fields.get(5));
            int metacriticScore = normalizeMetacriticScore(fields.get(6));
            float userScore = normalizeUserScore(fields.get(7));
            int achievements = normalizeAchievements(fields.get(8));
            String[] publishers = normalizeCommaSeparated(fields.get(9));
            String[] developers = normalizeCommaSeparated(fields.get(10));
            String[] categories = normalizeListField(fields.get(11));
            String[] genres = normalizeListField(fields.get(12));
            String[] tags = normalizeListField(fields.get(13));

            return new Game(
                id, name, releaseDate, estimatedOwners, price, supportedLanguages,
                metacriticScore, userScore, achievements, publishers, developers,
                categories, genres, tags
            );
        } catch (NumberFormatException e) {
            System.err.println("Erro de conversão numérica em linha: " + line);
            return null;
        }
    }
    
    // Método splitCSVLine (NOVO E CORRETO, MÁQUINA DE ESTADO)
    private static List<String> splitCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        if (line == null || line.isEmpty()) return fields;

        line = line.trim();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Escapamento de aspas: "" vira "
                    currentField.append('"');
                    i++; 
                } else {
                    // Inverte o estado de aspas
                    inQuotes = !inQuotes;
                    // IMPORTANTE: Não adiciona as aspas delimitadoras ao campo
                }
            } else if (c == ',' && !inQuotes) {
                // Fim do campo
                fields.add(currentField.toString().trim());
                currentField.setLength(0); 
            } else {
                // Adiciona o caractere ao campo
                currentField.append(c);
            }
        }

        // Adiciona o último campo
        fields.add(currentField.toString().trim());
        
        // Pós-processamento (geralmente não necessário se o parser for perfeito)
        // Removido o loop de pós-processamento para simplificar e confiar na lógica de estado.

        return fields;
    }
}

// Classe que representa um nó na lista de encadeamento do hash
class HashNode {
    Game game;
    HashNode prox;

    HashNode(Game game) {
        this.game = game;
        this.prox = null;
    }
}

class GamesHashTable {
    
    private HashNode[] table;
    private int size;
    private static final int CAPACITY = 101; 

    GamesHashTable() {
        this.table = new HashNode[CAPACITY];
        this.size = 0;
    }

    private int hash(int id) {
        return Math.abs(id) % CAPACITY;
    }

    public void addGame(Game game) {
        if (game == null) return;

        int index = hash(game.id);
        
        // Verifica se o ID já existe antes de inserir (opcional, mas boa prática)
        // Se a busca for estritamente por ID, jogos duplicados devem ser evitados.
        if (findGame(game.id) != null) return; 

        HashNode newNode = new HashNode(game);
        newNode.prox = table[index];
        table[index] = newNode;
        this.size++;
    }
    
    public Game findGame(int id) {
        int index = hash(id);
        
        for (HashNode current = table[index]; current != null; current = current.prox) {
            if (current.game.id == id) {
                return current.game;
            }
        }
        return null;
    }

    public int initCsv(String path) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); 
            
            String line;
            while ((line = br.readLine()) != null) {
                Game game = Game.fromCSVLine(line);
                if (game != null) {
                    addGame(game);
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        } catch (Exception e) {
             System.err.println("Ocorreu um erro geral durante o processamento do CSV: " + e.getMessage());
        }
        return count;
    }

    public String printInOrder(List<Integer> ids) {
        StringBuilder sb = new StringBuilder();
        for (int id : ids) {
            Game game = findGame(id);
            if (game != null) {
                sb.append(game.toString()).append("\n");
            } 
        }
        return sb.toString();
    }
}

class Main {
    public static void main(String[] args) {

        GamesHashTable lista = new GamesHashTable(); 
        String filePath = "games.csv"; 

        // System.out.println("Iniciando leitura e normalização do arquivo " + filePath + "...");
        
        int gamesLoaded = lista.initCsv(filePath);

        // --- BLOCO PARA LER OS IDs DO PUBIN ---
        List<Integer> idsToPrint = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        // System.out.println("Aguardando IDs para impressão (digite 'FIM' para terminar):");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("FIM")) {
                break;
            }
            try {
                int id = Integer.parseInt(line);
                idsToPrint.add(id);
            } catch (NumberFormatException e) {
                // Ignorar linhas inválidas (incluindo o prompt do sistema)
            }
        }
        scanner.close();
        // ------------------------------------------

        // System.out.println("\n--- Resumo ---");
        // System.out.println("Jogos carregados no Mapa de Hash: " + gamesLoaded);
        // System.out.println("IDs lidos para impressão: " + idsToPrint.size());
         
        //  --- CHAME O MÉTODO DE IMPRESSÃO ---
        // System.out.println("\n--- Dados dos Jogos (Ordem do Input) ---");
        System.out.print(lista.printInOrder(idsToPrint)); 
    }
}