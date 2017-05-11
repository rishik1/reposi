#include "stack.h"



stack* createStack()
{
	stack *s =  (stack *)malloc(sizeof(stack));
	s->ll = createlinklist();
	return(s);
}
void stackPush(stack *s,data *d)
{
	addFront(s->ll,d);

}
data* stackPop(stack *s)
{
	data *dat = popFront(s->ll);
return(dat);

}
void stackPrint(stack *s)
{
	stack *temp = createStack();
	data *d =NULL;
	while((d =popFront(s->ll)) !=NULL)
	{
		printf("%c\n",d->val);
		addFront(temp->ll,d);
	}
	while((d = popFront(temp->ll)) !=NULL)
	{
		printf("%c\n\n",d->val);
		addFront(s->ll,d);
	}
	stackClean(temp);
}
int stackSize(stack *s)
{
	stack *temp = createStack();
        data *d =NULL;
	int count =0;
        while((d =popFront(s->ll)) !=NULL)
        {
                count++;
                addFront(temp->ll,d);
        }
        while((d =popFront(temp->ll)) !=NULL)
        {

                addFront(s->ll,d);
        }
	stackClean(temp);
	return (count);
}


void stackClean(stack *s)
{
	cleanList(s->ll);
	free(s);
}


