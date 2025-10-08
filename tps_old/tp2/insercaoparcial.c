#include <stdio.h>
#include <stdlib.h> 
#include <string.h>
#include <stdbool.h>
#include <time.h>

typedef struct{
	int date;
	int month;
	int year;
}DATE;

typedef struct{
	char *show_id;
	char *type;
	char *title;
	char *director;
	char **cast;
	size_t castLen;
	char *country;
	DATE date_added;
	int release_year;
	char *rating;
	char *duration;
	char **listed_in;
	size_t listedLen;
}SHOW;

SHOW clone(SHOW show){
	SHOW clone;

	clone.show_id = (char *)calloc(strlen(show.show_id) + 1,sizeof(char));
	strcpy(clone.show_id,show.show_id);

	clone.type = (char *)calloc(strlen(show.type) + 1,sizeof(char));
	strcpy(clone.type,show.type);

	clone.title = (char *)calloc(strlen(show.title) + 1,sizeof(char));
	strcpy(clone.title,show.title);

	clone.director = (char *)calloc(strlen(show.director) + 1,sizeof(char));
	strcpy(clone.director,show.director);

	clone.castLen = show.castLen;

	if(clone.castLen > 0){
		clone.cast = (char **)calloc(clone.castLen, sizeof(char *));
		for(int i = 0; i < clone.castLen; i++){
			clone.cast[i] = (char *)calloc(strlen(show.cast[i]) + 1, sizeof(char));
			strcpy(clone.cast[i],show.cast[i]);
		}
	}else{
		clone.cast = NULL;
	}

	clone.country = (char *)calloc(strlen(show.country) + 1,sizeof(char));
	strcpy(clone.country,show.country);

	clone.date_added = show.date_added;

	clone.release_year = show.release_year;

	clone.rating = (char *)calloc(strlen(show.rating) + 1,sizeof(char));
	strcpy(clone.rating,show.rating);

	clone.duration = (char *)calloc(strlen(show.duration) + 1,sizeof(char));
	strcpy(clone.duration,show.duration);

	clone.listedLen = show.listedLen;

	if(clone.listedLen > 0){
		clone.listed_in = (char **)calloc(clone.listedLen, sizeof(char *));
		for(int i = 0; i < clone.listedLen; i++){
			clone.listed_in[i] = (char *)calloc(strlen(show.listed_in[i]) + 1, sizeof(char));
			strcpy(clone.listed_in[i],show.listed_in[i]);
		}
	}else{
		clone.listed_in = NULL;
	}

	return clone;
}

char* toLowerCase(char *w){
	int len = strlen(w);
	char *resp = (char *)calloc(len + 1,sizeof(char));

	for(int i = 0; i < len; i++){
		if(w[i] >= 'A' && w[i] <= 'Z'){
			resp[i] = w[i] + 32;
		}else{
			resp[i] = w[i];
		}
	}

	return resp;
}

int monthToInteger(char *w){
	int resp = 0;

	if(strcmp(w,"January") == 0) resp = 1; 
	if(strcmp(w,"February") == 0) resp = 2; 
	if(strcmp(w,"March") == 0) resp = 3; 
	if(strcmp(w,"April") == 0) resp = 4; 
	if(strcmp(w,"May") == 0) resp = 5; 
	if(strcmp(w,"June") == 0) resp = 6; 
	if(strcmp(w,"July") == 0) resp = 7; 
	if(strcmp(w,"August") == 0) resp = 8; 
	if(strcmp(w,"September") == 0) resp = 9; 
	if(strcmp(w,"October") == 0) resp = 10; 
	if(strcmp(w,"November") == 0) resp = 11; 
	if(strcmp(w,"December") == 0) resp = 12; 

	return resp;
}
char *integerToMonth(int x){
	char *resp = (char *)malloc(25 * sizeof(char));
	
	switch(x){
		case 1:
			strcpy(resp,"January");
			break;
		case 2:
			strcpy(resp,"February");
			break;
		case 3:
			strcpy(resp,"March");
			break;
		case 4:
			strcpy(resp,"April");
			break;
		case 5:
			strcpy(resp,"May");
			break;
		case 6:
			strcpy(resp,"June");
			break;
		case 7:
			strcpy(resp,"July");
			break;
		case 8:
			strcpy(resp,"August");
			break;
		case 9:
			strcpy(resp,"September");
			break;
		case 10:
			strcpy(resp,"October");
			break;
		case 11:
			strcpy(resp,"November");
			break;
		case 12:
			strcpy(resp,"December");
			break;
		default:
			printf("ERROR: Mes nao encontrado");
			break;
	}

	return resp;
}

