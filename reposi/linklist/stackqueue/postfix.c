#include "postfix.h"

queue* parseData(char *eq)
{

	queue *q = createQueue();
	int i=0;
	data *dat =NULL;
	while(eq[i]!= '\0')
	{
		dat = createData(eq[i]);
		queuePush(q,dat);
	i++;
	}
	queuePrint(q);	
return q;	
}

float operation(float op1, float op2 ,char opr)
{
		float result = 0;

	        if(opr == '+')
                result = op1 + op2;
                
		else if(opr == '-')
                result = op1 - op2;
                
		else if(opr == '*')
                result = op1 * op2;
                
		else if(opr == '/')
                result = op1 / op2;
                
		else if(opr == '%')
                result = fmod(op1,op2);
              
		else if(opr == '^')
                result = pow(op1,op2);
return result;
}

float preFix(queue* q)
{
	float result =0;
	data *dat=NULL;
	float op1 = 0;
	float op2 = 0;
	char opr;
	queue *temp = createQueue();

		dat = queuePop(q);
		op1 = atof(&(dat->val));
		queuePush(temp,dat);
	
		dat = queuePop(q);
		opr = dat->val;
		queuePush(temp,dat);

		dat = queuePop(q);
		op2 = atof(&(dat->val));
		queuePush(temp,dat);

		result = operation(op1,op2,opr);
 
		while((dat = queuePop(q)) != NULL)
		{
			opr = dat->val;
			queuePush(temp,dat);
			
			dat = queuePop(q);
			op2 = atof(&(dat->val));
			queuePush(temp,dat);

			result = operation(result,op2,opr);
			
		}
				
		while((dat = queuePop(temp))!=NULL)
		{
			queuePush(q,dat);
		}
		queuePrint(q);
queueClean(temp);

return result;			
		
}

float postFix(queue* q)
{
	float result =0;
	float op1 = 0;
	float op2 = 0;
	char opr;
	stack *s= createStack();
	data *dat =NULL;

	while((dat = queuePop(q))!= NULL)
	{
		stackPush(s,dat);
	}

                dat = stackPop(s);
                op2 = atof(&(dat->val));
                
                dat = stackPop(s);
                opr = dat->val;

                dat = stackPop(s);
                op1 = atof(&(dat->val));

                result = operation(op1,op2,opr);
 
                while((dat = stackPop(s)) != NULL)
                {
                        opr = dat->val;
                        dat = stackPop(s);
                        op1 = atof(&(dat->val));
                        result = operation(op1,result,opr);
                }
return result;                
}


