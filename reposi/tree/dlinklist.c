#include "dlinklist.h"


dlinklist* createlinklist()
{
  dlinklist* ll = (dlinklist*)malloc(sizeof(dlinklist));
  ll->head = NULL;
  ll->tail = NULL;
  return ll;
}


node* createNode(leaf *d)
{
  node *n = (node*)malloc(sizeof(node));
  n->d = d;
  n->next = NULL;
  n->prev = NULL;
  return n;
}


void addFront(dlinklist *ll,leaf *d)
{
  node *n = createNode(d);
  if(ll->head == NULL && ll->tail == NULL)
    {
      ll->head = n;
      ll->tail = n;
    }
  else
    {
      n->next = ll->head;
      ll->head->prev = n;
      ll->head = n;
    }
}

void addBack(dlinklist *ll,leaf *d)
{
  node *n = createNode(d);
  if(ll->head == NULL && ll->tail == NULL)
    {
      ll->head = n;
      ll->tail = n;
    }
  else
    {
      ll->tail->next = n;
      n->prev = ll->tail;
      ll->tail = n;
    }
}


int listSize(dlinklist *ll)
{
  node* tmp;
  int cnt = 0;
  for(tmp = ll->head; tmp != NULL; tmp=tmp->next)
    {
      cnt++;
    }
  return cnt;
}


void popFront(dlinklist *ll)
{

  node *tmp;


  if(ll == NULL)
    return;
  
  if(ll->head == NULL && ll->tail == NULL)
    return;

  if(ll->head == ll->tail)
    {      
      free(ll->head);
      ll->head = NULL;
      ll->tail = NULL;
    }
  else
    {
      tmp = ll->head;
      ll->head = ll->head->next;
      ll->head->prev = NULL;
      free(tmp);
    }

}
//remove node from the front of the list and return the leaf element
void popBack(dlinklist *ll)
{
  node *tmp;
  if(ll == NULL)
    return;
  
  if(ll->head == NULL && ll->tail == NULL)
    return;


  if(ll->head == ll->tail)
    {
      free(ll->head);
      ll->head = NULL;
      ll->tail = NULL;
    }
  else
    {
      tmp = ll->tail;
      ll->tail = ll->tail->prev;
      ll->tail->next = NULL;
      free(tmp);
    }
}


void cleanList(dlinklist *ll)
{
  node *tmp;
  while(ll->head != NULL)
    {
      tmp = ll->head;
      ll->head = ll->head->next;
      free(tmp);
    }
  free(ll);
}

leaf* checkFront(dlinklist *ll)
{

  return ll->head->d;
}

leaf* checkBack(dlinklist *ll)
{
  return ll->tail->d;
}
