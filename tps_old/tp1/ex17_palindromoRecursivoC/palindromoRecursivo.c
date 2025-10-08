/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q17 - Palíndromo em C (Recursivo) - v1.0 - 28 / 02 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <string.h>
#include <stdbool.h>
 
bool isPalindromo(char input[], int left, int right) {
    if (left >= right) {
        return true;
    }
    if (input[left] != input[right]) {
        return false;
    }
    return isPalindromo(input, left + 1, right - 1);
}
    
int main() {
    
    char input[1000];

    while (true) {

        scanf(" %[^\n]", input);

        if (strcmp(input, "FIM") == 0) {
            break;
        } else {
            int length = strlen(input);
            if (isPalindromo(input, 0, length - 1)) {
                printf("SIM\n");
            } else {
                printf("NAO\n");
            }
        }
    }
    return 0;
}