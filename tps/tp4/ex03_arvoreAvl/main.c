/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q03 - Arvore AVL em C - v1.0 - 26 / 06 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX 1024
#define MAX_LOW 1024

typedef struct Show
{
    char showId[7];
    char type[MAX];
    char title[MAX];
    char director[MAX];
    char cast[MAX_LOW][MAX];
    int castCount;
    char country[MAX];
    char dateAdded[MAX];
    int realeseYear;
    char rating[MAX];
    char duration[MAX];
    char listedIn[MAX_LOW][MAX];
    int listedInCount;
} Show;

typedef struct No
{
    Show *show;
    struct No *esq;
    struct No *dir;
    int altura;
} No;

char **alocar(int rows)
{
    if (rows <= 0 || rows > MAX_LOW)
    {
        return NULL;
    }
    char **matrix = (char **)malloc(rows * sizeof(char *));
    if (matrix == NULL)
    {
        return NULL;
    }
    for (int i = 0; i < rows; i++)
    {
        matrix[i] = (char *)malloc(MAX * sizeof(char));
        if (matrix[i] == NULL)
        {
            for (int j = 0; j < i; j++)
            {
                free(matrix[j]);
            }
            free(matrix);
            return NULL;
        }
        matrix[i][0] = '\0';
    }
    return matrix;
}

Show *ArrayShow(int n)
{
    Show *s = (Show *)malloc(n * sizeof(Show));
    if (!s)
        return NULL;
    return s;
}

const char *getShowId(Show *show)
{
    return show->showId;
}

void setShowId(Show *show, const char *id)
{
    strcpy(show->showId, id);
}

const char *getType(Show *show)
{
    return show->type;
}

void setType(Show *show, const char *type)
{
    strcpy(show->type, type);
}

const char *getTitle(Show *show)
{
    return show->title;
}

void setTitle(Show *show, const char *title)
{
    strcpy(show->title, title);
}

const char *getDirector(Show *show)
{
    return show->director;
}

void setDirector(Show *show, const char *director)
{
    strcpy(show->director, director);
}

char **getCastMember(const Show *show)
{
    if (show == NULL || show->castCount <= 0 || show->castCount > MAX_LOW)
    {

        return NULL;
    }
    char **castArray = alocar(show->castCount);
    if (castArray == NULL)
    {
        return NULL;
    }

    for (int i = 0; i < show->castCount; i++)
    {
        strcpy(castArray[i], show->cast[i]);
    }
    return (char **)castArray;
}

void setCastMembers(Show *show, char **cast, int count)
{
    for (int i = 0; i < count; i++)
    {
        strcpy(show->cast[i], cast[i]);
    }
    show->castCount = count;
}

const char *getCountry(Show *show)
{
    return show->country;
}

void setCountry(Show *show, const char *country)
{
    strcpy(show->country, country);
}

const char *getDateAdded(Show *show)
{
    return show->dateAdded;
}

void setDateAdded(Show *show, const char *date)
{
    strcpy(show->dateAdded, date);
}

int getReleaseYear(Show *show)
{
    return show->realeseYear;
}

void setReleaseYear(Show *show, int year)
{
    show->realeseYear = year;
}

const char *getRating(Show *show)
{
    return show->rating;
}

void setRating(Show *show, const char *rating)
{
    strcpy(show->rating, rating);
}

const char *getDuration(Show *show)
{
    return show->duration;
}

void setDuration(Show *show, const char *duration)
{
    strcpy(show->duration, duration);
}

char **getListedIn(const Show *show)
{
    if (show == NULL || show->listedInCount <= 0 || show->listedInCount > MAX_LOW)
    {
        return NULL;
    }
    char **listedInArray = alocar(show->listedInCount);
    if (listedInArray == NULL)
    {

        return NULL;
    }
    for (int i = 0; i < show->listedInCount; i++)
    {
        strcpy(listedInArray[i], show->listedIn[i]);
    }
    return (char **)listedInArray;
}

