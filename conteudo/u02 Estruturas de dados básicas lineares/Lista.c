#include <stdio.h>
#include <stdlib.h>

#define MAXTAM 100

int array[MAXTAM];
int n;

void start() {
	n = 0;
}

void inserirInicio(int x) {
    if (n >= MAXTAM)
        exit(1);

    for (int i = n; i > 0; i--) {
        array[i] = array[i - 1];
    }

    array[0] = x;
    n++;
}

void inserirFim(int x) {
    if (n >= MAXTAM)
        exit(1);

    array[n] = x;
    n++;
}

void inserir(int x, int pos) {
    if (n >= MAXTAM || pos < 0 || pos > n)
        exit(1);

    for (int i = n; i > pos; i--) {
        array[i] = array[i - 1];
    }

    array[pos] = x;
    n++;
}

int removerInicio() {
    if (n == 0)
        exit(1);

    int resp = array[0];
    n--;

    for (int i = 0; i < n; i++) {
        array[i] = array[i + 1]; 
    }

    return resp;
}

int removerFim() {
    if (n == 0)
        exit(1);

    return array[--n];
}

int remover(int pos) {
    if (n == 0 || pos < 0 || pos >= n)
        exit(1);

    int resp = array[pos];
    n--;

    for (int i = pos; i < n; i++) {
        array[i] = array[i + 1];
    }

    return resp;
}

void mostrar() {
    printf("[ ");
    for (int i = 0; i < n; i++) {
        printf("%d ", array[i]);
    }
    printf("]");
}
     
int main() {
    start();
    inserirFim(1);
    inserirFim(2);
    inserirFim(3);
    inserirInicio(0);
    inserir(4, 2);

    mostrar();
    printf("\n");

    printf("Removido do inicio: %d\n", removerInicio());
    printf("Removido do fim: %d\n", removerFim());
    printf("Removido da posicao 1: %d\n", remover(1));

    mostrar();
    printf("\n");

    return 0;
}