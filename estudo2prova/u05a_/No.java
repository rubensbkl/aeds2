class No {
    int elemento;
    No esq;
    No dir;
    No(int elemento) {
        this(elemento, null, null);
    }

    No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}