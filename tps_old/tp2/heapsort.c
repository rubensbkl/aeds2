#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>
#include<time.h>

#define MAX 512
#define MAX_CAST_LISTED 20
#define MAX_PARTS 60

int comparacoes = 0;
typedef struct SHOW{

    char showId[7];
    char type[MAX];
    char title[MAX];
    char director[MAX];
    char cast[MAX_CAST_LISTED][MAX];
    int castCount;
    char country[MAX];
    char dateAdded[MAX];
    int realeseYear;
    char rating[MAX];
    char duration[MAX];
    char listedIn[MAX_CAST_LISTED][MAX];
    int listedInCount;

} Show;
Show* ArrayShow(int n){

    Show* s = (Show*)malloc(n * sizeof(Show));

    if(!s) return NULL;

    return s;
}
char** alocar(int rows){

    if(rows <= 0 || rows > MAX_CAST_LISTED){

        return NULL;
    }

    char** matrix = (char**)malloc(rows * sizeof(char*));

    if(matrix == NULL){

        return NULL;
    }

    for(int i = 0; i < rows; i++){

        matrix[i] = (char*)malloc(MAX * sizeof(char));
        if(matrix[i] == NULL){

            for(int j = 0; j < i; j++){

                free(matrix[j]);
            }

            free(matrix);
            return NULL;
        }

        matrix[i][0] = '\0';
    }

    return matrix;
}
void freeMat(char** matrix, int rows){

    if(matrix != NULL){

        for(int i = 0; i < rows; i++){

            free(matrix[i]);
        }

        free(matrix);
    }
}




const char* getShowId(Show* show){
    return show->showId;
}

void setShowId(Show* show, const char* id){
    strcpy(show->showId, id);
}

const char* getType(Show* show){
    return show->type;
}

void setType(Show* show, const char* type){
    strcpy(show->type, type);
}

const char* getTitle(Show* show){
    return show->title;
}

void setTitle(Show* show, const char* title){
    strcpy(show->title, title);
}

const char* getDirector(Show* show){
    return show->director;
}

void setDirector(Show* show, const char* director){
    strcpy(show->director, director);
}

char** getCastMember(const Show* show){
    if(show == NULL || show->castCount <= 0 || show->castCount > MAX_CAST_LISTED){
        return NULL;
    }
    char** castArray = alocar(show->castCount);
    if(castArray == NULL){
        return NULL;
    }
    for(int i = 0; i < show->castCount; i++){
        strcpy(castArray[i], show->cast[i]);
    }
    return (char**)castArray;
}

void setCastMembers(Show* show, char** cast, int count){
    for(int i = 0; i < count; i++){
        strcpy(show->cast[i], cast[i]);
    }
    show->castCount = count;
}

const char* getCountry(Show* show){
    return show->country;
}

void setCountry(Show* show, const char* country){
    strcpy(show->country, country);
}

const char* getDateAdded(Show* show){
    return show->dateAdded;
}

void setDateAdded(Show* show, const char* date){
    strcpy(show->dateAdded, date);
}

int getReleaseYear(Show* show){
    return show->realeseYear;
}

void setReleaseYear(Show* show, int year){
    show->realeseYear = year;
}

const char* getRating(Show* show){
    return show->rating;
}

void setRating(Show* show, const char* rating){
    strcpy(show->rating, rating);
}

const char* getDuration(Show* show){
    return show->duration;
}


void setDuration(Show* show, const char* duration){
    strcpy(show->duration, duration);
}

