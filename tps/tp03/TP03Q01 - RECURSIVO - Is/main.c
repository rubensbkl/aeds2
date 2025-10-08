/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP03Q01 - Is Recursivo - v1.0 - 15 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

// Verifica se um caractere é vogal
bool ehVogal(char c)
{
  c = tolower(c);
  return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
}

// Verifica se a string só tem vogais
bool isVogalRec(char *input, int i)
{
  if (input[i] == '\0')
    return (i > 0);
  if (!ehVogal(input[i]))
    return false;
  return isVogalRec(input, i + 1);
}

// Verifica se a string só tem consoantes
bool isConsoanteRec(char *input, int i)
{
  if (input[i] == '\0')
    return (i > 0);
  char c = tolower(input[i]);
  if (!isalpha(c))
    return false;
  if (ehVogal(c))
    return false;
  return isConsoanteRec(input, i + 1);
}

// Verifica se a string é um número inteiro
bool isInteiroRec(char *input, int i)
{
  if (input[i] == '\0')
    return (i > 0);
  if (!isdigit(input[i]))
    return false;
  return isInteiroRec(input, i + 1);
}

// Verifica se a string é um número real
bool isRealRec(char *input, int i, bool dotFound)
{
  if (input[i] == '\0')
    return (i > 0);
  if (!isdigit(input[i]))
  {
    if ((input[i] == '.' || input[i] == ',') && !dotFound)
    {
      return isRealRec(input, i + 1, true);
    }
    else
    {
      return false;
    }
  }
  return isRealRec(input, i + 1, dotFound);
}

int main()
{
  char input[512];

  while (1)
  {
    if (!fgets(input, sizeof(input), stdin))
      break;

    int len = strlen(input);
    if (len > 0 && input[len - 1] == '\n')
      input[len - 1] = '\0';

    if (strcmp(input, "FIM") == 0)
      break;

    printf("%s %s %s %s\n",
           isVogalRec(input, 0) ? "SIM" : "NAO",
           isConsoanteRec(input, 0) ? "SIM" : "NAO",
           isInteiroRec(input, 0) ? "SIM" : "NAO",
           isRealRec(input, 0, false) ? "SIM" : "NAO");
  }

  return 0;
}
