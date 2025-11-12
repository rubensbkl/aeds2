class ArvoreBinaria {
    No raiz;
    ArvoreBinaria() {
        raiz = null;
    }


    void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    No inserir(int x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro!");
        }

        return i;
    }


    void inserirPai(int x) { }
    boolean pesquisar(int x) { }
    void caminharCentral() { }
    void caminharPre() { }
    void caminharPos() { }
    void remover(int x) { }
}