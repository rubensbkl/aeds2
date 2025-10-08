#include <stdio.h>

void combinador(char str1[], char str2[])
{
  char result[2048];
  int i = 0, j = 0, k = 0;

  while (str1[i] != '\0' && str2[j] != '\0')
  {
    result[k++] = str1[i++];
    result[k++] = str2[j++];
  }

  while (str1[i] != '\0')
  {
    result[k++] = str1[i++];
  }

  while (str2[j] != '\0')
  {
    result[k++] = str2[j++];
  }

  result[k] = '\0';

  printf("%s\n", result);
}

int main()
{
  char str1[1024], str2[1024];
  while (scanf("%s %s", str1, str2) == 2) {
    combinador(str1, str2);
  }

  return 0;
}
