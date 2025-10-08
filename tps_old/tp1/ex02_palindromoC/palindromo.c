/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q02 - Palíndromo em C - v1.0 - 28 / 02 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <locale.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <wchar.h>
#include <wctype.h>

bool isPalindromo(wchar_t input[], int length) {
    for (int i = 0; i < length / 2; i++) {
        if (input[i] != input[length - i - 1]) {
            return false;
        }
    }
    return true;
}

int main() {

    setlocale(LC_ALL, "");
    
    wchar_t message[100];

    do {
        wint_t input;
        int length = 0;

        while ((input = getwchar()) != '\n') {
            if (length < 100) {
                message[length++] = input;
            }
        }
        message[length] = L'\0';

        if (wcscmp(message, L"FIM") == 0) {
            break;
        }

        isPalindromo(message, length) ? wprintf(L"SIM\n") : wprintf(L"NAO\n");
        
    } while (wcscmp(message, L"FIM") != 0);

    return 0;
}