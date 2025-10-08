/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q22 - Soma de Dígitos em C - v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int somaDigitos(int num) {
    if (num == 0) {
        return 0;
    }
    return (num % 10) + somaDigitos(num / 10);
}

int main() {
    char input[100];
    
    while (1) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) {
            break;
        }
        
        int num = atoi(input);
        printf("%d\n", somaDigitos(num));
    }
    
    return 0;
}