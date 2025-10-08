#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include <stdbool.h>

#define MAX_SHOWS 14000
#define MAX_FIELD 256
#define MAX_ARRAY 30
#define MAX_LINE 2048

typedef struct {
    char show_id[MAX_FIELD];
    char type[MAX_FIELD];
    char title[MAX_FIELD];
    char director[MAX_FIELD];
    char cast[MAX_ARRAY][MAX_FIELD];
    int cast_size;
    char country[MAX_FIELD];
    char date_added[MAX_FIELD];
    int release_year;
    char rating[MAX_FIELD];
    char duration[MAX_FIELD];
    char listed_in[MAX_ARRAY][MAX_FIELD];
    int listed_size;
} Show;

Show all_shows[MAX_SHOWS];
int total_shows = 0;

int cmp_insensitive(const char *str1, const char *str2) {
    while (*str1 && *str2) {
        char c1 = tolower(*str1);
        char c2 = tolower(*str2);
        if (c1 != c2) {
            return c1 - c2;
        }
        str1++;
        str2++;
    }
    return tolower(*str1) - tolower(*str2);
}

void sort_array(char arr[MAX_ARRAY][MAX_FIELD], int size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = i + 1; j < size; j++) {
            if (cmp_insensitive(arr[i], arr[j]) > 0) {
                char temp[MAX_FIELD];
                strcpy(temp, arr[i]);
                strcpy(arr[i], arr[j]);
                strcpy(arr[j], temp);
            }
        }
    }
}

int split_string(char* src, char arr[MAX_ARRAY][MAX_FIELD]) {
    int count = 0;
    char* token = strtok(src, ",");
    while (token && count < MAX_ARRAY) {
        while (*token == ' ') token++;
        strncpy(arr[count], token, MAX_FIELD - 1);
        arr[count][MAX_FIELD - 1] = '\0';
        count++;
        token = strtok(NULL, ",");
    }
    return count;
}

void parse_csv_line(char* line, char fields[11][MAX_FIELD]) {
    int i = 0, f = 0;
    int in_quotes = 0;
    char buffer[MAX_FIELD] = "";

    for (i = 0; line[i] != '\0'; i++) {
        if (line[i] == '"') {
            in_quotes = !in_quotes;
        } else if (line[i] == ',' && !in_quotes) {
            if (f < 11) {
                strncpy(fields[f], buffer, MAX_FIELD - 1);
                fields[f][MAX_FIELD - 1] = '\0';
                f++;
            }
            buffer[0] = '\0';
        } else {
            int len = strlen(buffer);
            if (len < MAX_FIELD - 1) {
                buffer[len] = line[i];
                buffer[len + 1] = '\0';
            }
        }
    }
    if (f < 11) {
        strncpy(fields[f], buffer, MAX_FIELD - 1);
        fields[f][MAX_FIELD - 1] = '\0';
    }
}

void set_show_id(Show* s, const char* val) { strncpy(s->show_id, val, MAX_FIELD); }
void set_type(Show* s, const char* val) { strncpy(s->type, val, MAX_FIELD); }
void set_title(Show* s, const char* val) { strncpy(s->title, val, MAX_FIELD); }
void set_director(Show* s, const char* val) { strncpy(s->director, val, MAX_FIELD); }
void set_country(Show* s, const char* val) { strncpy(s->country, val, MAX_FIELD); }
void set_date_added(Show* s, const char* val) { strncpy(s->date_added, val, MAX_FIELD); }
void set_release_year(Show* s, int val) { s->release_year = val; }
void set_rating(Show* s, const char* val) { strncpy(s->rating, val, MAX_FIELD); }
void set_duration(Show* s, const char* val) { strncpy(s->duration, val, MAX_FIELD); }
void set_cast(Show* s, char arr[MAX_ARRAY][MAX_FIELD], int size) {
    s->cast_size = size;
    for (int i = 0; i < size; i++) strncpy(s->cast[i], arr[i], MAX_FIELD);
}
void set_listed_in(Show* s, char arr[MAX_ARRAY][MAX_FIELD], int size) {
    s->listed_size = size;
    for (int i = 0; i < size; i++) strncpy(s->listed_in[i], arr[i], MAX_FIELD);
}

Show clone_show(Show* s) {
    Show c = *s;
    return c;
}

void print_show(Show* s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_size; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_size; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_size - 1) printf(", ");
    }
    printf("] ##\n");
}

