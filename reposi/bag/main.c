
#include "bag.h"
#include "statistics.h"
#define debug 0

int main(int argc,char* argv[])
{
	int i = 0;

 	bag* s = createBag();

	if(debug ==1)
	{
	printf("number of arguments::::%d\n",argc);
                for(i=0; i < argc ; i++)
                {
                        printf("arguments are ::%s\n",argv[i]);
                }
	}

	s  = inputData(argv[1]);

	if(debug == 1)
	{
		printBag(s);
		printf("\n size =%d\n",s->size);
	}

	float mean =  Mean(s);
	printf("%f\n",mean);


	float deviation = StandardDeviation(s);
	printf("%f\n",deviation);

	float mode = Mode(s);
	printf("%f\n",mode);

	cleanBag(s);

  return 0;
}
