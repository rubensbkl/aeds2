/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimos e Estruturas de Dados II
 *
 * TP05Q03 - Quicksort em C refatorado
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <time.h>

#define MAX_CAMPO 5000
#define MAX_ARRAY 500
#define MAX_STR 500
#define MAX_JOGOS 100000

// Classe game
typedef struct {
    int id;
    char nome[MAX_STR];
    char dataLancamento[15];
    int donosEstimados;
    float preco;
    char idiomasSuportados[MAX_ARRAY][MAX_STR];
    int qtdIdiomas;
    int notaMetacritic;
    float notaUsuarios;
    int conquistas;
    char editoras[MAX_ARRAY][MAX_STR];
    int qtdEditoras;
    char desenvolvedores[MAX_ARRAY][MAX_STR];
    int qtdDesenvolvedores;
    char categorias[MAX_ARRAY][MAX_STR];
    int qtdCategorias;
    char generos[MAX_ARRAY][MAX_STR];
    int qtdGeneros;
    char tags[MAX_ARRAY][MAX_STR];
    int qtdTags;
} Jogo;

// Classe biblioteca
typedef struct {
    Jogo* jogos[MAX_JOGOS];
    int qtdJogos;
} Biblioteca;

// Remove espaços no início e no fim da string
void trim(char* str) {
    int inicio = 0;
    int fim = strlen(str) - 1;
    while (str[inicio] == ' ' || str[inicio] == '\t' || str[inicio] == '\n' || str[inicio] == '\r') inicio++;
    while (fim >= inicio && (str[fim] == ' ' || str[fim] == '\t' || str[fim] == '\n' || str[fim] == '\r')) fim--;
    for (int i = 0; i <= fim - inicio; i++) str[i] = str[inicio + i];
    str[fim - inicio + 1] = '\0';
}

