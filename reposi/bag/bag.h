#ifndef _BAG_H_
#define _BAG_H_

#include <stdio.h>
#include <stdlib.h>
#define debug2 0

typedef struct
{
	float val;
}data;

typedef struct
{
	data **array;
	int size;
}bag;

//malloc's a bag and initializes the variables of the bag to base values,
//returns new bag pointer.
bag* createBag();

//malloc's a data element and fill values,
//returns new data pointer.
data* createData(float v);

//increase bag size,
//creates new array,
//adds old data,
//adds new data,
//frees old array.
void addData(bag* b, data* d);

//prints values in data elements of the given bag.
void printBag(bag *b);

int getsize(bag *b);


//removes element from back.
void removeBack(bag *b);

//removes element from front.
void removeFront(bag *b);

//frees all memory in the bag.
void cleanBag(bag *b);

//searches for element in bag,
//if found the return first position it was found,
//if not return -1.
int searchBag(bag *b,float v);

//use search to find item.  
//if found: 
//free data item,
//decrement bag size,
//create new array,
//copy all non-removed data
void removeItem(bag *b, float v);

//return's pointer to dat of index 
data* getItem(bag*,int);
#endif
