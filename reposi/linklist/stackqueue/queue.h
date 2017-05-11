
#ifndef _QUEUE_H_
#define _QUEUE_H_

#include "dlinklist.h"

typedef struct
{
  dlinklist *ll;
}queue;

queue* createQueue();
void queuePush(queue *q,data *d);
data* queuePop(queue *q);
int queueSize(queue *q);
void queuePrint(queue *q);
void queueClean(queue *q);
#endif
