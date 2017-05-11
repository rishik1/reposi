#include "readData.h"

//This function will read data in from a file and load it into a binary search tree
btree* readData(char* file)
{
        FILE *fp = fopen(file,"r");
        if(fp == NULL)
        {
        printf("Error Opening file or file not found!!!\n");
        exit(0);
        }

        float buff = 0;
        data *d = NULL;
        btree *t = createBtree();

        while(fscanf(fp,"%f",&buff)!=EOF)
        {
		if(debug1 ==1)
		{
			printf("value read:: %f",buff);
		}

                d = createData(buff);
                addLeaf(t,d);

        }
fclose(fp);
return(t);

}
//This function will print out all values in the btree b that fall within the range from lower to upper (inclusive)
void range(btree* b, float lower, float upper)
{

	printBound(b,lower,upper);

}

// This function will merge two binary search trees into a third binary search tree. This means that duplicate values will need to be removed
btree* merge(btree* a, btree* b)
{
	btree *res =InOrderMerge(a,b); 

}



