#ifndef READINPUT_H
#define READINPUT_H

#include<stdio.h>
#include<stdlib.h>

typedef struct{
	int a;
	float b;
}input;

input* readData(char *filename,int *size);

void printData(input* data,int *size);
void cleanData(input* data);

#endif
