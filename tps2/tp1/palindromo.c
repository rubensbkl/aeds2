/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP01Q02 - Palíndromo RECURSIVO - v1.0 - 27 / 08 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <locale.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <wchar.h>
#include <wctype.h>

bool isPalindromo(wchar_t input[], int start, int end) {
    if (start >= end) return true;
    if (input[start] != input[end]) return false;

    return isPalindromo(input, start + 1, fim - 1);
}

int main() {

    setlocale(LC_ALL, "");
    
    wchar_t message[100];

    while (1) {
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

        isPalindromo(message, 0, length - 1) ? wprintf(L"SIM\n") : wprintf(L"NAO\n");
    }

    return 0;
}
