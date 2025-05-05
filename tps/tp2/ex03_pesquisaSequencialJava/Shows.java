import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

class Shows {
    // Atributos privados
    private String show_id;
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

    // Construtor padrão
    public Shows() {
        this.show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[]{"NaN"};
        this.country = "NaN";
        this.date_added = null;
        this.release_year = -1;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listed_in = new String[]{"NaN"};
    }

    // Construtor completo
    public Shows(String show_id, String type, String title, String director, String[] cast, String country, Date date_added, int release_year, String rating, String duration, String[] listed_in) {
        this.show_id = show_id;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.date_added = date_added;
        this.release_year = release_year;
        this.rating = rating;
        this.duration = duration;
        this.listed_in = listed_in;
    };

    // Métodos Get e Set
    public String getShow_id() { return show_id; }
    public void setShow_id(String show_id) { this.show_id = show_id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String[] getCast() { return cast; }
    public void setCast(String[] cast) { this.cast = cast; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate_added() { return date_added; }
    public void setDate_added(Date date_added) { this.date_added = date_added; }

    public int getRelease_year() { return release_year; }
    public void setRelease_year(int release_year) { this.release_year = release_year; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String[] getListed_in() { return listed_in; }
    public void setListed_in(String[] listed_in) { this.listed_in = listed_in; }

    // Método clone
    public Shows clone() {
        return new Shows(
            this.show_id,
            this.type,
            this.title,
            this.director,
            Arrays.copyOf(this.cast, this.cast.length),
            this.country,
            this.date_added,
            this.release_year,
            this.rating,
            this.duration,
            Arrays.copyOf(this.listed_in, this.listed_in.length)
        );
    }

    // Método imprimir
    public void imprimir() {
        System.out.print("=> " + show_id + " ## ");
        System.out.print(title + " ## ");
        System.out.print(type + " ## ");
        System.out.print(director + " ## ");
        System.out.print(Arrays.toString(cast) + " ## ");
        System.out.print(country + " ## ");
        System.out.print((date_added != null ? new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(date_added) : "NaN") + " ## ");
        System.out.print(release_year + " ## ");
        System.out.print(rating + " ## ");
        System.out.print(duration + " ## ");
        System.out.print(Arrays.toString(listed_in) + " ##\n");
    }

    // Método ler
    public void ler(String linhaCSV) {
        try {
            String[] partes = splitCSVLine(linhaCSV);
            this.show_id = getOrNaN(partes, 0);
            this.type = getOrNaN(partes, 1);
            this.title = getOrNaN(partes, 2);
            this.director = getOrNaN(partes, 3);
            String castStr = getOrNaN(partes, 4);
            if (castStr.equals("NaN")) {
                this.cast = new String[]{"NaN"};
            } else {
                this.cast = castStr.split(", ");
                Arrays.sort(this.cast);
            }
            this.country = getOrNaN(partes, 5);
            String dataStr = getOrNaN(partes, 6);
            if (dataStr.equals("NaN")) {
                this.date_added = null;
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                    this.date_added = sdf.parse(dataStr);
                } catch (Exception e) {
                    this.date_added = null;
                }
            }
            try {
                this.release_year = Integer.parseInt(getOrNaN(partes, 7));
            } catch (Exception e) {
                this.release_year = -1;
            }
            this.rating = getOrNaN(partes, 8);
            this.duration = getOrNaN(partes, 9);
            this.listed_in = getOrNaN(partes, 10).split(", ");
            if (listed_in[0].equals("NaN")) listed_in = new String[]{"NaN"};
        } catch (Exception e) {
            System.out.println("Erro ao ler linha: " + linhaCSV);
        }
    }

    private String getOrNaN(String[] arr, int i) {
        if (i >= arr.length || arr[i] == null || arr[i].trim().isEmpty()) return "NaN";
        return arr[i].trim();
    }

    private String[] splitCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        for (char ch : line.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(ch);
            }
        }
        result.add(field.toString());
        return result.toArray(new String[0]);
    }

    // Main 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv"));
        Map<String, String> baseDeDados = new HashMap<>();
        String linha;
        br.readLine();
        while ((linha = br.readLine()) != null) {
            String id = linha.split(",")[0];
            baseDeDados.put(id, linha);
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        while (true) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;
            Shows s = new Shows();
            if (baseDeDados.containsKey(entrada)) {
                s.ler(baseDeDados.get(entrada));
                s.imprimir();
            } else {
                System.out.println("Show ID não encontrado: " + entrada);
            }
        }
        sc.close();
    }
    
}