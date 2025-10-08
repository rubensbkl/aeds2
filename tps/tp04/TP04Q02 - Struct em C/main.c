#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Game {
    int id;
    char name[100];
    char releaseDate[11];
    int estimatedOwners;
    float price;
    char supportedLanguages[100];
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[100];
    char developers[100];
    char categories[100];
    char genres[100];
    char tags[100];
} Game;
