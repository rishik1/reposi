#ifndef _DLINKLIST_H_
#define _DLINKLIST_H_
#include <stdio.h>
#include <stdlib.h>


typedef struct
{
  float val;
}data;

typedef struct leafPtr
{
  struct leafPtr *right;
  struct leafPtr *left;
  data *d;
}leaf;



typedef struct nodePtr
{
  struct nodePtr *next;
  struct nodePtr *prev;
  leaf *d;
}node;

typedef struct
{
  node *head;
  node *tail;
}dlinklist;

//reserve space for a new dlinklist, set initial variables, and return dlinklist
dlinklist* createlinklist();
//reserve space for new node, set variables and return
node* createNode(leaf *d);
//create a new node, set the leaf pointer, and add to front of dlinklist
void addFront(dlinklist *ll,leaf *d);
//create a new node, set the leaf pointer, and add to back of dlinklist
void addBack(dlinklist *ll,leaf *d);
//count the number of elements in the dlinklist and return the count
int listSize(dlinklist *ll);
//remove node from the front of the list and return the leaf element
void popFront(dlinklist *ll);
//remove node from the front of the list and return the leaf element
void popBack(dlinklist *ll);
//free all memory in the dlinklist
void cleanList(dlinklist *ll);
//check front of list
leaf* checkFront(dlinklist *ll);
//check back of list
leaf* checkBack(dlinklist *ll);
#endif
