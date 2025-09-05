#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// ---------- PESQUISA SEQUENCIAL ----------
int pesquisaSequencial(int *array, int n, int key)
{
  for (int i = 0; i < n; i++)
  {
    if (array[i] == key)
    {
      return i;
    }
  }
  return -1;
}

// ---------- PESQUISA BINÁRIA (requer array ordenado) ----------
int pesquisaBinaria(int *array, int n, int key)
{
  int esq = 0, dir = n - 1;
  while (esq <= dir)
  {
    int meio = (esq + dir) / 2;
    if (array[meio] == key)
      return meio;
    else if (array[meio] < key)
      esq = meio + 1;
    else
      dir = meio - 1;
  }
  return -1;
}

// ---------- ORDENAÇÃO SELEÇÃO ----------
void selecao(int *array, int n)
{
  for (int i = 0; i < n - 1; i++)
  {
    int menor = i;
    for (int j = i + 1; j < n; j++)
    {
      if (array[j] < array[menor])
      {
        menor = j;
      }
    }
    int tmp = array[i];
    array[i] = array[menor];
    array[menor] = tmp;
  }
}

// ---------- ORDENAÇÃO INSERÇÃO ----------
void insercao(int *array, int n)
{
  for (int i = 1; i < n; i++)
  {
    int tmp = array[i];
    int j = i - 1;
    while (j >= 0 && array[j] > tmp)
    {
      array[j + 1] = array[j];
      j--;
    }
    array[j + 1] = tmp;
  }
}

// ---------- ORDENAÇÃO QUICKSORT ----------
void quicksort(int *array, int esq, int dir)
{
  int i = esq, j = dir;
  int pivo = array[(esq + dir) / 2];
  while (i <= j)
  {
    while (array[i] < pivo)
      i++;
    while (array[j] > pivo)
      j--;
    if (i <= j)
    {
      int tmp = array[i];
      array[i] = array[j];
      array[j] = tmp;
      i++;
      j--;
    }
  }
  if (esq < j)
    quicksort(array, esq, j);
  if (i < dir)
    quicksort(array, i, dir);
}

// ---------- FUNÇÃO AUXILIAR ----------
void copiarArray(int *orig, int *dest, int n)
{
  for (int i = 0; i < n; i++)
    dest[i] = orig[i];
}

// ---------- PROGRAMA PRINCIPAL ----------
int main()
{
  int n;
  printf("Digite o número de elementos: ");
  scanf("%d", &n);

  int *array = (int *)malloc(n * sizeof(int));
  int *tmp = (int *)malloc(n * sizeof(int));
  if (!array || !tmp)
  {
    fprintf(stderr, "Erro de alocação\n");
    return 1;
  }

  srand(time(NULL));
  for (int i = 0; i < n; i++)
  {
    array[i] = rand() % (n * 10); // números aleatórios
  }

  clock_t inicio, fim;
  double tempo;

  // ---------- TESTE ORDENAÇÃO ----------
  copiarArray(array, tmp, n);
  inicio = clock();
  selecao(tmp, n);
  fim = clock();
  tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;
  printf("Tempo Ordenacao Selecao: %.6f s\n", tempo);

  copiarArray(array, tmp, n);
  inicio = clock();
  insercao(tmp, n);
  fim = clock();
  tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;
  printf("Tempo Ordenacao Insercao: %.6f s\n", tempo);

  copiarArray(array, tmp, n);
  inicio = clock();
  quicksort(tmp, 0, n - 1);
  fim = clock();
  tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;
  printf("Tempo Ordenacao Quicksort: %.6f s\n", tempo);

  // ---------- TESTE PESQUISA ----------
  int key = array[rand() % n]; // escolhe um elemento que existe
  inicio = clock();
  pesquisaSequencial(array, n, key);
  fim = clock();
  tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;
  printf("Tempo Pesquisa Sequencial: %.6f s\n", tempo);

  quicksort(array, 0, n - 1); // ordena para pesquisa binária
  inicio = clock();
  pesquisaBinaria(array, n, key);
  fim = clock();
  tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;
  printf("Tempo Pesquisa Binaria: %.6f s\n", tempo);

  free(array);
  free(tmp);
  return 0;
}
