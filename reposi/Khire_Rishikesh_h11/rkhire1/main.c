#include<stdlib.h>
#include<stdio.h>
#include "readData.h"
#include "btree.h"
#define debug 0

int main(int argc,char *argv[])
{
	if(argc>3 && argc<1)
	{
		printf("invalid input from command line \n");
		exit(0);
	}
	
	btree *t1 = readData(argv[1]);
	#if debug
		printf("Inorder TREE1 ::\n");
		printInOrder(t1);
		printf("\n");
	#endif

	range(t1,4,8);
	printf("\n");
	
	btree *t2 = readData(argv[2]);
	#if debug		
		printf("Inorder Tree2 ::\n");
		printInOrder(t2);
		printf("\n");
	#endif
	
	range(t2,4,8);

	btree *t3 = merge(t1,t2);
	#if debug
	printf("\nInorder Merged Tree ::\n");
	printInOrder(t3);
	#endif

	cleanTree(t1);
	cleanTree(t2);
	cleanTreeMerge(t3);

return 0;
}