char *itoa(int num){
	char *resp = (char *)malloc(12 * sizeof(char));

	sprintf(resp,"%d",num);

	return resp;
}

char* dateToString(DATE date){
	char *s_date = (char *)calloc(255 , sizeof(char));
	char *month = integerToMonth(date.month);
	char *day = itoa(date.date);
	char *year = itoa(date.year);

	strcat(s_date,month);
	strcat(s_date," ");
	strcat(s_date,day);
	strcat(s_date,", ");
	strcat(s_date,year);

	free(month);
	free(day);
	free(year);

	return s_date;
}

char* arrayToString(char **array,size_t len){
	char *resp = (char *)calloc(255,sizeof(char));

	for(int i = 0; i < len; i++){
		strcat(resp,array[i]);
		if(i != len -1)
			strcat(resp,", ");
	}

	return resp;
}

void imprimir(SHOW *a){
	char *s_date_added;

	bool v1 = (a->date_added.month != 0);
	bool v2 = (a->date_added.date != 0);
	bool v3 = (a->date_added.year != 0);

	if(v1 && v2 && v3){
		s_date_added = dateToString(a->date_added);
	}else{
		s_date_added = (char *)calloc(5,sizeof(char));
		strcpy(s_date_added,"NaN");
	}

	char* s_cast;
	if(a->cast != NULL){
		s_cast = arrayToString(a->cast, a->castLen);
	}else{
		s_cast = (char *)calloc(5,sizeof(char));
		strcpy(s_cast,"NaN");
	}
	
	char* s_listed_in;
	if(a->listed_in != NULL){
		s_listed_in = arrayToString(a->listed_in,a->listedLen);
	}else{
		s_listed_in = (char *)calloc(5,sizeof(char));
		strcpy(s_listed_in,"NaN");
	}

	printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",a->show_id,a->title,a->type,a->director,s_cast,a->country,s_date_added, a->release_year, a->rating, a->duration, s_listed_in);

	free(s_date_added);
	free(s_listed_in);
	free(s_cast);
}