void setListedIn(Show *show, char **listed, int count)
{
    if (count > MAX_LOW)
        count = MAX_LOW;
    for (int i = 0; i < count; i++)
    {
        strcpy(show->listedIn[i], listed[i]);
    }
    show->listedInCount = count;
}

Show clone(const Show *original)
{
    Show novo;
    strcpy(novo.showId, original->showId);
    strcpy(novo.type, original->type);
    strcpy(novo.title, original->title);
    strcpy(novo.director, original->director);
    strcpy(novo.country, original->country);
    strcpy(novo.dateAdded, original->dateAdded);
    strcpy(novo.rating, original->rating);
    strcpy(novo.duration, original->duration);
    novo.realeseYear = original->realeseYear;
    novo.castCount = original->castCount;
    for (int i = 0; i < original->castCount; i++)
    {
        strcpy(novo.cast[i], original->cast[i]);
    }
    novo.listedInCount = original->listedInCount;
    for (int i = 0; i < original->listedInCount; i++)
    {
        strcpy(novo.listedIn[i], original->listedIn[i]);
    }
    return novo;
}

void printar(Show show)
{
    printf("=> ");
    printf("%s ## ", show.showId);
    printf("%s ## ", show.title);
    printf("%s ## ", show.type);
    printf("%s ## ", show.director);
    printf("[");
    for (int i = 0; i < show.castCount; i++)
    {
        printf("%s", show.cast[i]);
        if (i < show.castCount - 1)
        {
            printf(", ");
        }
    }
    printf("] ## ");
    printf("%s ## ", show.country);
    printf("%s ## ", show.dateAdded);
    printf("%d ## ", show.realeseYear);
    printf("%s ## ", show.rating);
    printf("%s ## ", show.duration);
    printf("[");
    for (int i = 0; i < show.listedInCount; i++)
    {
        printf("%s", show.listedIn[i]);
        if (i < show.listedInCount - 1)
        {
            printf(", ");
        }
    }
    printf("] ##\n");
}

char **StrToArrayStringToken(char *str)
{

    if (!str)
        return NULL;
    int cont = 0;
    int k = 0;
    while (str[k] != '\0')
    {
        if (str[k] == '|')
            cont++;
        k++;
    }
    int totalCampos = cont + 1;
    char **result = (char **)malloc(totalCampos * sizeof(char *));

    for (int i = 0; i < totalCampos; i++)
    {

        result[i] = (char *)malloc(MAX * sizeof(char));
    }
    int campo = 0;
    int i = 0, j = 0;
    char tmp[MAX];
    int len = strlen(str);
    while (i <= len)
    {
        if (str[i] == '|' || str[i] == '\0')
        {
            tmp[j] = '\0';
            if (j == 0)
            {
                strcpy(result[campo++], "NaN");
            }
            else
            {
                strcpy(result[campo++], tmp);
            }
            j = 0;
        }
        else
        {
            tmp[j++] = str[i];
        }
        i++;
    }
    return result;
}

int ContVir(char *str)
{
    int i = 0, cont = 0;
    while (str[i] != '\0')
    {
        if (str[i] == ',')
        {
            cont++;
        }
        i++;
    }
    return cont;
}

char **StrToArrayStringVir(char *str)
{
    if (!str)
    {
        char **result = (char **)malloc(sizeof(char *));
        result[0] = (char *)malloc(MAX * sizeof(char));
        strcpy(result[0], "NaN");
        return result;
    }
    int cont = 0;
    for (int i = 0; str[i] != '\0'; i++)
    {

        if (str[i] == ',')
            cont++;
    }
    int totalCampos = cont + 1;
    char **result = (char **)malloc(totalCampos * sizeof(char *));
    for (int i = 0; i < totalCampos; i++)
    {

        result[i] = (char *)malloc(MAX * sizeof(char));
        strcpy(result[i], "");
    }
    int campo = 0;
    int i = 0, j = 0;
    char tmp[MAX];
    int len = strlen(str);
    int primeiroChar = 1;
    int ultimoNaoEspaco = -1;
    while (i <= len)
    {
        if (str[i] == ',' || str[i] == '\0')
        {
            tmp[j] = '\0';
            tmp[ultimoNaoEspaco + 1] = '\0';
            if (j == 0 || strlen(tmp) == 0)
            {
                strcpy(result[campo++], "NaN");
            }
            else
            {
                strcpy(result[campo++], tmp);
            }
            j = 0;
            primeiroChar = 1;
            ultimoNaoEspaco = -1;
        }
        else
        {
            if (!(primeiroChar && str[i] == ' '))
            {
                if (str[i] != ' ')
                    ultimoNaoEspaco = j;
                tmp[j++] = str[i];
                primeiroChar = 0;
            }
        }
        i++;
    }
    return result;
}

