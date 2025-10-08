import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Shows> base = LeitorCSV.carregar("/tmp/disneyplus.csv");
        List<Shows> selecionados = new ArrayList<>();

        // Parte 1 - Inserção por ID
        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) break;

            for (Shows r : base) {
                if (r.getId().equals(linha)) {
                    selecionados.add(r);
                    break;
                }
            }
        }

        // Parte 2 - Pesquisas por título
        List<String> pesquisas = new ArrayList<>();
        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) break;
            pesquisas.add(linha);
        }

        Pesquisa p = new Pesquisa();
        long inicio = System.currentTimeMillis();

        System.out.println("NAO");
        for (String titulo : pesquisas) {
            Shows r = p.buscarPorTitulo(selecionados, titulo);
            System.out.println((r != null) ? "SIM" : "NAO");
        }

        long fim = System.currentTimeMillis();
        long duracao = fim - inicio;

        // Log
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("855796_sequencial.txt"))) {
            bw.write("855796\t" + duracao + "ms\t" + p.getComparacoes());
        } catch (IOException e) {
            System.err.println("Erro ao escrever log.");
        }

        sc.close();
    }

    static class Shows {
        private String id;
        private String titulo;

        public Shows(String[] campos) {
            this.id = campos[0];
            this.titulo = campos[2];
        }

        public String getId() {
            return id;
        }

        public String getTitulo() {
            return titulo;
        }
    }

    static class LeitorCSV {
        public static List<Shows> carregar(String caminho) {
            List<Shows> lista = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                br.readLine();
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] campos = separarCSV(linha);
                    if (campos.length >= 3) {
                        lista.add(new Shows(campos));
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }
            return lista;
        }

        private static String[] separarCSV(String linha) {
            List<String> resultado = new ArrayList<>();
            boolean dentroAspas = false;
            StringBuilder valor = new StringBuilder();

            for (char c : linha.toCharArray()) {
                if (c == '"') {
                    dentroAspas = !dentroAspas;
                } else if (c == ',' && !dentroAspas) {
                    resultado.add(valor.toString());
                    valor.setLength(0);
                } else {
                    valor.append(c);
                }
            }
            resultado.add(valor.toString());
            return resultado.toArray(new String[0]);
        }
    }

    static class Pesquisa {
        private int comparacoes = 0;

        public int getComparacoes() {
            return comparacoes;
        }

        public Shows buscarPorTitulo(List<Shows> lista, String titulo) {
            for (Shows r : lista) {
                comparacoes++;
                if (r.getTitulo() != null && r.getTitulo().trim().equalsIgnoreCase(titulo.trim())) {
                    return r;
                }
            }
            return null;
        }
    }
}
