/**
 *
 * Questão 1 - Subsequência
 *
 * Você foi contratado pela Agência Extra-Espacial Brasileira,que procura
 * indícios de vida extraterrestre. Umdos telescópios da Agência, para o
 * espectro ultravioleta, gera uma sequência de valores inteiros positivos que
 * devem ser analisados continuamente.
 *
 * Dadas duas sequências SA e SB, sua primeira missão é determinar se SB é uma
 * subsequência de SA. Uma subsequência de uma dada sequência S é um conjunto de
 * elementos de S que não são necessariamente adjacentes mas que mantêm a mesma
 * ordem em que aparecem em S. Por exemplo, [2], [1, 4], [1, 2,4] e [1,2,3,4]
 * são subsequências de [1,2,3,4], mas [4,3], [3,4,1] e [1,3,5] não são.
 *
 * Entrada
 * A primeira linha contém dois inteiros A e B, o número de elementos das
 * sequências. A segunda linha contém A inteiros Xi, os números da sequência SA.
 * A seguir a entrada contém B inteiros Yi, os números da sequência SB.
 *
 * Saída
 * Seu programa deve produzir uma única linha, contendo um único caractere, que
 * deve ser a letra maiúscula ‘S’ se SB é uma subsequência da SA ou a letra
 * maiúscula ‘N’ caso contrário.
 *
 */

#include <stdio.h>
#include <stdlib.h>

int main() {
  int a = 0, b = 0;
  scanf("%d %d", &a, &b);
  int *sa = malloc(sizeof(int) * a);
  int *sb = malloc(sizeof(int) * b);
  for (int i = 0; i < a; i++) { scanf("%d", &sa[i]); }
  for (int i = 0; i < b; i++) { scanf("%d", &sb[i]); }
  int idx = 0;
  int acc = 0;
  for (int i = 0; i < a; i++) {
    if (sa[i] == sb[idx]) {
      acc++;
      idx++;
    }
  }

  if (acc == b) {
    printf("S");
  } else {
    printf("N");
  }

  return 0;
}
