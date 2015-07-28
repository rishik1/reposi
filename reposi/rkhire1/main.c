#include "array.h"	
#include "readInput.h"
#define debug 0


int main(int argc,char *argv[])
{
	int i=0;
	int size;

	if(debug==1)
	{
		printf("number of arguments::::%d\n",argc);
	
		for(i=0; i < argc ; i++)
		{
			printf("arguments are ::%s\n",argv[i]);
		}			
	}
	
	if(argc < 3)
	{
		printf("Number of arguments are less than expected\n");
		if(argc == 0)
			{
				exit(0);
			}
	}
	else if(argc>3)
	{
		printf("\n more number of arguments \n");
	}	

	int num = atoi(argv[1]);
	if(num <=0)
	{
		printf("\nplease enter integer at  command line argument 1 or value greate than 0\n");
	}

	arrayData *s= createArray(num);
	setArray(s);
	printArray(s);
	freeMem(s);

	input *p = readData(argv[2],&size);
	printData(p,&size);
	cleanData(p);
	

return 0;
}
