import java.util.Random;

public class Array {
    private int[] elements;
    private int n;
    private int capacity;
    private Random rand = new Random();

    public Array(int capacity) {
        this.elements = new int[capacity];
        this.n = 0;
        this.capacity = capacity;
    }

    // Métodos de inserção e remoção
    public void inserirInicio(int x) {
        if (n >= capacity) {
            System.out.println("Erro: Array cheio!");
            return;
        }
        for (int i = n; i > 0; i--) {
            elements[i] = elements[i - 1];
        }
        elements[0] = x;
        n++;
    }

    public void inserirFim(int x) {
        if (n >= capacity) {
            System.out.println("Erro: Array cheio!");
            return;
        }
        elements[n] = x;
        n++;
    }

    public void inserir(int x, int pos) {
        if (n >= capacity) {
            System.out.println("Erro: Array cheio!");
            return;
        }
        if (pos < 0 || pos > n) {
            System.out.println("Erro: Posição inválida!");
            return;
        }
        for (int i = n; i > pos; i--) {
            elements[i] = elements[i - 1];
        }
        elements[pos] = x;
        n++;
    }

    public int removerInicio() {
        if (n == 0) {
            System.out.println("Erro: Lista vazia!");
            return -1;
        }
        int resp = elements[0];
        n--;
        for (int i = 0; i < n; i++) {
            elements[i] = elements[i + 1];
        }
        return resp;
    }

    public int removerFim() {
        if (n == 0) {
            System.out.println("Erro: Lista vazia!");
            return -1;
        }
        return elements[--n];
    }

    public int remover(int pos) {
        if (n == 0) {
            System.out.println("Erro: Lista vazia!");
            return -1;
        }
        if (pos < 0 || pos >= n) {
            System.out.println("Erro: Posição inválida!");
            return -1;
        }
        int resp = elements[pos];
        n--;
        for (int i = pos; i < n; i++) {
            elements[i] = elements[i + 1];
        }
        return resp;
    }

    // PESQUISA EM MEMÓRIA PRINCIPAL

    // Pesquisa Sequencial
    boolean pesquisaSequencial(int x) {
        boolean resp = false;
        for (int i = 0; i < n; i++){
            if (elements[i] == x){
                resp = true;
                i = n;
            }
        }
        return resp;
    }

    // Pesquisa Binária
    boolean pesquisaBinaria(int x) {
        boolean resp = false;
        int dir = n - 1, esq = 0, meio;
        while (esq <= dir) {
            meio = (esq + dir) / 2;
            if (x == elements[meio]){
                resp = true;
                esq = n;
            } else if (x > elements[meio]){
                esq = meio + 1;
            } else {
                dir = meio - 1;
            }
        }
        return resp;
    }

    // // Pesquisa Binária Recursiva
    // int pesquisaBinariaRecursiva(int array[], int x, int esq, int dir) {
    //     if (esq > dir) return -1;
    //     else {
    //         int meio = (esq + dir) / 2;
    //         if (array[meio] == x) return meio;
    //         else if (array[meio] < x) return pesquisaBinariaRecursiva(array, x, meio + 1, dir);
    //         else return pesquisaBinariaRecursiva(array, x, esq, meio - 1);
    //     }
    // }

    // ORDENAÇÃO EM MEMÓRIA PRINCIPAL

    public void swap(int x, int y) {
        int temp = elements[x];
        elements[x] = elements[y];
        elements[y] = temp;
    }

    // Selection Sort
    public void selectionSort() {
        for (int i = 0; i < (n - 1); i++) {
            int menor = i; 
            for (int j = (i + 1); j < n; j++){
                if (elements[menor] > elements[j]){
                    menor = j;
                }
            }
            swap(menor, i);
        }
    }

    // Selection Sort Otimizado

    // Insertion Sort
    public static void insertionSort() {
        for (int i = 1; i < n; i++) {
            int tmp = elements[i];
            int j = i - 1;
            while ( (j >= 0) && (elements[j] > tmp) ){
                elements[j + 1] = elements[j];
                j--;
            }
            elements[j + 1] = tmp;
        }
    }

    // Quicksort
    // void quicksort() {
    //     quicksort(0,n-1);
    // }

    // void quicksort(int esq, int dir) {
    //     int i = esq,    
    //         j = dir,
    //         pivo = array[(esq+dir)/2];
    //     while (i <= j) {
    //         while (array[i] < pivo){ 
    //             i++;
    //         }
    //         while (array[j] > pivo){
    //             j--;
    //         }
    //         if (i <= j) {   
    //             swap(i, j);
    //             i++;
    //             j--;
    //         }
    //     }
    //     if (esq < j){
    //         quicksort(esq, j);
    //     }
    //     if (i < dir){
    //         quicksort(i, dir);
    //     }
    // }

    // // Bubble Sort
    // void bubbleSort(int array[], int n) {
    //     for (int i = 0; i < n - 1; i++) {
    //         for (int j = 0; j < n - i - 1; j++) {
    //             if (array[j] > array[j + 1]) {
    //                 swap(array[j], array[j + 1]);
    //             }
    //         }
    //     }
    // }

    // // Bubble Sort Otimizado
    // void bubbleSortOtimizado(int array[], int n) {
    //     bool trocou;
    //     for (int i = 0; i < n - 1; i++) {
    //         trocou = false;
    //         for (int j = 0; j < n - i - 1; j++) {
    //             if (array[j] > array[j + 1]) {
    //                 swap(array[j], array[j + 1]);
    //                 trocou = true;
    //             }
    //         }
    //         if (!trocou) break; // Se não houve trocas, já está ordenado
    //     }
    // }

    // Método de exibição
    public void show() {
        System.out.print("Array:");
        for (int i = 0; i < this.n; i++) {
            System.out.print(" [" + this.elements[i] + "]");
        }
        for (int i = this.n; i < this.capacity; i++) {
            System.out.print(" {}");
        }
        System.out.println();
    }

    public void fillRandom(int min, int max) {
        if (capacity < 1) {
            System.out.println("Erro: Capacidade insuficiente!");
            return;
        }
        for (int i = 0; i < capacity; i++) {
            elements[i] = rand.nextInt(max - min + 1) + min;
        }
        n = capacity;
    }
}
