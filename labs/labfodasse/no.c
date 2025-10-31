#include <stdlib.h>
#include "no.h"

No* novoNo(int elemento) {
    No* novo = (No*) malloc(sizeof(No));
    novo->elemento = elemento;
    novo->esq = NULL;
    novo->dir = NULL;
    return novo;
}