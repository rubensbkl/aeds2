class Celula {
    int elemento;
    Celula inf, sup, esq, dir;

    Celula() {
        this(0);
    }

    Celula(int elemento) {
        this(elemento, null, null, null, null);
    }

    Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir) {
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
}

class Matriz {
    private Celula inicio;
    private int linha, coluna;
    
    public Matriz() {
        this(3, 3);
    }
    public Matriz(int i, int c) {
        this.linha = i;
        this.coluna = c;
    }

    Matriz soma(Matriz a, Matriz b) {
        Matriz resp = null;

        if (a.linha == b.linha && a.coluna == b.coluna) {
            resp = new Matriz(a.linha, a.coluna);
            Celula ii = a.inicio, 
                   ji = b.inicio,
                   ki = resp.inicio;
            for ( ; ii != null; ii = ii.inf, ij = ij.inf, ki = ki.inf) {
                Celula i = ii,
                       j = ji,
                       k = ki;
                for ( ; i != null; i = i.dir, j = j.dir, k = k.dir) {
                    k.elemento = i.elemento + j.elemento;
                }
            }
        }
        return resp;
    }
}

class Main {
    public static void main(String[] args) {
        Matriz matriz = new Matriz();
    }
}