int toInt(char *str)
{
    int len = strlen(str);
    int data = 0;
    for (int i = 0; i < len; i++)
    {
        char c = str[i];
        data = (10 * data) + (c - '0');
    }
    return data;
}

Show Ler(const char *in)
{
    Show show;
    int len = strlen(in);
    bool flag = true;
    int j = 0;
    char *simplify = (char *)malloc(2 * MAX * sizeof(char));
    if (!simplify)
        return show;
    for (int i = 0; i < len; i++)
    {

        char c = in[i];

        if (c == '"')
        {
            flag = !flag;
        }
        else if (c == ',' && flag)
        {
            simplify[j++] = '|';
        }
        else
        {
            simplify[j++] = c;
        }
    }
    simplify[j] = '\0';
    char **ArrayStrings = StrToArrayStringToken(simplify);
    setShowId(&show, ArrayStrings[0]);
    setType(&show, ArrayStrings[1]);
    setTitle(&show, ArrayStrings[2]);
    setDirector(&show, ArrayStrings[3]);
    char **CastMembers = StrToArrayStringVir(ArrayStrings[4]);
    setCastMembers(&show, CastMembers, ContVir(ArrayStrings[4]) + 1);
    setCountry(&show, ArrayStrings[5]);
    setDateAdded(&show, ArrayStrings[6]);
    setReleaseYear(&show, toInt(ArrayStrings[7]));
    setRating(&show, ArrayStrings[8]);
    setDuration(&show, ArrayStrings[9]);
    char **ListedIn = StrToArrayStringVir(ArrayStrings[10]);
    setListedIn(&show, ListedIn, ContVir(ArrayStrings[10]) + 1);
    return show;
}

Show **LerCsv()
{
    Show **resultado = calloc(1369, sizeof(Show *));
    FILE *csv = fopen("/tmp/disneyplus.csv", "rt");
    if (!csv)
    {
        printf("Erro ao abrir o arquivo");
        return NULL;
    }
    char *lixo = malloc(2048 * sizeof(char));
    fgets(lixo, 2047, csv);
    free(lixo);
    for (int i = 0; i < 1369; i++)
    {
        char *buffer = malloc(2048 * sizeof(char));
        if (fgets(buffer, 2047, csv) == NULL)
        {
            free(buffer);
        }
        else
        {
            buffer[strcspn(buffer, "\n")] = '\0';
            resultado[i] = malloc(sizeof(Show));
            *(resultado[i]) = Ler(buffer);
            free(buffer);
        }
    }
    fclose(csv);
    return resultado;
}

void swap(char a[MAX], char b[MAX])
{
    char temp[MAX];
    strcpy(temp, a);
    strcpy(a, b);
    strcpy(b, temp);
}

void quickSort(int esq, int dir, char arr[][MAX])
{
    int i = esq, j = dir;
    char pivo[MAX];
    strcpy(pivo, arr[(esq + dir) / 2]);
    while (i <= j)
    {
        while (strcmp(arr[i], pivo) < 0)
            i++;
        while (strcmp(arr[j], pivo) > 0)
            j--;
        if (i <= j)
        {

            swap(arr[i], arr[j]);
            i++;
            j--;
        }
    }
    if (esq < j)
        quickSort(esq, j, arr);
    if (i < dir)
        quickSort(i, dir, arr);
}