void ler(SHOW *a, char *line){
	int len = strlen(line);
	char *atributos[11];
	int k = 0;
	int l = 0;
	for(int i = 0; i < 11; i++){
		atributos[i] = (char *)calloc(1024,sizeof(char));
		strcpy(atributos[i],"NaN");
	}
	for(int i = 0; i < len && k < 11; i++){
		if(line[i] != ','){
			if(line[i] == '"'){
				i++;
				while(line[i] != '"'){
					atributos[k][l++] = line[i++];
				}
			}else{
				atributos[k][l++] = line[i];
			}
		}else{
			atributos[k][l] = '\0';
			l = 0;
			k++;
			while(line[i + 1] == ','){
				atributos[k][l++] = 'N';
				atributos[k][l++] = 'a';
				atributos[k][l++] = 'N';
				atributos[k][l] = '\0';
				i++;
				if(k < 11)
					k++;
				l = 0;
			}
			
		}
	}

	for(int i = 0; i < 11; i++){
		switch(i){
			case 0:
				{
					size_t len = strlen(atributos[i]);
					a->show_id =(char *)malloc((len + 1) * sizeof(char));
					strcpy(a->show_id,atributos[i]);
					break;
				}
			case 1:
				{
					size_t len = strlen(atributos[i]);
					a->type =(char *)malloc((len + 1)* sizeof(char));
					strcpy(a->type,atributos[i]);
					break;
				}
			case 2:
				{
					size_t len = strlen(atributos[i]);
					a->title =(char *)calloc((len + 1) , sizeof(char));
					strcpy(a->title,atributos[i]);
					break;
				}
			case 3:
				{
					size_t len = strlen(atributos[i]);
					a->director =(char *)malloc((len + 1) * sizeof(char));
					strcpy(a->director,atributos[i]);
					break;
				}
			case 4:
				{
					if(strcmp(atributos[i],"NaN") != 0 || strlen(atributos[i]) != 0){
						int quantidade = 1;
						int len = strlen(atributos[i]);

						for(int j = 0; j < len; j++)
							if(atributos[i][j] == ',')
								quantidade++;

						a->castLen = quantidade;

						a->cast = (char **)calloc(quantidade , sizeof(char*));
						for(int j = 0; j < quantidade;j++){
							*(a->cast + j) = (char *)calloc(len , sizeof(char));
						}

						for(int j = 0,k = 0,l = 0; j < len; j++){
							if(atributos[i][j] != ','){
								a->cast[k][l++] = atributos[i][j];
							}else if(atributos[i][j] == ','){
								a->cast[k++][l] = '\0';
								l = 0;
								if(atributos[i][j + 1] == ' '){
									j++;
								}
							}
						}

						size_t s_len = a->castLen;
						for(int j = 0; j < s_len - 1; j++){
							int menor = j;
							for(int k = j + 1; k < s_len; k++){
								if(strcmp(a->cast[k],a->cast[menor]) < 0){
									menor = k;
								}
							}
							char *aux = a->cast[j];
							a->cast[j] = a->cast[menor];
							a->cast[menor] = aux;
						}

					}else{
						a->castLen = 0;
						a->cast = NULL;
					}

					break;
				}
			case 5:
				{
					size_t len = strlen(atributos[i]);
					a->country =(char *)malloc((len + 1) * sizeof(char));
					strcpy(a->country,atributos[i]);
					break;
				}
			case 6:
				{
					if(strcmp(atributos[i],"NaN") != 0){
						int len = strlen(atributos[i]);
						char c_month[len];
						char c_date[len];
						char c_year[len];

						int k;
						for(int j = 0; j < len; j++){
							if(atributos[i][j] != ' '){
								c_month[j] = atributos[i][j];
							}else{
								c_month[j] = '\0';
								k = j + 1;
								j = len;
							}
						}
						for(int j = k,l = 0; j < len; j++){
							if(atributos[i][j] != ','){
								c_date[l++] = atributos[i][j];
							}else{
								c_date[l] = '\0';
								k = j + 2;
								j = len;
							}
						}
						for(int j = k,l = 0; j < len; j++){
							c_year[l++] = atributos[i][j];
							if(j == len - 1)
								c_year[l] = '\0';
						}

						a->date_added.month = monthToInteger(c_month);
						a->date_added.date = atoi(c_date);
						a->date_added.year = atoi(c_year);
					}else{
						a->date_added.month = 3;
						a->date_added.date = 1;
						a->date_added.year = 1900;
					}
					break;
				}
			case 7:
				a->release_year = atoi(atributos[i]);
				break;
			case 8:
				{
					size_t len = strlen(atributos[i]);
					a->rating =(char *)malloc((len + 1) * sizeof(char));
					strcpy(a->rating,atributos[i]);
					break;
				}
			case 9:
				{
					size_t len = strlen(atributos[i]);
					a->duration =(char *)malloc((len + 1) * sizeof(char));
					strcpy(a->duration,atributos[i]);
					break;
				}
			case 10:
				{
					if(strcmp(atributos[i],"NaN") != 0){
						int quantidade = 1;
						int len = strlen(atributos[i]);

						for(int j = 0; j < len; j++)
							if(atributos[i][j] == ',')
								quantidade++;

						a->listedLen = quantidade;

						a->listed_in = (char **)malloc(quantidade * sizeof(char*));
						for(int j = 0; j < quantidade;j++){
							*(a->listed_in + j) = (char *)malloc(len * sizeof(char));
						}

						for(int j = 0,k = 0,l = 0; j < len; j++){
							if(atributos[i][j] != ','){
								a->listed_in[k][l++] = atributos[i][j];
							}else if(atributos[i][j] == ','){
								a->listed_in[k++][l] = '\0';
								l = 0;
								if(atributos[i][j + 1] == ' '){
									j++;
								}
							}
						}

						size_t s_len = a->listedLen;
						for(int j = 0; j < s_len - 1; j++){
							int menor = j;
							for(int k = j + 1; k < s_len; k++){
								if(strcmp(a->listed_in[k],a->listed_in[menor]) < 0){
									menor = k;
								}
							}
							char *aux = a->listed_in[j];
							a->listed_in[j] = a->listed_in[menor];
							a->listed_in[menor] = aux;
						}

					}else{
						a->listedLen = 0;
						a->listed_in = NULL;
					}
					break;
				}
		}
	}

}

