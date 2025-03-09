/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q21 - Inversão de Strings em C (Recursivo)- v1.0 - 08 / 03 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <string.h>

void inverterString(char *string, int left, int right) {
    if (left >= right) {
        return;
    }
    
    char temp = string[left];
    string[left] = string[right];
    string[right] = temp;
    
    inverterString(string, left + 1, right - 1);
}

int main() {
    char input[100];
    
    while (1) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) {
            break;
        }
        
        inverterString(input, 0, strlen(input) - 1);
        printf("%s\n", input);
    }
    
    return 0;
}
