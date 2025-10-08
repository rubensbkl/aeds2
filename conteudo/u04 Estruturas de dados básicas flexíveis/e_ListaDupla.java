
class Celula {
    public int elemento;
    public Celula prox, ant;

    public Celula() {
        this(0);
    }

    public Celula(int x) {
        this.elemento = x;
        this.prox = this.ant = null;
    }
}

class Lista {
    private Celula primeiro, ultimo;

    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox, tamanho++);
        return tamanho;
    }

    public void inserirInicio(int x) {
        Celula tmp = new Celula(x);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        } else {
            tmp.prox.ant = tmp;
        }
        tmp = null;
    }

    public void inserirFim(int x) {
        ultimo.prox = new Celula(x);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    public int removerInicio() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro!");
        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        int elemento = primeiro.elemento;
        tmp.prox = primeiro.ant = null;
        tmp = null;
        return elemento;
    }

    public int removerFim() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro!");
        int elemento = ultimo.elemento;
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
        return elemento;
    }

    public void inserir(int x, int pos) throws Exception {
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho) {
            throw new Exception("Erro!");
        } else if (pos == 0) {
            inserirInicio(x);
        } else if (pos == tamanho) {
            inserirFim(x);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = new Celula(x);
            tmp.ant = i;
            tmp.prox = i.prox;
            tmp.ant.prox = tmp.prox.ant = tmp;
            tmp = i = null;
        }
    }

    public int remover(int pos) throws Exception {
        int elemento, tamanho = tamanho();
        if (primeiro == ultimo) {
            throw new Exception("Erro!");
        } else if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro!");
        } else if (pos == 0) {
            elemento = removerInicio();
        } else if (pos == tamanho - 1) {
            elemento = removerFim();
        } else {
            Celula i = primeiro.prox;
            for (int j = 0; j < pos; j++, i = i.prox);
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            elemento = i.elemento;
            i.prox = i.ant = null;
            i = null;
        }
        return elemento;
    }

    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public void inverte() {
        Celula i = primeiro.prox, j = ultimo;
        while (i != j && j.prox != i) {
            int temp = i.elemento;
            i.elemento = j.elemento;
            j.elemento = temp;
            i = i.prox;
            j = j.ant;
        }
    }

    public void quicksort() {
        quicksort(primeiro.prox, ultimo);
    }

    private void quicksort(Celula esq, Celula dir) {
        if (esq == null || dir == null || esq == dir) return;
        
        Celula temp = esq;
        while (temp != null && temp != dir) {
            temp = temp.prox;
        }
        if (temp == null) return;
        
        Celula pivo = particionar(esq, dir);
        
        if (pivo != null && pivo.ant != null && pivo != esq) {
            quicksort(esq, pivo.ant);
        }
        if (pivo != null && pivo.prox != null && pivo != dir) {
            quicksort(pivo.prox, dir);
        }
    }

    private Celula particionar(Celula esq, Celula dir) {
        int pivoValor = dir.elemento;
        Celula i = esq.ant;
        
        for (Celula j = esq; j != dir; j = j.prox) {
            if (j.elemento <= pivoValor) {
                i = (i == null || i == primeiro) ? esq : i.prox;
                swap(i, j);
            }
        }
        i = (i == null || i == primeiro) ? esq : i.prox;
        swap(i, dir);
        return i;
    }

    private void swap(Celula a, Celula b) {
        int temp = a.elemento;
        a.elemento = b.elemento;
        b.elemento = temp;
    }
}

public class e_ListaDupla {
    public static void main(String[] args) {
        Lista listaSimples = new Lista();

        listaSimples.inserirFim(20);
        listaSimples.inserirFim(10);
        listaSimples.inserirFim(5);
        listaSimples.mostrar();
        listaSimples.quicksort();
        listaSimples.mostrar();
    }
}