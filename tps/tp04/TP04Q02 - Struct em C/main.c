/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP04Q01 - Struct em C - v1.0 - 13 / 10 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>

#define MAX_FIELD 5000
#define MAX_ARRAY 500
#define MAX_STR 500
#define MAX_GAMES 100000

typedef struct {
    int id;
    char name[MAX_STR];
    char releaseDate[15];
    int estimatedOwners;
    float price;
    char supportedLanguages[MAX_ARRAY][MAX_STR];
    int languagesCount;
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[MAX_ARRAY][MAX_STR];
    int publishersCount;
    char developers[MAX_ARRAY][MAX_STR];
    int developersCount;
    char categories[MAX_ARRAY][MAX_STR];
    int categoriesCount;
    char genres[MAX_ARRAY][MAX_STR];
    int genresCount;
    char tags[MAX_ARRAY][MAX_STR];
    int tagsCount;
} Game;

typedef struct {
    Game* games[MAX_GAMES];
    int count;
} Library;

void trim(char* str) {
    int start = 0;
    int end = strlen(str) - 1;
    
    while (str[start] == ' ' || str[start] == '\t' || str[start] == '\n' || str[start] == '\r') start++;
    while (end >= start && (str[end] == ' ' || str[end] == '\t' || str[end] == '\n' || str[end] == '\r')) end--;
    
    for (int i = 0; i <= end - start; i++) {
        str[i] = str[start + i];
    }
    str[end - start + 1] = '\0';
}

int getMonthNumber(char* month) {
    if (strncasecmp(month, "Jan", 3) == 0) return 1;
    if (strncasecmp(month, "Feb", 3) == 0) return 2;
    if (strncasecmp(month, "Mar", 3) == 0) return 3;
    if (strncasecmp(month, "Apr", 3) == 0) return 4;
    if (strncasecmp(month, "May", 3) == 0) return 5;
    if (strncasecmp(month, "Jun", 3) == 0) return 6;
    if (strncasecmp(month, "Jul", 3) == 0) return 7;
    if (strncasecmp(month, "Aug", 3) == 0) return 8;
    if (strncasecmp(month, "Sep", 3) == 0) return 9;
    if (strncasecmp(month, "Oct", 3) == 0) return 10;
    if (strncasecmp(month, "Nov", 3) == 0) return 11;
    if (strncasecmp(month, "Dec", 3) == 0) return 12;
    return 1;
}

void normalizeDate(char* dateStr, char* result) {
    if (!dateStr || strlen(dateStr) == 0) {
        strcpy(result, "01/01/0001");
        return;
    }
    
    char month[20], day[10], year[10];
    
    if (sscanf(dateStr, "%s %[^,], %s", month, day, year) == 3) {
        sprintf(result, "%02d/%02d/%04d", atoi(day), getMonthNumber(month), atoi(year));
    } else if (sscanf(dateStr, "%s %s", month, year) == 2) {
        sprintf(result, "01/%02d/%04d", getMonthNumber(month), atoi(year));
    } else if (strlen(dateStr) == 4) {
        sprintf(result, "01/01/%s", dateStr);
    } else {
        strcpy(result, "01/01/0001");
    }
}

int normalizeOwners(char* str) {
    if (!str || strlen(str) == 0) return 0;
    
    char cleaned[MAX_STR];
    int j = 0;
    for (int i = 0; str[i]; i++) {
        if ((str[i] >= '0' && str[i] <= '9') || str[i] == '-') {
            cleaned[j++] = str[i];
        }
    }
    cleaned[j] = '\0';
    
    char* dash = strchr(cleaned, '-');
    if (dash) *dash = '\0';
    
    return atoi(cleaned);
}

float normalizePrice(char* str) {
    if (!str || strlen(str) == 0 || strcasecmp(str, "Free to Play") == 0) return 0.0;
    
    for (int i = 0; str[i]; i++) {
        if (str[i] == ',') str[i] = '.';
    }
    return atof(str);
}

int normalizeIntScore(char* str) {
    return (str && strlen(str) > 0) ? atoi(str) : -1;
}

float normalizeFloatScore(char* str) {
    if (!str || strlen(str) == 0 || strcasecmp(str, "tbd") == 0) return -1.0;
    
    for (int i = 0; str[i]; i++) {
        if (str[i] == ',') str[i] = '.';
    }
    return atof(str);
}

int normalizeList(char* str, char result[][MAX_STR]) {
    if (!str || strlen(str) == 0 || strcmp(str, "[]") == 0) return 0;
    
    int count = 0;
    int i = 0;
    int len = strlen(str);
    
    while (i < len && count < MAX_ARRAY) {
        while (i < len && (str[i] == ' ' || str[i] == '[' || str[i] == ']' || str[i] == ',')) i++;
        if (i >= len) break;
        
        int hasQuote = (str[i] == '\'');
        if (hasQuote) i++;
        
        int start = i;
        while (i < len && str[i] != (hasQuote ? '\'' : ',') && str[i] != ']') i++;
        
        int size = i - start;
        if (hasQuote && i < len && str[i] == '\'') i++;
        
        if (size > 0) {
            strncpy(result[count], &str[start], size);
            result[count][size] = '\0';
            trim(result[count]);
            if (strlen(result[count]) > 0) count++;
        }
    }
    return count;
}

