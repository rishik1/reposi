#include "queue.h"

queue* createQueue()
{
	queue *q =  (queue *)malloc(sizeof(queue));
        q->ll = createlinklist();
        return(q);

}
void queuePush(queue *q,data *d)
{
	addBack(q->ll,d);
}
data* queuePop(queue *q)
{
	data *dat = popFront(q->ll);
	return(dat);
}
int queueSize(queue *q)
{
	queue *temp = createQueue();
	int count = 0;
	data *d = NULL;
	while((d = popFront(q->ll))!=NULL)
	{
		addBack(temp->ll,d);
		count++;
	} 
	while((d = popFront(temp->ll))!=NULL)
	{
		addBack(q->ll,d);
	} 
queueClean(temp);
return(count);
}
void queuePrint(queue *q)
{
  queue *temp = createQueue();
        data *d = NULL;
        while((d = popFront(q->ll))!=NULL)
        { 
	printf("\n %c",d->val);
        addBack(temp->ll,d);  
        } 
        while((d = popFront(temp->ll))!=NULL) 
        { 
	printf("\n %c",d->val);
        addBack(q->ll,d);
        } 
queueClean(temp);
}

void queueClean(queue *q)
{
cleanList(q->ll);
free(q);
}

