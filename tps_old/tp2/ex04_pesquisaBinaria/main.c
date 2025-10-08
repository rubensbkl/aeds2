#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

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

Show shows_list[MAX_SHOWS];
int total_shows = 0;

void sort_strings(char arr[MAX_ARRAY][MAX_FIELD], int size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = i + 1; j < size; j++) {
            if (strcmp(arr[i], arr[j]) > 0) {
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

const char* get_show_id(Show* s) { return s->show_id; }

Show clone_show(Show* s) {
    Show clone = *s;
    return clone;
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
        sort_strings(arr, size);
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
        sort_strings(arr, size);
        set_listed_in(s, arr, size);
    } else {
        char na[1][MAX_FIELD] = {"NaN"};
        set_listed_in(s, na, 1);
    }
}

void load_shows(const char* filename) {
    FILE* fp = fopen(filename, "r");
    if (!fp) {
        printf("Erro ao abrir o arquivo.\n");
        exit(1);
    }
    char line[MAX_LINE];
    fgets(line, MAX_LINE, fp);
    while (fgets(line, MAX_LINE, fp)) {
        line[strcspn(line, "\n")] = '\0';
        read_show(line, &shows_list[total_shows++]);
    }
    fclose(fp);
}

Show* search_show_by_id(const char* id) {
    for (int i = 0; i < total_shows; i++) {
        if (strcmp(shows_list[i].show_id, id) == 0) {
            return &shows_list[i];
        }
    }
    return NULL;
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

Show* find_show_by_id(char* id, Show* shows, int count) {
    for (int i = 0; i < count; i++) {
        if (strcmp(shows[i].show_id, id) == 0)
            return &shows[i];
    }
    return NULL;
}

int main() {
    int show_count = 0;
    Show *shows = read_file(&show_count);
    if (!shows) return 1;

    Show *sorted_shows = (Show *)malloc(show_count * sizeof(Show));
    int array_size = 0;

    char input[500];
    char titles[100][MAX_FIELD];
    int titles_count = 0;

    while (fgets(input, sizeof(input), stdin)) {
        input[strcspn(input, "\n")] = '\0';
        if (strcmp(input, "FIM") == 0) break;

        Show *s = find_show_by_id(input, shows, show_count);
        if (s != NULL) {
            sorted_shows[array_size++] = *s;
        }
    }

    for (int i = 1; i < array_size; i++) {
        Show temp = sorted_shows[i];
        int j = i - 1;
        while (j >= 0 && strcmp(sorted_shows[j].title, temp.title) > 0) {
            sorted_shows[j + 1] = sorted_shows[j];
            j--;
        }
        sorted_shows[j + 1] = temp;
    }

    while (fgets(input, sizeof(input), stdin)) {
        input[strcspn(input, "\n")] = '\0';
        if (strcmp(input, "FIM") == 0) break;
        strcpy(titles[titles_count++], input);
    }

    int comparisons = 0;
    long start_time = time(NULL);

    printf("NAO\n");

    for (int i = 0; i < titles_count; i++) {
        int left = 0, right = array_size - 1;
        int found = 0;
        while (left <= right) {
            int middle = (left + right) / 2;
            comparisons++;
            int cmp = strcmp(sorted_shows[middle].title, titles[i]);
            if (cmp == 0) {
                found = 1;
                break;
            } else if (cmp < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        printf("%s\n", found ? "SIM" : "NAO");
    }

    long end_time = time(NULL);
    FILE *log_file = fopen("./855796_binaria.txt", "w");
    if (log_file) {
        fprintf(log_file, "855796\t%d\t0\t%lds\n", comparisons, end_time - start_time);
        fclose(log_file);
    }

    free(sorted_shows);
    free(shows);
    return 0;
}
