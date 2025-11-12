import java.util.Scanner;

class Aluno {
    String nome;
    char regiao;
    int tempo;

    Aluno() {
        this.nome = "";
        this.regiao = ' ';
        this.tempo = 0;
    }
}

// classe Lista simples
class ListaAlunos {
    Aluno[] alunos;
    int n; // quantidade atual

    ListaAlunos(int tamanho) {
        alunos = new Aluno[tamanho];
        n = 0;
    }

    void inserir(Aluno a) {
        alunos[n++] = a;
    }

    // método para ordenar por tempo, depois região, depois nome
    void ordenar() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (precisaTrocar(alunos[j], alunos[j + 1])) {
                    Aluno temp = alunos[j];
                    alunos[j] = alunos[j + 1];
                    alunos[j + 1] = temp;
                }
            }
        }
    }

    // compara dois alunos para saber se precisam trocar de lugar
    boolean precisaTrocar(Aluno a, Aluno b) {
        if (a.tempo > b.tempo) return true;
        if (a.tempo < b.tempo) return false;

        // se o tempo for igual, compara pela região
        if (a.regiao > b.regiao) return true;
        if (a.regiao < b.regiao) return false;

        // se região também for igual, compara pelo nome (ordem alfabética)
        return a.nome.compareTo(b.nome) > 0;
    }

    void imprimir() {
        for (int i = 0; i < n; i++) {
            System.out.println(alunos[i].nome);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Q;

        while ((Q = sc.nextInt()) != -1) { // lê até encontrar -1
            ListaAlunos lista = new ListaAlunos(Q);

            for (int i = 0; i < Q; i++) {
                Aluno a = new Aluno();
                a.nome = sc.next();
                a.regiao = sc.next().charAt(0);
                a.tempo = sc.nextInt();
                lista.inserir(a);
            }

            lista.ordenar();
            lista.imprimir();
        }

        sc.close();
    }
}
