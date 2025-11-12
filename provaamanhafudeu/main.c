#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(void) {
    char entrada[32];
    int matriz[200][200];

    while (scanf("%31s", entrada) == 1) {
        if (strcmp(entrada, "FIM") == 0) break;

        int N = atoi(entrada);
        int M;
        if (scanf("%d", &M) != 1) return 0;

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j)
                scanf("%d", &matriz[i][j]);

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                if (matriz[i][j] == 1) {
                    printf("9");
                } else {
                    int acc = 0;
                    // cima
                    if (i-1 >= 0 && matriz[i-1][j] == 1) ++acc;
                    // baixo
                    if (i+1 < N && matriz[i+1][j] == 1) ++acc;
                    // esquerda
                    if (j-1 >= 0 && matriz[i][j-1] == 1) ++acc;
                    // direita
                    if (j+1 < M && matriz[i][j+1] == 1) ++acc;

                    printf("%d", acc);
                }
                if (j + 1 < M) printf(" ");
            }
            printf("\n");
        }

    }

    return 0;
}