int max(int a, int b)
{
    return (a > b) ? a : b;
}

int altura(No *no)
{
    return (no == NULL) ? 0 : no->altura;
}

int getBalance(No *no)
{
    return (no == NULL) ? 0 : altura(no->esq) - altura(no->dir);
}

No *rotateDir(No *y)
{
    No *x = y->esq;
    No *T2 = x->dir;
    x->dir = y;
    y->esq = T2;
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    return x;
}

No *rotateEsq(No *x)
{
    No *y = x->dir;
    No *T2 = y->esq;
    y->esq = x;
    x->dir = T2;
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    return y;
}

int comparacoes = 0;

No *insertAVL(No *no, Show *show)
{
    if (no == NULL)
    {
        No *novo = (No *)malloc(sizeof(No));
        novo->show = show;
        novo->esq = novo->dir = NULL;
        novo->altura = 1;
        return novo;
    }
    comparacoes++;
    int cmp = strcmp(show->title, no->show->title);
    if (cmp < 0)
    {
        no->esq = insertAVL(no->esq, show);
    }
    else if (cmp > 0)
    {
        no->dir = insertAVL(no->dir, show);
    }
    else
    {
        return no;
    }

    no->altura = 1 + max(altura(no->esq), altura(no->dir));
    int balance = getBalance(no);

    if (balance > 1 && strcmp(show->title, no->esq->show->title) < 0)
        return rotateDir(no);

    if (balance < -1 && strcmp(show->title, no->dir->show->title) > 0)
        return rotateEsq(no);

    if (balance > 1 && strcmp(show->title, no->esq->show->title) > 0)
    {
        no->esq = rotateEsq(no->esq);
        return rotateDir(no);
    }

    if (balance < -1 && strcmp(show->title, no->dir->show->title) < 0)
    {
        no->dir = rotateDir(no->dir);
        return rotateEsq(no);
    }

    return no;
}

int searchAVL(No *no, const char *nome)
{
    printf("raiz ");
    while (no != NULL)
    {
        comparacoes++;
        int cmp = strcmp(nome, no->show->title);
        if (cmp == 0)
        {
            printf("SIM\n");
            return 1;
        }
        else if (cmp < 0)
        {
            printf("esq ");
            no = no->esq;
        }
        else
        {
            printf("dir ");
            no = no->dir;
        }
    }
    printf("NAO\n");
    return 0;
}

void freeAVL(No *no)
{
    if (no != NULL)
    {
        freeAVL(no->esq);
        freeAVL(no->dir);
        free(no->show);
        free(no);
    }
}

int main()
{
    clock_t inicio = clock();
    Show **shows = LerCsv();
    if (shows == NULL)
    {
        printf("Falha ao carregar os dados.\n");
        return 1;
    }

    No *raiz = NULL;
    char input[1001];
    scanf(" %1000[^\n]", input);
    while (strcmp(input, "FIM") != 0)
    {
        for (int i = 0; i < 1369; i++)
        {
            if (shows[i] != NULL && strcmp(getShowId(shows[i]), input) == 0)
            {
                Show *novo = malloc(sizeof(Show));
                *novo = clone(shows[i]);
                raiz = insertAVL(raiz, novo);
                break;
            }
        }
        scanf(" %1000[^\n]", input);
    }

    scanf(" %1000[^\n]", input);
    while (strcmp(input, "FIM") != 0)
    {
        searchAVL(raiz, input);
        scanf(" %1000[^\n]", input);
    }

    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    FILE *log = fopen("855796_arvoreAVL.txt", "w");
    fprintf(log, "855796\t%lf\t%d\n", tempo, comparacoes);
    fclose(log);

    freeAVL(raiz);
    for (int i = 0; i < 1369; i++)
    {
        if (shows[i] != NULL)
        {
            free(shows[i]);
        }
    }
    free(shows);
    return 0;
}