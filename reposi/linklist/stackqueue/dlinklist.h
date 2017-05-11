#ifndef _DLINKLIST_H_
#define _DLINKLIST_H_
#include <stdio.h>
#include <stdlib.h>


typedef struct
{
  char val;
}data;

typedef struct nodePtr
{
  struct nodePtr *next;
  struct nodePtr *prev;
  data *d;
}node;

typedef struct
{
  node *head;
  node *tail;
}dlinklist;
//reserve space for a new dlinklist, set initial variables, and return dlinklist
dlinklist* createlinklist();
//reserve space for new node, set variables and return
node* createNode(data *d);
//reserve space for a new data struct,set variables and return
data* createData(char val);
//create a new node, set the data pointer, and add to front of dlinklist
void addFront(dlinklist *ll,data *d);
//create a new node, set the data pointer, and add to back of dlinklist
void addBack(dlinklist *ll,data *d);
//print values in the data structures in the dlinklist
void printList(dlinklist *ll);
//count the number of elements in the dlinklist and return the count
int listSize(dlinklist *ll);
//remove node from the front of the list and return the data element
data* popFront(dlinklist *ll);
//remove node from the front of the list and return the data element
data* popBack(dlinklist *ll);
//free all memory in the dlinklist
void cleanList(dlinklist *ll);
//reverse the elements of the dlinklist
void reverseList(dlinklist *ll);
//search for element in dlinklist, if found remove.
void searchRemove(dlinklist *ll,char val);
#endif
