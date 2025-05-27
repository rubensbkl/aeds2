#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_TITLE_LENGTH 200
#define MAX_GENRE_LENGTH 50
#define MAX_COUNTRY_LENGTH 50
#define MAX_CAST_LENGTH 500
#define MAX_CSV_LINE 1024

// Estrutura para armazenar as informações de cada show
typedef struct {
    char title[MAX_TITLE_LENGTH];
    char type[MAX_TITLE_LENGTH];
    char director[MAX_TITLE_LENGTH];
    char cast[MAX_CAST_LENGTH];
    char country[MAX_COUNTRY_LENGTH];
    char releaseDate[MAX_TITLE_LENGTH];
    int year;
    int duration;  // Duração em minutos
    char classification[MAX_TITLE_LENGTH];
    char genres[MAX_GENRE_LENGTH];
} Show;

// Função para limpar a linha de entrada
void clearInputBuffer() {
    while (getchar() != '\n');
}

// Função para ler a linha e remover o caractere de nova linha
void readLine(char* buffer, int size) {
    fgets(buffer, size, stdin);
    buffer[strcspn(buffer, "\n")] = '\0';  // Remove o '\n' no final da string
}

// Função para fazer o parsing de uma linha CSV
void splitCSVLine(char* line, Show* show) {
    char* token;

    // Primeiro, separa o título
    token = strtok(line, ",");
    if (token) strncpy(show->title, token, MAX_TITLE_LENGTH);

    // Segundo, separa o tipo
    token = strtok(NULL, ",");
    if (token) strncpy(show->type, token, MAX_TITLE_LENGTH);

    // Diretor
    token = strtok(NULL, ",");
    if (token) strncpy(show->director, token, MAX_TITLE_LENGTH);

    // Elenco
    token = strtok(NULL, ",");
    if (token) strncpy(show->cast, token, MAX_CAST_LENGTH);

    // País de origem
    token = strtok(NULL, ",");
    if (token) strncpy(show->country, token, MAX_COUNTRY_LENGTH);

    // Data de lançamento
    token = strtok(NULL, ",");
    if (token) strncpy(show->releaseDate, token, MAX_TITLE_LENGTH);

    // Ano de produção
    token = strtok(NULL, ",");
    if (token) show->year = atoi(token);

    // Duração
    token = strtok(NULL, ",");
    if (token) show->duration = atoi(token);

    // Classificação
    token = strtok(NULL, ",");
    if (token) strncpy(show->classification, token, MAX_TITLE_LENGTH);

    // Gêneros
    token = strtok(NULL, ",");
    if (token) strncpy(show->genres, token, MAX_GENRE_LENGTH);
}

// Função para ler os dados de cada show a partir de um arquivo CSV
void readShowDataFromCSV(FILE* file, Show* show) {
    char line[MAX_CSV_LINE];

    // Lê uma linha do arquivo
    if (fgets(line, sizeof(line), file)) {
        // Faz o parsing da linha lida e preenche o struct
        splitCSVLine(line, show);
    }
}

// Função para imprimir as informações de um show
void printShowData(const Show* show) {
    printf("\n=> sXXX\n");
    printf("Título: %s\n", show->title);
    printf("Tipo: %s\n", show->type);
    printf("Diretor: %s\n", show->director);
    printf("Elenco: %s\n", show->cast);
    printf("País de origem: %s\n", show->country);
    printf("Data de lançamento: %s\n", show->releaseDate);
    printf("Ano de produção: %d\n", show->year);
    printf("Duração: %d minutos\n", show->duration);
    printf("Classificação: %s\n", show->classification);
    printf("Gêneros: %s\n", show->genres);
}

// Função principal
int main() {
    Show show;
    FILE* file = fopen("shows.csv", "r");  // Abre o arquivo CSV para leitura

    if (!file) {
        printf("Erro ao abrir o arquivo CSV.\n");
        return 1;
    }

    // Lê os dados do primeiro show do arquivo CSV
    readShowDataFromCSV(file, &show);

    // Exibe as informações do show com a formatação correta
    printShowData(&show);

    fclose(file);  // Fecha o arquivo após a leitura
    return 0;
}
