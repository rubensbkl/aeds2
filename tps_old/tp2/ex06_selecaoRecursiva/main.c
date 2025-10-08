#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SHOWS 3000
#define MAX_LINHA 1000
#define MAX_ID 20
#define MAX_SELECIONADOS 1000

typedef struct {
    char id[MAX_ID];
    char title[300];
    char date_added[50];
    int release_year;
    char rating[10];
    char duration[20];
    char genre[100];
    char description[300];
} Show;

char *trim(char *str) {
    char *end;
    while (*str == ' ' || *str == '\t' || *str == '\n') str++;
    if (*str == 0) return str;
    end = str + strlen(str) - 1;
    while (end > str && (*end == ' ' || *end == '\t' || *end == '\n')) end--;
    *(end + 1) = 0;
    return str;
}

void ler(Show *s, char *linha) {
    char *token;
    token = strtok(linha, ",");

    strcpy(s->id, token ? token : "");

    token = strtok(NULL, ",");
    strcpy(s->title, token ? token : "");

    token = strtok(NULL, ",");
    strcpy(s->date_added, token ? token : "");

    token = strtok(NULL, ",");
    s->release_year = token ? atoi(token) : 0;

    token = strtok(NULL, ",");
    strcpy(s->rating, token ? token : "");

    token = strtok(NULL, ",");
    strcpy(s->duration, token ? token : "");

    token = strtok(NULL, ",");
    strcpy(s->genre, token ? token : "");

    token = strtok(NULL, ",");
    strcpy(s->description, token ? token : "");
}

void imprimir(Show *s) {
    printf("%s %s %s %d %s %s %s %s\n", s->id, s->title, s->date_added,
           s->release_year, s->rating, s->duration, s->genre, s->description);
}

// === ORDENACAO POR SELECAO RECURSIVA ===

int comparacoes = 0;

void swapShows(Show *a, Show *b) {
    Show temp = *a;
    *a = *b;
    *b = temp;
}

int encontrarMinIndice(Show arr[], int i, int n) {
    if (i == n - 1) return i;
    int minIndex = encontrarMinIndice(arr, i + 1, n);
    comparacoes++;
    return (strcmp(arr[i].title, arr[minIndex].title) < 0) ? i : minIndex;
}

void selecaoRecursiva(Show arr[], int n, int i) {
    if (i >= n - 1) return;
    int minIndex = encontrarMinIndice(arr, i, n);
    if (minIndex != i) {
        swapShows(&arr[i], &arr[minIndex]);
    }
    selecaoRecursiva(arr, n, i + 1);
}

// === MAIN ===

int main() {
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) {
        printf("Erro ao abrir arquivo CSV.\n");
        return 1;
    }

    char linhas[MAX_SHOWS][MAX_LINHA];
    char ids[MAX_SHOWS][MAX_ID];
    int total = 0;

    char linha[MAX_LINHA];
    fgets(linha, MAX_LINHA, fp); // pular cabeçalho

    while (fgets(linha, MAX_LINHA, fp)) {
        linha[strcspn(linha, "\n")] = 0;
        strcpy(linhas[total], linha);

        char tempLinha[MAX_LINHA];
        strcpy(tempLinha, linha);
        char *id = strtok(tempLinha, ",");
        strcpy(ids[total], id ? id : "");
        total++;
    }

    fclose(fp);

    // === LEITURA DOS IDS ===
    char entrada[MAX_ID];
    Show selecionados[MAX_SELECIONADOS];
    int numSelecionados = 0;

    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0) break;

        for (int i = 0; i < total; i++) {
            if (strcmp(ids[i], entrada) == 0) {
                ler(&selecionados[numSelecionados], linhas[i]);
                numSelecionados++;
                break;
            }
        }
    }

    // === ORDENAR ===
    clock_t inicio = clock();
    selecaoRecursiva(selecionados, numSelecionados, 0);
    clock_t fim = clock();

    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0;

    // === IMPRIMIR ORDENADOS ===
    for (int i = 0; i < numSelecionados; i++) {
        imprimir(&selecionados[i]);
    }

    // === LOG ===
    FILE *log = fopen("855796_selecaoRecursiva.txt", "w");
    if (log) {
        fprintf(log, "855796\t%.0lfms\t%d\n", tempo, comparacoes);
        fclose(log);
    } else {
        perror("Erro ao criar o arquivo de log");
    }

    return 0;
}
