#include "queue.h"
#include "stack.h"
#include "postfix.h"

int main(int argc, char* argv[])
{
	if(argc %2 !=0)
	{
		printf("\n input expression invalid\n");
		exit(0);
	}

	printf("%s",argv[1]);

	queue *q = parseData(argv[1]);
	queuePrint(q);
	float res = preFix(q);
	printf("\n %f",res);
	res = postFix(q);
	printf("\n %f",res);
	queueClean(q);


  return 0;
}
