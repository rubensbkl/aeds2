/**
 *
 * Pontifícia Universidade Católica de Minas Gerais
 *
 * Curso de Ciência da Computação
 * Algoritmos e Estruturas de Dados II
 *
 * TP06Q02 - Lista com Alocação Flexível em C - v1.0 - 03 / 11 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <time.h>

#define MAX_FIELD 5000
#define MAX_ARRAY 500
#define MAX_STR 500
#define MAX_GAMES 100000

typedef struct {
    int id;
    char nome[MAX_STR];
    char dataLancamento[15];
    int donosEstimados;
    float preco;
    char linguagensSuportadas[MAX_ARRAY][MAX_STR];
    int quantidadeLinguagens;
    int pontuacaoMetacritic;
    float pontuacaoUsuarios;
    int conquistas;
    char editoras[MAX_ARRAY][MAX_STR];
    int quantidadeEditoras;
    char desenvolvedores[MAX_ARRAY][MAX_STR];
    int quantidadeDesenvolvedores;
    char categorias[MAX_ARRAY][MAX_STR];
    int quantidadeCategorias;
    char generos[MAX_ARRAY][MAX_STR];
    int quantidadeGeneros;
    char tags[MAX_ARRAY][MAX_STR];
    int quantidadeTags;
} Jogo;

typedef struct {
    Jogo* jogos[MAX_GAMES];
    int quantidade;
} Biblioteca;

typedef struct Celula {
    Jogo* jogo;
    struct Celula* prox;
} Celula;

typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tamanho;
} Lista;

// Remove espaços e caracteres especiais do início e fim da string
void trim(char* str) {
    int inicio = 0;
    int fim = strlen(str) - 1;
    while (str[inicio] == ' ' || str[inicio] == '\t' || str[inicio] == '\n' || str[inicio] == '\r') inicio++;
    while (fim >= inicio && (str[fim] == ' ' || str[fim] == '\t' || str[fim] == '\n' || str[fim] == '\r')) fim--;
    for (int i = 0; i <= fim - inicio; i++) str[i] = str[inicio + i];
    str[fim - inicio + 1] = '\0';
}

// Converte mês abreviado para número
int numeroDoMes(char* mes) {
    if (strncasecmp(mes, "Jan", 3) == 0) return 1;
    if (strncasecmp(mes, "Feb", 3) == 0) return 2;
    if (strncasecmp(mes, "Mar", 3) == 0) return 3;
    if (strncasecmp(mes, "Apr", 3) == 0) return 4;
    if (strncasecmp(mes, "May", 3) == 0) return 5;
    if (strncasecmp(mes, "Jun", 3) == 0) return 6;
    if (strncasecmp(mes, "Jul", 3) == 0) return 7;
    if (strncasecmp(mes, "Aug", 3) == 0) return 8;
    if (strncasecmp(mes, "Sep", 3) == 0) return 9;
    if (strncasecmp(mes, "Oct", 3) == 0) return 10;
    if (strncasecmp(mes, "Nov", 3) == 0) return 11;
    if (strncasecmp(mes, "Dec", 3) == 0) return 12;
    return 1;
}

// Normaliza a data para formato dd/mm/yyyy
void normalizaData(char* dataOriginal, char* resultado) {
    if (!dataOriginal || strlen(dataOriginal) == 0) {
        strcpy(resultado, "01/01/0001");
        return;
    }
    char mes[20], dia[10], ano[10];
    if (sscanf(dataOriginal, "%s %[^,], %s", mes, dia, ano) == 3) {
        sprintf(resultado, "%02d/%02d/%04d", atoi(dia), numeroDoMes(mes), atoi(ano));
    } else if (sscanf(dataOriginal, "%s %s", mes, ano) == 2) {
        sprintf(resultado, "01/%02d/%04d", numeroDoMes(mes), atoi(ano));
    } else if (strlen(dataOriginal) == 4) {
        sprintf(resultado, "01/01/%s", dataOriginal);
    } else {
        strcpy(resultado, "01/01/0001");
    }
}

// Normaliza número de donos estimados
int normalizaDonos(char* str) {
    if (!str || strlen(str) == 0) return 0;
    char limpo[MAX_STR];
    int j = 0;
    for (int i = 0; str[i]; i++) {
        if ((str[i] >= '0' && str[i] <= '9') || str[i] == '-') limpo[j++] = str[i];
    }
    limpo[j] = '\0';
    char* dash = strchr(limpo, '-');
    if (dash) *dash = '\0';
    return atoi(limpo);
}

// Normaliza preço
float normalizaPreco(char* str) {
    if (!str || strlen(str) == 0 || strcasecmp(str, "Free to Play") == 0) return 0.0;
    for (int i = 0; str[i]; i++) if (str[i] == ',') str[i] = '.';
    return atof(str);
}

// Normaliza score inteiro
int normalizaScoreInt(char* str) { return (str && strlen(str) > 0) ? atoi(str) : -1; }

// Normaliza score float
float normalizaScoreFloat(char* str) {
    if (!str || strlen(str) == 0 || strcasecmp(str, "tbd") == 0) return -1.0;
    for (int i = 0; str[i]; i++) if (str[i] == ',') str[i] = '.';
    return atof(str);
}

// Normaliza listas entre colchetes
int normalizaLista(char* str, char resultado[][MAX_STR]) {
    if (!str || strlen(str) == 0 || strcmp(str, "[]") == 0) return 0;
    int count = 0, i = 0, len = strlen(str);
    while (i < len && count < MAX_ARRAY) {
        while (i < len && (str[i] == ' ' || str[i] == '[' || str[i] == ']' || str[i] == ',')) i++;
        if (i >= len) break;
        int temAspas = (str[i] == '\'');
        if (temAspas) i++;
        int start = i;
        while (i < len && str[i] != (temAspas ? '\'' : ',') && str[i] != ']') i++;
        int size = i - start;
        if (temAspas && i < len && str[i] == '\'') i++;
        if (size > 0) {
            strncpy(resultado[count], &str[start], size);
            resultado[count][size] = '\0';
            trim(resultado[count]);
            if (strlen(resultado[count]) > 0) count++;
        }
    }
    return count;
}

// Normaliza listas separadas por vírgula
int normalizaVirgula(char* str, char resultado[][MAX_STR]) {
    if (!str || strlen(str) == 0) return 0;
    char temp[MAX_FIELD];
    strcpy(temp, str);
    int count = 0;
    char* token = strtok(temp, ",");
    while (token && count < MAX_ARRAY) {
        trim(token);
        if (strlen(token) > 0) strcpy(resultado[count++], token);
        token = strtok(NULL, ",");
    }
    return count;
}

// Separa CSV respeitando aspas
int separaCSV(char* linha, char campos[][MAX_FIELD]) {
    int count = 0, entreAspas = 0, pos = 0;
    for (int i = 0; linha[i] && count < 20; i++) {
        if (linha[i] == '"') {
            if (entreAspas && linha[i + 1] == '"') { campos[count][pos++] = '"'; i++; }
            else entreAspas = !entreAspas;
        } else if (linha[i] == ',' && !entreAspas) {
            campos[count][pos] = '\0';
            trim(campos[count]);
            count++; pos = 0;
        } else campos[count][pos++] = linha[i];
    }
    campos[count][pos] = '\0';
    trim(campos[count]);
    return count + 1;
}

// Converte linha CSV em struct Jogo
Jogo* parseJogo(char* linha) {
    char campos[20][MAX_FIELD];
    if (separaCSV(linha, campos) < 14) return NULL;
    Jogo* j = (Jogo*)malloc(sizeof(Jogo));
    if (!j) return NULL;
    j->id = atoi(campos[0]);
    strcpy(j->nome, campos[1]);
    normalizaData(campos[2], j->dataLancamento);
    j->donosEstimados = normalizaDonos(campos[3]);
    j->preco = normalizaPreco(campos[4]);
    j->quantidadeLinguagens = normalizaLista(campos[5], j->linguagensSuportadas);
    j->pontuacaoMetacritic = normalizaScoreInt(campos[6]);
    j->pontuacaoUsuarios = normalizaScoreFloat(campos[7]);
    j->conquistas = normalizaScoreInt(campos[8]);
    j->quantidadeEditoras = normalizaVirgula(campos[9], j->editoras);
    j->quantidadeDesenvolvedores = normalizaVirgula(campos[10], j->desenvolvedores);
    j->quantidadeCategorias = normalizaLista(campos[11], j->categorias);
    j->quantidadeGeneros = normalizaLista(campos[12], j->generos);
    j->quantidadeTags = normalizaLista(campos[13], j->tags);
    return j;
}

// Formata arrays para impressão
void formataArray(char* resultado, char array[][MAX_STR], int tamanho) {
    if (tamanho == 0) { strcpy(resultado, "[]"); return; }
    strcpy(resultado, "[");
    for (int i = 0; i < tamanho; i++) {
        strcat(resultado, array[i]);
        if (i < tamanho - 1) strcat(resultado, ", ");
    }
    strcat(resultado, "]");
}

// Imprime um jogo formatado
void imprimeJogo(Jogo* j) {
    char lang[MAX_FIELD], pub[MAX_FIELD], dev[MAX_FIELD];
    char cat[MAX_FIELD], gen[MAX_FIELD], tag[MAX_FIELD];
    formataArray(lang, j->linguagensSuportadas, j->quantidadeLinguagens);
    formataArray(pub, j->editoras, j->quantidadeEditoras);
    formataArray(dev, j->desenvolvedores, j->quantidadeDesenvolvedores);
    formataArray(cat, j->categorias, j->quantidadeCategorias);
    formataArray(gen, j->generos, j->quantidadeGeneros);
    formataArray(tag, j->tags, j->quantidadeTags);
    char precoStr[20];
    if (j->preco == (int)j->preco) sprintf(precoStr, "%d.0", (int)j->preco);
    else {
        sprintf(precoStr, "%.2f", j->preco);
        int len = strlen(precoStr);
        while (len > 1 && precoStr[len-1] == '0' && precoStr[len-2] != '.') precoStr[--len] = '\0';
    }
    printf("=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##\n",
           j->id, j->nome, j->dataLancamento, j->donosEstimados, precoStr,
           lang, j->pontuacaoMetacritic, j->pontuacaoUsuarios, j->conquistas,
           pub, dev, cat, gen, tag);
}

// Inicializa biblioteca
void initBiblioteca(Biblioteca* lib) { lib->quantidade = 0; }

// Adiciona jogo à biblioteca
void adicionaJogo(Biblioteca* lib, Jogo* j) { 
    if (j && lib->quantidade < MAX_GAMES) lib->jogos[lib->quantidade++] = j; 
}

// Busca jogo por ID
Jogo* buscaJogo(Biblioteca* lib, int id) {
    for (int i = 0; i < lib->quantidade; i++) if (lib->jogos[i]->id == id) return lib->jogos[i];
    return NULL;
}

// Carrega CSV na biblioteca
void carregarCSV(Biblioteca* lib, char* caminho) {
    FILE* f = fopen(caminho, "r");
    if (!f) return;
    char linha[MAX_FIELD * 5];
    fgets(linha, sizeof(linha), f); 
    while (fgets(linha, sizeof(linha), f)) {
        Jogo* j = parseJogo(linha);
        if (j) adicionaJogo(lib, j);
    }
    fclose(f);
}

// Libera memória da biblioteca
void liberaBiblioteca(Biblioteca* lib) { 
    for (int i = 0; i < lib->quantidade; i++) free(lib->jogos[i]); 
}

// Selection Sort pelo nome
void selectionSort(Jogo* arr[], int n, int* comparacoes, int* movimentacoes) {
    for (int i = 0; i < n - 1; i++) {
        int min = i;
        for (int j = i + 1; j < n; j++) {
            (*comparacoes)++;
            if (strcmp(arr[j]->nome, arr[min]->nome) < 0) 
                min = j;
        }
        if (min != i) {
            Jogo* temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
            (*movimentacoes) += 3;
        }
    }
}

// LISTA

// Inicializa lista
void initLista(Lista* l) {
    l->primeiro = (Celula*)malloc(sizeof(Celula));
    l->primeiro->prox = NULL;
    l->ultimo = l->primeiro;
    l->tamanho = 0;
}

// Insere no início
void inserirInicio(Lista* l, Jogo* j) {
    Celula* tmp = (Celula*)malloc(sizeof(Celula));
    tmp->jogo = j;
    tmp->prox = l->primeiro->prox;
    l->primeiro->prox = tmp;
    if (l->tamanho == 0) l->ultimo = tmp;
    l->tamanho++;
}

// Insere no fim
void inserirFim(Lista* l, Jogo* j) {
    Celula* tmp = (Celula*)malloc(sizeof(Celula));
    tmp->jogo = j;
    tmp->prox = NULL;
    l->ultimo->prox = tmp;
    l->ultimo = tmp;
    l->tamanho++;
}

// Insere em posição
void inserirPos(Lista* l, Jogo* j, int pos) {
    if (pos < 0 || pos > l->tamanho) {
        printf("Erro ao inserir!\n");
        exit(1);
    }
    if (pos == 0) return inserirInicio(l, j);
    if (pos == l->tamanho) return inserirFim(l, j);
    Celula* ant = l->primeiro;
    for (int i = 0; i < pos; i++) ant = ant->prox;
    Celula* tmp = (Celula*)malloc(sizeof(Celula));
    tmp->jogo = j;
    tmp->prox = ant->prox;
    ant->prox = tmp;
    l->tamanho++;
}

// Remove do início
Jogo* removerInicio(Lista* l) {
    if (l->tamanho == 0) {
        printf("Erro ao remover!\n");
        exit(1);
    }
    Celula* tmp = l->primeiro->prox;
    Jogo* resp = tmp->jogo;
    l->primeiro->prox = tmp->prox;
    if (l->primeiro->prox == NULL) l->ultimo = l->primeiro;
    free(tmp);
    l->tamanho--;
    return resp;
}

// Remove do fim
Jogo* removerFim(Lista* l) {
    if (l->tamanho == 0) {
        printf("Erro ao remover!\n");
        exit(1);
    }
    Celula* i = l->primeiro;
    while (i->prox != l->ultimo) i = i->prox;
    Jogo* resp = l->ultimo->jogo;
    free(l->ultimo);
    l->ultimo = i;
    l->ultimo->prox = NULL;
    l->tamanho--;
    return resp;
}

// Remove em posição
Jogo* removerPos(Lista* l, int pos) {
    if (l->tamanho == 0 || pos < 0 || pos >= l->tamanho) {
        printf("Erro ao remover!\n");
        exit(1);
    }
    if (pos == 0) return removerInicio(l);
    if (pos == l->tamanho - 1) return removerFim(l);
    Celula* ant = l->primeiro;
    for (int i = 0; i < pos; i++) ant = ant->prox;
    Celula* tmp = ant->prox;
    Jogo* resp = tmp->jogo;
    ant->prox = tmp->prox;
    free(tmp);
    l->tamanho--;
    return resp;
}

// Mostrar lista
void mostrar(Lista* l) {
    int idx = 0;
    for (Celula* i = l->primeiro->prox; i != NULL; i = i->prox) {
        printf("[%d] ", idx++);
        imprimeJogo(i->jogo);
    }
}


// MAIN

int main() {
    Biblioteca biblioteca;
    initBiblioteca(&biblioteca);
    carregarCSV(&biblioteca, "/tmp/games.csv");

    Lista lista;
    initLista(&lista);

    char entrada[100];
    while (fgets(entrada, sizeof(entrada), stdin)) {
        trim(entrada);
        if (strcasecmp(entrada, "FIM") == 0) break;
        int id = atoi(entrada);
        Jogo* jogo = buscaJogo(&biblioteca, id);
        if (jogo) inserirFim(&lista, jogo);
    }

    int n;
    scanf("%d\n", &n);
    for (int i = 0; i < n; i++) {
        char comando[10];
        fgets(entrada, sizeof(entrada), stdin);
        trim(entrada);

        if (sscanf(entrada, "%s", comando) == 1) {
            if (strcmp(comando, "II") == 0) {
                int id;
                sscanf(entrada, "II %d", &id);
                inserirInicio(&lista, buscaJogo(&biblioteca, id));
            } else if (strcmp(comando, "IF") == 0) {
                int id;
                sscanf(entrada, "IF %d", &id);
                inserirFim(&lista, buscaJogo(&biblioteca, id));
            } else if (strcmp(comando, "I*") == 0) {
                int pos, id;
                sscanf(entrada, "I* %d %d", &pos, &id);
                inserirPos(&lista, buscaJogo(&biblioteca, id), pos);
            } else if (strcmp(comando, "RI") == 0) {
                Jogo* removido = removerInicio(&lista);
                printf("(R) %s\n", removido->nome);
            } else if (strcmp(comando, "RF") == 0) {
                Jogo* removido = removerFim(&lista);
                printf("(R) %s\n", removido->nome);
            } else if (strcmp(comando, "R*") == 0) {
                int pos;
                sscanf(entrada, "R* %d", &pos);
                Jogo* removido = removerPos(&lista, pos);
                printf("(R) %s\n", removido->nome);
            }
        }
    }

    mostrar(&lista);

    liberaBiblioteca(&biblioteca);
    return 0;
}
