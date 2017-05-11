#include "tree.h"
#include "dlinklist.h"
#include "analyzeData.h"

int main(int argc,char *argv[])
{
	if(argc > 2 || argc < 1)
	{
		printf("invalid input from command line \n");
	}
	
	tree *t = readData(argv[1]);
	float *arr = makeArray(t);
	int size = treeSize(t);
	printArray(arr,size);

//	printf("\n%d",size);
	printf("\n");

	insertionSort(arr,size);
	printArray(arr,size);
	
	treeClean(t);
	free(arr);

  return 0;
}
