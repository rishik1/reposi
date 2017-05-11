#include "queue.h"


queue* createQueue()
{
  queue *q = (queue*)malloc(sizeof(queue));
  q->ll = createlinklist();

  return q;
}


void queuePush(queue *q,leaf *d)
{
  addBack(q->ll,d);
}

void queuePop(queue *q)
{
  popFront(q->ll);

}

void queueClean(queue *q)
{
  cleanList(q->ll);
  free(q);
}

leaf* queueFront(queue *q)
{
	if(q->ll->head == NULL)return NULL;

  return checkFront(q->ll);
}
