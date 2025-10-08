#include <stdio.h>
#include <stdlib.h>

typedef struct Celula {
  int elemento;
  struct Celula *prox;
} Celula;

typedef struct Pilha {
  Celula *topo;
} Pilha;

void start(Pilha *p) { p->topo = NULL; }

void inserir(Pilha *p, int x) {
  Celula *tmp = (Celula *)malloc(sizeof(Celula));
  tmp->elemento = x;
  tmp->prox = p->topo;
  p->topo = tmp;
}

int remover(Pilha *p) {
  if (p->topo == NULL) {
    printf("Erro: pilha vazia.\n");
    return -1;
  }
  Celula *temp = p->topo;
  int removido = temp->elemento;
  p->topo = temp->prox;
  free(temp);
  return removido;
}

void mostrar(Pilha *p) {
  Celula *atual = p->topo;
  printf("Pilha: ");
  while (atual != NULL) {
    printf("%d ", atual->elemento);
    atual = atual->prox;
  }
  printf("\n");
}

int main() {
  Pilha p;
  start(&p);

  inserir(&p, 10);
  inserir(&p, 20);
  inserir(&p, 30);

  mostrar(&p);

  printf("Removido: %d\n", remover(&p));
  mostrar(&p);

  return 0;
}