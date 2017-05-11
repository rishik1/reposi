#ifndef ANALYZEDATA_H
#define ANALYZEDATA_H

#include "tree.h"
#define debug2 0

/*
Name		:: readData
Input		:: pointer to char as filename
Return's	:: pointer to tree
This function read the data from a file and into a binary tree. The name of the file will be read in from the command line.
and return's the tree.
*/
tree* readData(char *file);

/*
Name		:: makeArray
Input		:: pointer to tree
Return's	:: pointer to float array
This function creates an array of floats that is the size of the number of elements in the tree.
read the float value in each of the data struct in each leaf in the array.Finally return the array.
*/
float* makeArray(tree* t);

/*
Name		:: printArray
Input		:: pointer to float array,integer Size of array
Return's	:: None
 Print out all elements of the array.
*/
void printArray(float *arr, int size);
/*
Name		:: insertionSort
Input		:: pointer to float array,int size of array
 Sort the data in the array using insertion sort.
*/
void insertionSort(float* arr, int size);

#endif
