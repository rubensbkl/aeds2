
class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        this(0);
    }

    public Celula(int x) {
        this.elemento = x;
        this.prox = null;
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
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;
    }

    public void inserirFim(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int removerInicio() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro!");
        Celula tmp = primeiro.prox;
        int elemento = tmp.elemento;
        primeiro.prox = tmp.prox;
        if (tmp == ultimo) {
            ultimo = primeiro;
        }
        tmp.prox = null;
        tmp = null;
        return elemento;
    }

    public int removerFim() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro!");
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);
        int elemento = ultimo.elemento;
        ultimo = i;
        i = ultimo.prox = null;
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
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    public int remover(int pos) throws Exception {
        int elemento, tamanho = tamanho();
        if (primeiro == ultimo || pos < 0 || pos >= tamanho) {
            throw new Exception("Erro!");
        } else if (pos == 0) {
            elemento = removerInicio();
        } else if (pos == tamanho - 1) {
            elemento = removerFim();
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = i.prox;
            elemento = tmp.elemento;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;
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
        Celula anterior = null;
        Celula atual = primeiro.prox;
        Celula proximo = null;
        Celula tmp = primeiro.prox;
        
        while (atual != null) {
            proximo = atual.prox;
            atual.prox = anterior;
            anterior = atual;
            atual = proximo;
        }
        
        primeiro.prox = anterior;
        ultimo = tmp;
    }

    public void quicksort() {
        quicksort(primeiro.prox, ultimo);
    }

    private void quicksort(Celula esq, Celula dir) {
        if (esq == null || dir == null || esq == dir) return;
        
        Celula pivo = particionar(esq, dir);
        
        if (pivo != null && pivo != esq) {
            quicksort(esq, getCelulaAnterior(pivo));
        }
        if (pivo != null && pivo != dir) {
            quicksort(pivo.prox, dir);
        }
    }

    private Celula particionar(Celula esq, Celula dir) {
        int pivoValor = dir.elemento;
        Celula i = getCelulaAnterior(esq);
        
        for (Celula j = esq; j != dir; j = j.prox) {
            if (j.elemento <= pivoValor) {
                i = (i == null) ? esq : i.prox;
                swap(i, j);
            }
        }
        i = (i == null) ? esq : i.prox;
        swap(i, dir);
        return i;
    }

    private void swap(Celula a, Celula b) {
        int temp = a.elemento;
        a.elemento = b.elemento;
        b.elemento = temp;
    }

    private Celula getCelulaAnterior(Celula cel) {
        if (cel == primeiro.prox) return null;
        Celula i = primeiro.prox;
        while (i.prox != cel) {
            i = i.prox;
        }
        return i;
    }
}

public class d_ListaSimples {
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