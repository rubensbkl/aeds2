#include <stdbool.h>
#include "no.h"

typedef struct ArvoreBinaria {
    No* raiz;
} ArvoreBinaria;


bool pesquisarRec(int, No*);
void caminharCentralRec(No*);
void caminharPreRec(No*);
void caminharPosRec(No*);
void inserirRec(int, No*);
void removerRec(int, No**);
void maiorEsq(No**, No**);

void start();
bool pesquisar(int);
void caminharCentral();
void caminharPre();
void caminharPos();
void inserir(int);
void remover(int);