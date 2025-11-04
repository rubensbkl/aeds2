#include <stdio.h>
#include "arvorebinaria.h"

int main() {

    ArvoreBinaria* arvore = start();

    inserir(5, arvore);
    inserir(3, arvore);
    inserir(7, arvore);
    inserir(2, arvore);
    inserir(4, arvore);
    inserir(6, arvore);
    inserir(8, arvore);

    printf("Caminhada central: ");
    caminharCentral(arvore);
    printf("\n");

    printf("Caminhada pre-ordem: ");
    caminharPre(arvore);
    printf("\n");

    printf("Caminhada pos-ordem: ");
    caminharPos(arvore);
    printf("\n");


    return 0;
}