/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP02Q02 - Is - v1.0 - 04 / 09 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// Verifica se a string só tem vogais
bool isVogal(char *input)
{
  if (strlen(input) == 0)
    return false;

  for (int i = 0; input[i] != '\0'; i++)
  {
    char c = tolower(input[i]);
    if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'))
    {
      return false;
    }
  }
  return true;
}

// Verifica se a string só tem consoantes
bool isConsoante(char *input)
{
  if (strlen(input) == 0)
    return false;

  for (int i = 0; input[i] != '\0'; i++)
  {
    char c = tolower(input[i]);
    if (!isalpha(c))
      return false;
    if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
      return false;
  }
  return true;
}

// Verifica se a string é um número inteiro
bool isInteiro(char *input)
{
  if (strlen(input) == 0)
    return false;

  for (int i = 0; input[i] != '\0'; i++)
  {
    if (!isdigit(input[i]))
      return false;
  }
  return true;
}

// Verifica se a string é um número real
bool isReal(char *input)
{
  if (strlen(input) == 0)
    return false;

  bool dotFound = false;
  for (int i = 0; input[i] != '\0'; i++)
  {
    if (!isdigit(input[i]))
    {
      if ((input[i] == '.' || input[i] == ',') && !dotFound)
      {
        dotFound = true;
      }
      else
      {
        return false;
      }
    }
  }
  return true;
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
           isVogal(input) ? "SIM" : "NAO",
           isConsoante(input) ? "SIM" : "NAO",
           isInteiro(input) ? "SIM" : "NAO",
           isReal(input) ? "SIM" : "NAO");
  }

  return 0;
}