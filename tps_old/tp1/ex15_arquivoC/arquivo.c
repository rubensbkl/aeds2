/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q15 - Arquivo em C - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>

void writeFile(int n, const char *filename) {
    FILE *file = fopen(filename, "w");
    if (file == NULL) {
        return;
    }

    for (int i = 0; i < n; i++) {
        double num;
        scanf("%lf", &num);
        fprintf(file, "%lf\n", num);
    }

    fclose(file);
}

void readReverse(int n, const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        return;
    }

    double numbers[n];
    int count = 0;

    while (fscanf(file, "%lf", &numbers[count]) == 1) {
        count++;
    }

    for (int i = count - 1; i >= 0; i--) {
        if (numbers[i] == (int)numbers[i]) {
            printf("%d\n", (int)numbers[i]);
        } else {
            printf("%g\n", numbers[i]);
        }
    }

    fclose(file);
}

int main() {
    int n;
    scanf("%d", &n);

    const char *filename = "arquivo.txt";
    writeFile(n, filename);
    readReverse(n, filename);

    return 0;
}
