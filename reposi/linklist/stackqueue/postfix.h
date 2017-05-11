#ifndef POSTFIX_H
#define POSTFIX_H

#include<stdio.h>
#include<math.h>
#include "stack.h"
#include "queue.h"
#include "dlinklist.h"

queue* parseData(char *eq);
float preFix(queue* q);
float postFix(queue* q);
float operation(float op1,float op2,char opr);

#endif
