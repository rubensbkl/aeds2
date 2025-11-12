#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

void moveRight(char* str) {
	for (int i = strlen(str); i > 0; i--) {
		str[i] = str[i-1];
	}
}

int main() {
	char input[100000];

	bool ii = false;
	while (scanf("%s", input) != 0) {
		char *result = malloc(100000 * sizeof(int));
		for (int i = 0; i < strlen(input); i++) {
			printf("%d %ld\n", i, strlen(input));
			if (input[i] == '[') ii = true;
			else if (input[i] == ']') ii = false;
			else if (ii) {
				moveRight(result);
				result[0] = input[i];
			} else {
				result[strlen(result)] = input[i];
				printf("result: %s\n", result);
			}
		}
	}
	return 0;
}
