class Lista {
    int[] array;
    int n;

    Lista() { this(6); }
    Lista(int tamanho) {
        array = new int[tamanho];
        n = 0;
    }
    
    void inserirInicio(int x) throws Exception {
        if (n >= array.length)
            throw new Exception("Erro!");

        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = x;
        n++;
    }

    void inserirFim(int x) throws Exception {
        if (n >= array.length)
            throw new Exception("Erro!");
        array[n] = x;
        n++;
    }

    void inserir(int x, int pos) throws Exception {
        if (n >= array.length || pos < 0 || pos > n)
            throw new Exception("Erro!");

        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }
        array[pos] = x;
        n++;
    }

    int removerInicio() throws Exception {
        if (n == 0)
            throw new Exception("Erro!");
        
        int resp = array[0];
        n--;

        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }

        return resp;
    }

    int removerFim() throws Exception {
        if (n == 0)
            throw new Exception("Erro!");

        return array[--n];
    }

    int remover(int pos) throws Exception {
        if (n == 0 || pos < 0 || pos >= n)
            throw new Exception("Erro!");

        int resp = array[pos];
        n--;

        for (int i = pos; i < n; i++) {
            array[i] = array[i + 1];
        }

        return resp;
    }
    
    void mostrar() {
        System.out.print("[ ");
        for (int i = 0; i < n; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        try {
            Lista lista = new Lista();
            lista.inserirFim(1);
            lista.inserirFim(2);
            lista.inserirFim(3);
            lista.inserirInicio(0);
            lista.inserir(4, 2);
            lista.mostrar();

            System.out.println("Removido do inicio: " + lista.removerInicio());
            System.out.println("Removido do fim: " + lista.removerFim());
            System.out.println("Removido da posicao 1: " + lista.remover(1));
            lista.mostrar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
