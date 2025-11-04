typedef struct No {
    int elemento;
    struct No *esq;
    struct No *dir;
} No;

No* novoNo(int);