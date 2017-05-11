#include "analyzeData.h"

//This function will read the data from a file and into a binary tree. The name of the file will be read in from the command line
tree* readData(char *file)
{

	FILE *fp = fopen(file,"r");
	if(fp == NULL)
    	{
        printf("Error Opening file or file not found!!!\n");
        exit(0);
	}

	float buff = 0;
	data *d = NULL;
	tree *t = createTree();

	 while(fscanf(fp,"%f",&buff)!=EOF)
	{	
		d = createData(buff);
		addLeaf(t,d);
		#if debug2
			printf("\n%f",buff);
		#endif

	}
fclose(fp);	
return(t);
}
//This function will create an array of floats that is the size of the number of elements in the tree.
//read the float value in each of the data struct in each leaf in the array.Finally return the array.

float* makeArray(tree* t)
{
	float *array = treeArray(t);

		#if debug2
		int i=0;
		int size = treeSize(t);
		for(i=0;i<size;i++)
		{
			printf("\n%f",array[i]);
		}
		#endif

	return(array);
}

// Print out all elements of the array.                                    
void printArray(float *arr, int size)
{
	 int i=0;

        for(i=0;i<size;i++)
        {
                printf("%f\n",arr[i]);
        }

}
// Sort the data in the array using insertion sort.                                       
void insertionSort(float* arr, int size)
{
	int i=1;
	int j=0;
	float temp = 0;
	for(i=0;i<size;i++)
	{
		temp =arr[i];
		for(j=i-1;j>=0;j--)
		{
			if(temp>arr[j])
			{
				break;
			}
			else
			{
				arr[j+1]=arr[j];
			}
		}
		arr[j+1]=temp;
			
			#if debug2
			int k=0;
				printf("\n");
				for(k=0;k<size;k++)
					{
						printf(" %f",arr[k]);
					}
			#endif
	}	

}

