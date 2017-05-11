#ifndef READDATA_H
#define READDATA_H

#include<stdio.h>
#include<stdlib.h>
#include "btree.h"

#define debug1 0

/*
Name		:: readData
Input		:: pinter to char as  filename
Return's	:: pointer to created btree	
This function will read data in from a file and load it into a binary search tree
*/
btree* readData(char* file);
/*
Name            :: range
Input           :: pointer to btree ,float upper bound,float lower bound
Return's        :: NOne  
This function will print out all values in the btree b that fall within the range from lower to upper (inclusive)
*/
void range(btree* b, float lower, float upper);
/*
Name            ::merge
Input           ::pointer to 1st btree,pointer to second btree to be merged
Return's        :: resutling btree   
 This function will merge two binary search trees into a third binary search tree. This means that duplicate values will need to be removed
*/
btree* merge(btree* a, btree* b);

#endif