// Retorna o número do mês a partir da abreviação
int getNumeroMes(char* mes) {
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

// Normaliza datas para o formato dd/mm/aaaa
void normalizarData(char* dataStr, char* resultado) {
    if (!dataStr || strlen(dataStr) == 0) {
        strcpy(resultado, "01/01/0001");
        return;
    }
    char mes[20], dia[10], ano[10];
    if (sscanf(dataStr, "%s %[^,], %s", mes, dia, ano) == 3)
        sprintf(resultado, "%02d/%02d/%04d", atoi(dia), getNumeroMes(mes), atoi(ano));
    else if (sscanf(dataStr, "%s %s", mes, ano) == 2)
        sprintf(resultado, "01/%02d/%04d", getNumeroMes(mes), atoi(ano));
    else if (strlen(dataStr) == 4)
        sprintf(resultado, "01/01/%s", dataStr);
    else
        strcpy(resultado, "01/01/0001");
}

// Normaliza quantidade de donos estimados
int normalizarDonos(char* str) {
    if (!str || strlen(str) == 0) return 0;
    char limpo[MAX_STR]; int j = 0;
    for (int i = 0; str[i]; i++)
        if ((str[i] >= '0' && str[i] <= '9') || str[i] == '-') limpo[j++] = str[i];
    limpo[j] = '\0';
    char* dash = strchr(limpo, '-'); if (dash) *dash = '\0';
    return atoi(limpo);
}

// Normaliza preço do jogo
float normalizarPreco(char* str) {
    if (!str || strlen(str) == 0 || strcasecmp(str, "Free to Play") == 0) return 0.0;
    for (int i = 0; str[i]; i++) if (str[i] == ',') str[i] = '.';
    return atof(str);
}

// Normaliza notas inteiras
int normalizarNotaInt(char* str) { return (str && strlen(str) > 0) ? atoi(str) : -1; }

// Normaliza notas de ponto flutuante
float normalizarNotaFloat(char* str) {
    if (!str || strlen(str) == 0 || strcasecmp(str, "tbd") == 0) return -1.0;
    for (int i = 0; str[i]; i++) if (str[i] == ',') str[i] = '.';
    return atof(str);
}

// Normaliza listas com colchetes ['a', 'b']
int normalizarLista(char* str, char resultado[][MAX_STR]) {
    if (!str || strlen(str) == 0 || strcmp(str, "[]") == 0) return 0;
    int qtd = 0, i = 0, len = strlen(str);
    while (i < len && qtd < MAX_ARRAY) {
        while (i < len && (str[i] == ' ' || str[i] == '[' || str[i] == ']' || str[i] == ',')) i++;
        if (i >= len) break;
        int temAspas = (str[i] == '\''); if (temAspas) i++;
        int inicio = i;
        while (i < len && str[i] != (temAspas ? '\'' : ',') && str[i] != ']') i++;
        int tamanho = i - inicio;
        if (temAspas && i < len && str[i] == '\'') i++;
        if (tamanho > 0) {
            strncpy(resultado[qtd], &str[inicio], tamanho);
            resultado[qtd][tamanho] = '\0';
            trim(resultado[qtd]);
            if (strlen(resultado[qtd]) > 0) qtd++;
        }
    }
    return qtd;
}

// Normaliza listas separadas por vírgula
int normalizarVirgula(char* str, char resultado[][MAX_STR]) {
    if (!str || strlen(str) == 0) return 0;
    char temp[MAX_CAMPO]; strcpy(temp, str);
    int qtd = 0;
    char* token = strtok(temp, ",");
    while (token && qtd < MAX_ARRAY) {
        trim(token);
        if (strlen(token) > 0) strcpy(resultado[qtd++], token);
        token = strtok(NULL, ",");
    }
    return qtd;
}

// Separa uma linha CSV em campos
int separarCSV(char* linha, char campos[][MAX_CAMPO]) {
    int qtd = 0, dentroAspas = 0, pos = 0;
    for (int i = 0; linha[i] && qtd < 20; i++) {
        if (linha[i] == '"') {
            if (dentroAspas && linha[i + 1] == '"') { campos[qtd][pos++] = '"'; i++; }
            else dentroAspas = !dentroAspas;
        } else if (linha[i] == ',' && !dentroAspas) {
            campos[qtd][pos] = '\0'; trim(campos[qtd]); qtd++; pos = 0;
        } else campos[qtd][pos++] = linha[i];
    }
    campos[qtd][pos] = '\0'; trim(campos[qtd]);
    return qtd + 1;
}

// Cria um jogo a partir de uma linha CSV
Jogo* criarJogo(char* linha) {
    char campos[20][MAX_CAMPO];
    if (separarCSV(linha, campos) < 14) return NULL;
    Jogo* g = (Jogo*)malloc(sizeof(Jogo));
    if (!g) return NULL;
    g->id = atoi(campos[0]);
    strcpy(g->nome, campos[1]);
    normalizarData(campos[2], g->dataLancamento);
    g->donosEstimados = normalizarDonos(campos[3]);
    g->preco = normalizarPreco(campos[4]);
    g->qtdIdiomas = normalizarLista(campos[5], g->idiomasSuportados);
    g->notaMetacritic = normalizarNotaInt(campos[6]);
    g->notaUsuarios = normalizarNotaFloat(campos[7]);
    g->conquistas = normalizarNotaInt(campos[8]);
    g->qtdEditoras = normalizarVirgula(campos[9], g->editoras);
    g->qtdDesenvolvedores = normalizarVirgula(campos[10], g->desenvolvedores);
    g->qtdCategorias = normalizarLista(campos[11], g->categorias);
    g->qtdGeneros = normalizarLista(campos[12], g->generos);
    g->qtdTags = normalizarLista(campos[13], g->tags);
    return g;
}

// Formata array para impressão
void formatarArray(char* resultado, char array[][MAX_STR], int tamanho) {
    if (tamanho == 0) { strcpy(resultado, "[]"); return; }
    strcpy(resultado, "[");
    for (int i = 0; i < tamanho; i++) {
        strcat(resultado, array[i]);
        if (i < tamanho - 1) strcat(resultado, ", ");
    }
    strcat(resultado, "]");
}

// Imprime os dados de um jogo
void imprimirJogo(Jogo* g) {
    char idiomas[MAX_CAMPO], editoras[MAX_CAMPO], devs[MAX_CAMPO];
    char categorias[MAX_CAMPO], generos[MAX_CAMPO], tags[MAX_CAMPO];
    formatarArray(idiomas, g->idiomasSuportados, g->qtdIdiomas);
    formatarArray(editoras, g->editoras, g->qtdEditoras);
    formatarArray(devs, g->desenvolvedores, g->qtdDesenvolvedores);
    formatarArray(categorias, g->categorias, g->qtdCategorias);
    formatarArray(generos, g->generos, g->qtdGeneros);
    formatarArray(tags, g->tags, g->qtdTags);

    char precoStr[20];
    if (g->preco == (int)g->preco) sprintf(precoStr, "%d.0", (int)g->preco);
    else sprintf(precoStr, "%.2f", g->preco);

    printf("=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##\n",
           g->id, g->nome, g->dataLancamento, g->donosEstimados, precoStr,
           idiomas, g->notaMetacritic, g->notaUsuarios, g->conquistas,
           editoras, devs, categorias, generos, tags);
}

// Inicializa biblioteca
void initBiblioteca(Biblioteca* lib) { lib->qtdJogos = 0; }

// Adiciona jogo à biblioteca
void adicionarJogo(Biblioteca* lib, Jogo* g) { if (g && lib->qtdJogos < MAX_JOGOS) lib->jogos[lib->qtdJogos++] = g; }

// Busca jogo por ID
Jogo* buscarJogo(Biblioteca* lib, int id) {
    for (int i = 0; i < lib->qtdJogos; i++) if (lib->jogos[i]->id == id) return lib->jogos[i];
    return NULL;
}

// Carrega CSV na biblioteca
void carregarCSV(Biblioteca* lib, char* caminho) {
    FILE* f = fopen(caminho, "r");
    if (!f) return;
    char linha[MAX_CAMPO * 5];
    fgets(linha, sizeof(linha), f);
    while (fgets(linha, sizeof(linha), f)) {
        Jogo* g = criarJogo(linha);
        if (g) adicionarJogo(lib, g);
    }
    fclose(f);
}

// Libera memória da biblioteca
void liberarBiblioteca(Biblioteca* lib) {
    for (int i = 0; i < lib->qtdJogos; i++) free(lib->jogos[i]);
}

// Compara datas
int compararDatas(char* d1, char* d2) {
    int dia1, mes1, ano1, dia2, mes2, ano2;
    sscanf(d1, "%d/%d/%d", &dia1, &mes1, &ano1);
    sscanf(d2, "%d/%d/%d", &dia2, &mes2, &ano2);
    if (ano1 != ano2) return (ano1 < ano2) ? -1 : 1;
    if (mes1 != mes2) return (mes1 < mes2) ? -1 : 1;
    if (dia1 != dia2) return (dia1 < dia2) ? -1 : 1;
    return 0;
}

// Quicksort recursivo
void quicksortRec(Jogo* arr[], int esq, int dir, long* comp, long* mov) {
    int i = esq, j = dir;
    Jogo* pivo = arr[(esq + dir) / 2];
    while (i <= j) {
        while (1) {
            (*comp)++;
            int cmp = compararDatas(arr[i]->dataLancamento, pivo->dataLancamento);
            if (cmp < 0 || (cmp == 0 && arr[i]->id < pivo->id)) i++;
            else break;
        }
        while (1) {
            (*comp)++;
            int cmp = compararDatas(arr[j]->dataLancamento, pivo->dataLancamento);
            if (cmp > 0 || (cmp == 0 && arr[j]->id > pivo->id)) j--;
            else break;
        }
        if (i <= j) {
            Jogo* temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            (*mov) += 3;
            i++; j--;
        }
    }
    if (esq < j) quicksortRec(arr, esq, j, comp, mov);
    if (i < dir) quicksortRec(arr, i, dir, comp, mov);
}

// Função principal do Quicksort
void quicksort(Jogo* arr[], int n, long* comp, long* mov) {
    quicksortRec(arr, 0, n - 1, comp, mov);
}

// Função principal
int main() {
    Biblioteca biblioteca;
    initBiblioteca(&biblioteca);
    carregarCSV(&biblioteca, "/tmp/games.csv");

    int ids[1000], qtdIds = 0;
    char entrada[100];
    while (fgets(entrada, sizeof(entrada), stdin)) {
        trim(entrada);
        if (strcasecmp(entrada, "FIM") == 0) break;
        ids[qtdIds++] = atoi(entrada);
    }

    Jogo* selecionados[1000];
    int qtdSelecionados = 0;
    for (int i = 0; i < qtdIds; i++) {
        Jogo* g = buscarJogo(&biblioteca, ids[i]);
        if (g) selecionados[qtdSelecionados++] = g;
    }

    long comparacoes = 0, movimentacoes = 0;
    clock_t inicio = clock();
    quicksort(selecionados, qtdSelecionados, &comparacoes, &movimentacoes);
    clock_t fim = clock();

    for (int i = 0; i < qtdSelecionados; i++) imprimirJogo(selecionados[i]);

    double tempoExec = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    FILE* log = fopen("855796_quicksort.txt", "w");
    if (log) {
        fprintf(log, "855796\t%ld\t%ld\t%.6f\n", comparacoes, movimentacoes, tempoExec);
        fclose(log);
    }

    liberarBiblioteca(&biblioteca);
    return 0;
}
