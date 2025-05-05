#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Definindo constantes para tamanho máximo de shows e linha do CSV
#define MAX_SHOWS 10000
#define MAX_LINE 1000

// Estrutura que representa um show (filme ou série)
typedef struct {
    char *show_id;
    char *type;
    char *title;
    char *director;
    char **cast; // Vetor de strings (nomes dos atores)
    int cast_size;
    char *country;
    char *date_added;
    int release_year;
    char *rating;
    char *duration;
    char **listed_in; // Vetor de strings (gêneros)
    int listed_in_size;
} Show;

// Função auxiliar que retorna "NaN" caso o campo esteja vazio ou nulo
char *getOrNaN(char *field) {
    if (field == NULL || strlen(field) == 0) {
        return strdup("NaN");
    }
    return strdup(field);
}

// Função que divide uma string separada por vírgulas em um vetor de strings
char **splitList(const char *str, int *size) {
    if (strcmp(str, "NaN") == 0) {
        *size = 1;
        char **res = malloc(sizeof(char*));
        res[0] = strdup("NaN");
        return res;
    }

    char *copy = strdup(str);
    int count = 1;

    // Conta quantos elementos a lista possui
    for (char *c = copy; *c; c++) {
        if (*c == ',') count++;
    }

    char **res = malloc(count * sizeof(char*));
    char *token = strtok(copy, ",");
    int i = 0;

    // Quebra a string em tokens e remove espaços à esquerda
    while (token) {
        while (*token == ' ') token++;
        res[i++] = strdup(token);
        token = strtok(NULL, ",");
    }
    *size = i;
    free(copy);
    return res;
}

// Função para ordenar alfabeticamente uma lista de strings
void sortList(char **list, int size) {
    for (int i = 0; i < size-1; i++) {
        for (int j = i+1; j < size; j++) {
            if (strcmp(list[i], list[j]) > 0) {
                char *tmp = list[i];
                list[i] = list[j];
                list[j] = tmp;
            }
        }
    }
}

// Função que imprime um show no formato solicitado
void imprimir(Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_size; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_in_size; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_in_size - 1) printf(", ");
    }
    printf("] ##\n");
}

// Função que faz o parsing de uma linha do CSV e preenche os campos do struct Show
void ler(Show *s, char *linha) {
    char *campos[11];
    int idx = 0;
    char *token = malloc(strlen(linha) + 1);
    int inQuotes = 0;
    int j = 0;
    int i;

    // Parsing manual da linha, respeitando campos entre aspas
    for (i = 0; linha[i]; i++) {
        if (linha[i] == '"') {
            inQuotes = !inQuotes;
        } else if (linha[i] == ',' && !inQuotes) {
            token[j] = '\0';
            if (idx < 11) {
                campos[idx++] = strdup(token);
            }
            j = 0;
        } else {
            token[j++] = linha[i];
        }
    }

    token[j] = '\0';
    if (idx < 11) {
        campos[idx++] = strdup(token);
    }
    free(token);

    // Preenche os campos faltantes com "NaN"
    while (idx < 11) {
        campos[idx++] = strdup("NaN");
    }

    // Preenchendo o struct com os dados tratados
    s->show_id = getOrNaN(campos[0]);
    s->type = getOrNaN(campos[1]);
    s->title = getOrNaN(campos[2]);
    s->director = getOrNaN(campos[3]);

    s->cast = splitList(getOrNaN(campos[4]), &s->cast_size);
    sortList(s->cast, s->cast_size); // Ordena o elenco

    s->country = getOrNaN(campos[5]);
    s->date_added = getOrNaN(campos[6]);

    s->release_year = -1;
    if (campos[7] && isdigit(campos[7][0])) {
        s->release_year = atoi(campos[7]);
    }

    s->rating = getOrNaN(campos[8]);
    s->duration = getOrNaN(campos[9]);

    s->listed_in = splitList(getOrNaN(campos[10]), &s->listed_in_size);

    for (i = 0; i < 11; i++) {
        free(campos[i]);
    }
}

// Libera toda a memória alocada para um show
void freeShow(Show *s) {
    free(s->show_id); free(s->type); free(s->title); free(s->director);
    free(s->country); free(s->date_added); free(s->rating); free(s->duration);

    for (int i = 0; i < s->cast_size; i++) {
        free(s->cast[i]);
    }
    free(s->cast);

    for (int i = 0; i < s->listed_in_size; i++) {
        free(s->listed_in[i]);
    }
    free(s->listed_in);
}

int main() {
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) {
        perror("Erro ao abrir arquivo");
        return 1;
    }

    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, fp); // Ignora cabeçalho

    char *ids[MAX_SHOWS]; // IDs de cada show para facilitar busca
    char *linhas[MAX_SHOWS]; // Armazena todas as linhas do CSV
    int total = 0;

    // Leitura de todas as linhas do arquivo e armazenamento na memória
    while (fgets(linha, MAX_LINE, fp)) {
        linha[strcspn(linha, "\r\n")] = 0; // Remove quebras de linha
        char *linhaCpy = strdup(linha);
        char *id = strtok(linhaCpy, ",");
        ids[total] = strdup(id); // Salva apenas o ID para busca
        linhas[total] = strdup(linha); // Salva a linha completa
        total++;
        free(linhaCpy);
    }
    fclose(fp);

    // Loop principal para ler entradas do usuário
    char entrada[100];
    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0) break;

        int achou = 0;

        // Busca o ID digitado nas linhas lidas
        for (int i = 0; i < total; i++) {
            if (strcmp(ids[i], entrada) == 0) {
                Show s;
                ler(&s, linhas[i]); // Lê os dados do show
                imprimir(&s); // Imprime no formato especificado
                freeShow(&s); // Libera a memória usada
                achou = 1;
                break;
            }
        }

        if (!achou) {
            printf("Show ID não encontrado: %s\n", entrada);
        }
    }

    // Liberação de memória alocada para as linhas e IDs
    for (int i = 0; i < total; i++) {
        free(ids[i]);
        free(linhas[i]);
    }

    return 0;
}