void readLine(char *line,int maxsize, FILE *file){
	if (file == NULL) {
		fprintf(stderr, "Erro: ponteiro de arquivo NULL passado para readLine().\n");
		exit(1);
	}

	if (fgets(line, maxsize, file) == NULL) {
		fprintf(stderr, "Erro ao ler linha do arquivo ou fim do arquivo atingido.\n");
		exit(1);
	}
	size_t len = strlen(line);
	if(line[len - 1] == '\n')
		line[len - 1] = '\0';
}

void freeShow(SHOW *i){
	free(i->show_id);
	free(i->type);
	free(i->title);
	free(i->director);
	free(i->country);
	free(i->rating);
	free(i->duration);
	if(i->cast != NULL){
		for(int j = 0; j < i->castLen; j++){
			free(*(i->cast + j));
		}
		free(i->cast);
	}
	if(i->listed_in != NULL){
		for(int j = 0; j < i->listedLen; j++){
			free(*(i->listed_in + j));
		}
		free(i->listed_in);
	}
}

bool verifica(SHOW tmp, SHOW j, int *comp){
	char *tmpType = toLowerCase( tmp.type);
	char *jType = toLowerCase(j.type);
	char *tmpTitle = toLowerCase(tmp.title);
	char *jTitle = toLowerCase(j.title);

	bool v1 = strcmp(tmpType,jType) < 0;

	if(v1)
		*comp += 1;


	bool v2 = (strcmp(tmpType,jType) == 0 &&
			strcmp(tmpTitle,jTitle) < 0);

	if(v2)
		*comp += 3;

	free(tmpType);
	free(jType);
	free(tmpTitle);
	free(jTitle);

	return (v1 || v2);
}


void ordenarParcialInsercao(SHOW *vetor, int tamanho, int *movimentos, int *comparacoes) {
    for (int i = 1; i < tamanho; i++) {
        SHOW atual = vetor[i];
        int limite = (i < 10) ? i - 1 : 9;
        int j = limite;

        while (j >= 0) {
            (*comparacoes)++;
            bool troca = false;

            if (strcmp(vetor[j].type, atual.type) > 0) {
                troca = true;
            } else if (strcmp(vetor[j].type, atual.type) == 0 &&
                       strcmp(vetor[j].title, atual.title) > 0) {
                troca = true;
            }

            if (troca) {
                vetor[j + 1] = vetor[j];
                (*movimentos)++;
                j--;
            } else {
                break;
            }
        }

        if (j + 1 != i) {
            vetor[j + 1] = atual;
            (*movimentos)++;
        }
    }
}

int main(){
	SHOW *shows = (SHOW *)calloc(1368,sizeof(SHOW));

	FILE *file = fopen("/tmp/disneyplus.csv", "r");

	char *line = (char *)malloc(1024*sizeof(char));
	while(fgetc(file) != '\n');

	for(int i = 0; i < 1368; i++){
		readLine(line, 1024,  file);

		ler((shows + i),line);

	}
	free(line);
	fclose(file);

	char *entry = (char *)malloc(255 * sizeof(char));
	scanf("%s",entry);

	SHOW array[1368];
	int tam_array = 0;

	while(strcmp(entry,"FIM") != 0){
		int id = atoi((entry + 1));
		array[tam_array++] = clone(shows[--id]);
		scanf("%s",entry);
	}

	int mov= 0;
	int comp= 0;
	clock_t inicio = clock();
	ordenarParcialInsercao(array,tam_array, &mov,&comp);
	clock_t fim = clock();
	double duration = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

	FILE *log = fopen("./844933_insercaoParcial.txt","w");
	fprintf(log,"844933\t%d\t%d\t%.6f",comp,mov,duration * 1000);
	fclose(log);

	for(int i = 0; i < 10; i++){
		imprimir(&array[i]);
	}
	

	for(int i = 0; i < 1368; i++)
		freeShow(shows + i);
	free(shows);

	return 0;
}
