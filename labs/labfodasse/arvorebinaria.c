#include "arvorebinaria.h"
#include <err.h>
#include <stdio.h>
#include <stdlib.h>

ArvoreBinaria* start() {
    ArvoreBinaria* arvore = (ArvoreBinaria*) malloc(sizeof(ArvoreBinaria));
    arvore->raiz = NULL;
    return arvore;
}

void inserir(int elemento, ArvoreBinaria* arvore) {
    inserirRec(elemento, arvore->raiz);
}

void inserirRec(int elemento, No* p) {
    if (p == NULL) {
        p = novoNo(elemento);
    } else if (elemento < p->elemento) {
        inserirRec(elemento, p->esq);
    } else if (elemento > p->elemento) {
        inserirRec(elemento, p->dir);
    }
}

void caminharCentral(ArvoreBinaria* arvore) {
    caminharCentralRec(arvore->raiz);
}

void caminharCentralRec(No* p) {
    if (p != NULL) {
        caminharCentralRec(p->esq);
        printf("%d ", p->elemento);
        caminharCentralRec(p->dir);
    }
}

void caminharPre() {
    caminharPreRec(raiz);
}

void caminharPreRec(No* p) {
    if (p != NULL) {
        printf("%d ", p->elemento);
        caminharPreRec(p->esq);
        caminharPreRec(p->dir);
    }
}

void caminharPos() {
    caminharPosRec(raiz);
}

void caminharPosRec(No* p) {
    if (p != NULL) {
        caminharPosRec(p->esq);
        caminharPosRec(p->dir);
        printf("%d ", p->elemento);
    }
}