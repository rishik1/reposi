#ifndef _QUEUE_H_
#define _QUEUE_H_

#include "dlinklist.h"


typedef struct
{
  dlinklist *ll;
}queue;
//create a queue and set init vars
queue* createQueue();
//add a leaf onto the stack
void queuePush(queue *q,leaf *d);
//remove a leaf from the stack
void queuePop(queue *q);
//clean the queue and free all memory
void queueClean(queue *q);
//return the leaf at the front of the queue
leaf* queueFront(queue *q);
#endif
