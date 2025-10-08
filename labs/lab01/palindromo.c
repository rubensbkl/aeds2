#include <locale.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <wchar.h>
#include <wctype.h>

bool isPalindromo(wchar_t input[]) {
    int length = 0;
    wchar_t cleaned_string[100];

    // Preprocess the string: remove non-alphanumeric characters
    for (int i = 0; input[i] != L'\0'; i++) {
        if (iswalnum(input[i])) {
            cleaned_string[length++] = input[i];
        }
    }
    cleaned_string[length] = L'\0';

    // Check for palindrome
    for (int i = 0; i < length / 2; i++) {
        if (cleaned_string[i] != cleaned_string[length - i - 1]) {
            return false;
        }
    }
    return true;
}

int main() {
    setlocale(LC_ALL, "");
    
    wchar_t message[100];
    wint_t input;
    int length;

    do {
        length = 0;
        
        while ((input = getwchar()) != L'\n') {
            if (input == WEOF) {
                break;
            }
            if (length < 99) {
                message[length++] = input;
            }
        }

        if (input == WEOF) {
            break;
        }

        message[length] = L'\0';

        if (wcscmp(message, L"FIM") == 0) {
            break;
        }

        isPalindromo(message) ? wprintf(L"SIM\n") : wprintf(L"NAO\n");
        
    } while (true);

    return 0;
}