char** getListedIn(const Show* show){
    if(show == NULL || show->listedInCount <= 0 || show->listedInCount > MAX_CAST_LISTED){
        return NULL;
    }

    char** listedInArray = alocar(show->listedInCount);
    if(listedInArray == NULL){
        return NULL;
    }

    for(int i = 0; i < show->listedInCount; i++){
        strcpy(listedInArray[i], show->listedIn[i]);
    }

    return (char**)listedInArray;
}
void setListedIn(Show* show, char** listed, int count){
    if(count > MAX_CAST_LISTED) count = MAX_CAST_LISTED;
    for(int i = 0; i < count; i++){
        strcpy(show->listedIn[i], listed[i]);
    }
    show->listedInCount = count;
}
Show* cloneShow(const Show* original) {
    Show* novo = (Show*)malloc(sizeof(Show));
    strcpy(novo->showId, original->showId);
    strcpy(novo->type, original->type);
    strcpy(novo->title, original->title);
    strcpy(novo->director, original->director);
    strcpy(novo->country, original->country);
    strcpy(novo->dateAdded, original->dateAdded);
    strcpy(novo->rating, original->rating);
    strcpy(novo->duration, original->duration);
    novo->realeseYear = original->realeseYear;
    novo->castCount = original->castCount;
    for(int i = 0; i < original->castCount; i++){
        strcpy(novo->cast[i], original->cast[i]);
    }
    novo->listedInCount = original->listedInCount;
    for(int i = 0; i < original->listedInCount; i++){
        strcpy(novo->listedIn[i], original->listedIn[i]);
    }
    return novo;
}
void printar(Show *show)
{
    printf("=> ");
    printf("%s ## ", show->showId);
    printf("%s ## ", show->title);
    printf("%s ## ", show->type);
    printf("%s ## ", show->director);
    printf("[");
    for (int i = 0; i < show->castCount; i++)
    {
        printf("%s", show->cast[i]);
        if (i < show->castCount - 1)
        {
            printf(", ");
        }
    }
    printf("] ## ");
    printf("%s ## ", show->country);
    printf("%s ## ", show->dateAdded);
    printf("%d ## ", show->realeseYear);
    printf("%s ## ", show->rating);
    printf("%s ## ", show->duration);
    printf("[");
    for (int i = 0; i < show->listedInCount; i++)
    {
        printf("%s", show->listedIn[i]);
        if (i < show->listedInCount - 1)
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
int StrToInt(char *str)
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
    setReleaseYear(&show, StrToInt(ArrayStrings[7]));
    setRating(&show, ArrayStrings[8]);
    setDuration(&show, ArrayStrings[9]);
    char **ListedIn = StrToArrayStringVir(ArrayStrings[10]);
    setListedIn(&show, ListedIn, ContVir(ArrayStrings[10]) + 1);
    return show;
}

Show LerCsv(char *title)
{
    Show resultado;
    FILE *csv = fopen("disneyplus.csv", "rt");
    if (!csv)
    {
        perror("Erro ao abrir o arquivo");
        return resultado;
    }
    else
    {
        char *lixo = malloc(2048 * sizeof(char));
        fgets(lixo, 2047, csv);
        free(lixo);
        for (int i = 0; i < 300; i++)
        {
            char *buffer = malloc(2048 * sizeof(char));
            if (fgets(buffer, 2047, csv) == NULL)
            {
                free(buffer);
            }
            else if (strcmp(title, fgets(buffer, 2047, csv)) == 0)
            {
                buffer[strcspn(buffer, "\n")] = '\0';
                resultado = Ler(buffer);
                free(buffer);
            }
        }
        fclose(csv);
        return resultado;
    }
}

Show **LerCsvCompleto()
{
    Show **resultado = calloc(1369, sizeof(Show *));
    FILE *csv = fopen("/tmp/disneyplus.csv", "rt");
    if (!csv)
    {
        perror("Erro ao abrir o arquivo");
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

void swap(char str1[], char str2[])
{

    char temp[MAX];
    strcpy(temp, str1);
    strcpy(str1, str2);
    strcpy(str2, temp);
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

void swapShows(Show **a, Show **b)
{

    Show *temp = *a;
    *a = *b;
    *b = temp;
}

void quickSortByTitle(int esq, int dir, Show **array)
{

    int i = esq, j = dir;
    char pivo[MAX];
    strcpy(pivo, array[(esq + dir) / 2]->title);

    while (i <= j)
    {

        while (strcmp(array[i]->title, pivo) < 0)
            i++;
        while (strcmp(array[j]->title, pivo) > 0)
            j--;

        if (i <= j)
        {

            swapShows(&array[i], &array[j]);
            i++;
            j--;
        }
    }

    if (esq < j)
        quickSortByTitle(esq, j, array);
    if (i < dir)
        quickSortByTitle(i, dir, array);
}

int compararShowsPorDirectorETitulo(Show *a, Show *b)
{
    int cmpDirector = strcasecmp(a->director, b->director);
    if (cmpDirector != 0)
    {
        return cmpDirector;
    }
    return strcasecmp(a->title, b->title);
}

void heapify(Show **arr, int n, int i)
{
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    if (left < n && compararShowsPorDirectorETitulo(arr[left], arr[largest]) > 0)
    {
        largest = left;
    }

    if (right < n && compararShowsPorDirectorETitulo(arr[right], arr[largest]) > 0)
    {
        largest = right;
    }

    if (largest != i)
    {
        Show *temp = arr[i];
        arr[i] = arr[largest];
        arr[largest] = temp;
        heapify(arr, n, largest);
    }
}

void buildHeap(Show **arr, int n)
{
    for (int i = n / 2 - 1; i >= 0; i--)
    {
        heapify(arr, n, i);
    }
}

void heapSort(Show **arr, int n)
{
    buildHeap(arr, n);

    for (int i = n - 1; i > 0; i--)
    {
        Show *temp = arr[0];
        arr[0] = arr[i];
        arr[i] = temp;

        heapify(arr, i, 0);
    }
}

int main()
{
    clock_t inicio = clock();
    Show **resultado = LerCsvCompleto();
    Show **ArrayShow = calloc(300, sizeof(Show *));
    int j = 0;

    if (resultado == NULL)
    {

        perror("Falha ao carregar os dados.\n");
        return 1;
    }

    char *input1 = malloc(1001 * sizeof(char));
    if (input1 == NULL)
    {

        perror("Erro ao alocar memória para input1 inicial");
        return 1;
    }

    scanf(" %10[^\n]", input1);

    while (strcmp(input1, "FIM") != 0)
    {

        for (int i = 0; i < 1369; i++)
        {

            if (strcmp(input1, getShowId(resultado[i])) == 0)
            {

                ArrayShow[j++] = cloneShow(resultado[i]);
                quickSort(0, ArrayShow[j - 1]->castCount - 1, ArrayShow[j - 1]->cast);
                i = 1369;
            }
        }

        scanf(" %1000[^\n]", input1);
    }

    free(input1);

    heapSort(ArrayShow, j);

    for (int i = 0; i < 10; i++)
    {

        printar(ArrayShow[i]);
    }

    for (int i = 0; i < 1369; i++)
    {

        if (resultado[i] != NULL)
        {

            free(resultado[i]);
        }
    }

    for (int i = 0; i < 300; i++)
    {

        if (ArrayShow[i] != NULL)
        {

            free(ArrayShow[i]);
        }
    }

    free(resultado);
    free(ArrayShow);

    clock_t fim = clock();

    double tempo = (double)(fim - inicio);
    FILE *log = fopen("844933_selecaoRecursiva.txt", "w");
    if (log == NULL)
    {
        printf("Erro ao abrir");
        return 1;
    }
    fprintf(log, "844933\t");
    fprintf(log, "%d\t", comparacoes);
    fprintf(log, "%f\t", tempo);
    fclose(log);

    return 0;
}