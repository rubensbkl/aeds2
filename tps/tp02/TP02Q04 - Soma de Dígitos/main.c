/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP02Q04 - Soma de Dígitos - v1.0 - 04 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Função recursiva que retorna a soma dos dígitos de num
int somaDigitos(int num)
{
  if (num == 0)
  {
    return 0;
  }
  return (num % 10) + somaDigitos(num / 10);
}

int main()
{
  char input[100];

  while (1)
  {
    // Lê a linha de entrada
    if (!fgets(input, sizeof(input), stdin))
      break;

    // Remove a quebra de linha
    int len = strlen(input);
    if (len > 0 && input[len - 1] == '\n')
      input[len - 1] = '\0';

    // Verifica se é "FIM"
    if (strcmp(input, "FIM") == 0)
      break;

    // Converte para inteiro
    int num = atoi(input);

    // Chama a função recursiva e mostra o resultado
    printf("%d\n", somaDigitos(num));
  }

  return 0;
}