#include<stdio.h>
#include<stdlib.h>
#include<time.h>

typedef struct{
	int *array;
	int size;
}arrayData;


arrayData* createArray(int size);
void setArray(arrayData *data);
void printArray(arrayData *data);
void freeMem(arrayData *data);