void read_show(char* line, Show* s) {
    char fields[11][MAX_FIELD];
    parse_csv_line(line, fields);

    set_show_id(s, strlen(fields[0]) ? fields[0] : "NaN");
    set_type(s, strlen(fields[1]) ? fields[1] : "NaN");
    set_title(s, strlen(fields[2]) ? fields[2] : "NaN");
    set_director(s, strlen(fields[3]) ? fields[3] : "NaN");

    if (strlen(fields[4])) {
        char arr[MAX_ARRAY][MAX_FIELD];
        int size = split_string(fields[4], arr);
        sort_array(arr, size);
        set_cast(s, arr, size);
    } else {
        char na[1][MAX_FIELD] = {"NaN"};
        set_cast(s, na, 1);
    }

    set_country(s, strlen(fields[5]) ? fields[5] : "NaN");
    set_date_added(s, strlen(fields[6]) ? fields[6] : "March 1, 1900");
    set_release_year(s, strlen(fields[7]) ? atoi(fields[7]) : 0);
    set_rating(s, strlen(fields[8]) ? fields[8] : "NaN");
    set_duration(s, strlen(fields[9]) ? fields[9] : "NaN");

    if (strlen(fields[10])) {
        char arr[MAX_ARRAY][MAX_FIELD];
        int size = split_string(fields[10], arr);
        sort_array(arr, size);
        set_listed_in(s, arr, size);
    } else {
        char na[1][MAX_FIELD] = {"NaN"};
        set_listed_in(s, na, 1);
    }
}

Show* read_file(int* count) {
    *count = 0;
    Show* shows = malloc(MAX_SHOWS * sizeof(Show));
    if (!shows) return NULL;
    FILE* fptr = fopen("/tmp/disneyplus.csv", "r");
    if (!fptr) return shows;

    char* line = (char*)malloc(MAX_LINE);
    if (!line) {
        fclose(fptr);
        return shows;
    }

    fgets(line, MAX_LINE, fptr);

    while (fgets(line, MAX_LINE, fptr) != NULL && *count < MAX_SHOWS) {
        line[strcspn(line, "\n")] = '\0';
        read_show(line, &shows[*count]);
        (*count)++;
    }

    free(line);
    fclose(fptr);
    return shows;
}

Show* findOne(char* id, Show* shows, int count) {
    for (int i = 0; i < count; i++) {
        if (cmp_insensitive(shows[i].show_id, id) == 0)
            return &shows[i];
    }
    return NULL;
}

void swap(Show *a, Show *b) {
    Show temp = *a;
    *a = *b;
    *b = temp;
}

void bubble_sort(Show* array, int n, int* comparacoes, int* movimentacoes) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            (*comparacoes)++;
            int cmp = cmp_insensitive(array[j].date_added, array[j + 1].date_added);
            if (cmp > 0 || (cmp == 0 && cmp_insensitive(array[j].title, array[j + 1].title) > 0)) {
                swap(&array[j], &array[j + 1]);
                (*movimentacoes)++;
            }
        }
    }
}

void free_show(Show* s) {}

void free_shows(Show *shows, int count) {
    for (int i = 0; i < count; i++) {
        free_show(&shows[i]);
    }
    free(shows);
}

int main() {
    int show_count = 0;
    Show *all_shows = read_file(&show_count);
    if (!all_shows) return 1;

    Show *selected = (Show *)malloc(MAX_SHOWS * sizeof(Show));
    int selected_count = 0;

    char input[100];
    while (true) {
        scanf(" %s", input);
        if (cmp_insensitive(input, "FIM") == 0) break;

        Show *s = findOne(input, all_shows, show_count);
        if (s != NULL) {
            selected[selected_count++] = clone_show(s);
        }
    }

    int movimentos = 0;
    int comparacoes = 0;

    clock_t inicio = clock();
    bubble_sort(selected, selected_count, &comparacoes, &movimentos);
    clock_t fim = clock();
    double duracao = (double)(fim - inicio) / CLOCKS_PER_SEC;

    FILE *log = fopen("844933_bolha.txt", "w");
    if (log) {
        fprintf(log, "844933\t%d\t%d\t%.6f\n", comparacoes, movimentos, duracao * 1000);
        fclose(log);
    }

    for (int i = 0; i < selected_count; i++) {
        print_show(&selected[i]);
    }

    free_shows(all_shows, show_count);
    free_shows(selected, selected_count);

    return 0;
}
