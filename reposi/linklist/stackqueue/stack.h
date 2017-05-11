#ifndef _STACK_H_
#define _STACK_H_
#include "dlinklist.h"

typedef struct
{
  dlinklist *ll;
}stack;

stack* createStack();
void stackPush(stack *s,data *d);
data* stackPop(stack *s);
void stackPrint(stack *s);
int stackSize(stack *s);
void stackClean(stack *s);

#endif