int normalizeComma(char* str, char result[][MAX_STR]) {
    if (!str || strlen(str) == 0) return 0;
    
    char temp[MAX_FIELD];
    strcpy(temp, str);
    
    int count = 0;
    char* token = strtok(temp, ",");
    while (token && count < MAX_ARRAY) {
        trim(token);
        if (strlen(token) > 0) {
            strcpy(result[count++], token);
        }
        token = strtok(NULL, ",");
    }
    return count;
}

int splitCSV(char* line, char fields[][MAX_FIELD]) {
    int count = 0;
    int inQuotes = 0;
    int pos = 0;
    
    for (int i = 0; line[i] && count < 20; i++) {
        if (line[i] == '"') {
            if (inQuotes && line[i + 1] == '"') {
                fields[count][pos++] = '"';
                i++;
            } else {
                inQuotes = !inQuotes;
            }
        } else if (line[i] == ',' && !inQuotes) {
            fields[count][pos] = '\0';
            trim(fields[count]);
            count++;
            pos = 0;
        } else {
            fields[count][pos++] = line[i];
        }
    }
    fields[count][pos] = '\0';
    trim(fields[count]);
    return count + 1;
}

Game* parseGame(char* line) {
    char fields[20][MAX_FIELD];
    if (splitCSV(line, fields) < 14) return NULL;
    
    Game* g = (Game*)malloc(sizeof(Game));
    if (!g) return NULL;
    
    g->id = atoi(fields[0]);
    strcpy(g->name, fields[1]);
    normalizeDate(fields[2], g->releaseDate);
    g->estimatedOwners = normalizeOwners(fields[3]);
    g->price = normalizePrice(fields[4]);
    g->languagesCount = normalizeList(fields[5], g->supportedLanguages);
    g->metacriticScore = normalizeIntScore(fields[6]);
    g->userScore = normalizeFloatScore(fields[7]);
    g->achievements = normalizeIntScore(fields[8]);
    g->publishersCount = normalizeComma(fields[9], g->publishers);
    g->developersCount = normalizeComma(fields[10], g->developers);
    g->categoriesCount = normalizeList(fields[11], g->categories);
    g->genresCount = normalizeList(fields[12], g->genres);
    g->tagsCount = normalizeList(fields[13], g->tags);
    
    return g;
}

void formatArray(char* result, char array[][MAX_STR], int size) {
    if (size == 0) {
        strcpy(result, "[]");
        return;
    }
    
    strcpy(result, "[");
    for (int i = 0; i < size; i++) {
        strcat(result, array[i]);
        if (i < size - 1) strcat(result, ", ");
    }
    strcat(result, "]");
}

void printGame(Game* g) {
    char lang[MAX_FIELD], pub[MAX_FIELD], dev[MAX_FIELD];
    char cat[MAX_FIELD], gen[MAX_FIELD], tag[MAX_FIELD];
    
    formatArray(lang, g->supportedLanguages, g->languagesCount);
    formatArray(pub, g->publishers, g->publishersCount);
    formatArray(dev, g->developers, g->developersCount);
    formatArray(cat, g->categories, g->categoriesCount);
    formatArray(gen, g->genres, g->genresCount);
    formatArray(tag, g->tags, g->tagsCount);
    
    char price[20];
    if (g->price == (int)g->price) {
        sprintf(price, "%d.0", (int)g->price);
    } else {
        sprintf(price, "%.2f", g->price);
        int len = strlen(price);
        while (len > 1 && price[len-1] == '0' && price[len-2] != '.') {
            price[--len] = '\0';
        }
    }
    
    printf("=> %d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##\n",
           g->id, g->name, g->releaseDate, g->estimatedOwners, price,
           lang, g->metacriticScore, g->userScore, g->achievements,
           pub, dev, cat, gen, tag);
}

void initLibrary(Library* lib) {
    lib->count = 0;
}

void addGame(Library* lib, Game* game) {
    if (game && lib->count < MAX_GAMES) {
        lib->games[lib->count++] = game;
    }
}

Game* findGame(Library* lib, int id) {
    for (int i = 0; i < lib->count; i++) {
        if (lib->games[i]->id == id) return lib->games[i];
    }
    return NULL;
}

void loadCSV(Library* lib, char* path) {
    FILE* file = fopen(path, "r");
    if (!file) return;
    
    char line[MAX_FIELD * 5];
    fgets(line, sizeof(line), file);
    
    while (fgets(line, sizeof(line), file)) {
        Game* game = parseGame(line);
        if (game) addGame(lib, game);
    }
    
    fclose(file);
}

void freeLibrary(Library* lib) {
    for (int i = 0; i < lib->count; i++) {
        free(lib->games[i]);
    }
}

int main() {
    Library lib;
    initLibrary(&lib);
    loadCSV(&lib, "/tmp/games.csv");
    
    int ids[1000];
    int count = 0;
    char input[100];
    
    while (fgets(input, sizeof(input), stdin)) {
        trim(input);
        if (strcasecmp(input, "FIM") == 0) break;
        ids[count++] = atoi(input);
    }
    
    for (int i = 0; i < count; i++) {
        Game* game = findGame(&lib, ids[i]);
        if (game) printGame(game);
    }
    
    freeLibrary(&lib);
    return 0;
}