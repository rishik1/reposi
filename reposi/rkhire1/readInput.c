
#include "readInput.h"
/*
Function Name   :: readData    
Input           :: file pointer and pointer to integer size for size of array
Return value    :: NONE
Function readData reads from file in read only mode "r" and store values in structure "buffer" allocated on heap
and at the end closes file

*/
input* readData(char *filename,int *size)
{

	FILE *fp = fopen(filename,"r");
	if(fp == NULL)
    	{
        printf("Error Opening file or file not found!!!\n");
        exit(0);
    	}

	int length;
	int i=0;
	fscanf(fp,"%d",&length);
	
	input *buffer = NULL;
	buffer = (input*)malloc(length *sizeof(input));

	while(!feof(fp) && i<length)
	{
		
		fscanf(fp,"%d",&(buffer[i].a));	
		fscanf(fp,"%f",&(buffer[i].b));
		i++;
	}
	
	
	if(i!=length)				// means loop breaked fue to end of file
	{
		*size = i-1;			// if to handle if first line to text file has more value than actual entries
	}					// so put size = counter -1  "-1" because loop breaks on extra count
	else
		*size =i;			//else keep the size as given in input file

	fclose(fp);
	return(buffer);
}

/*
Function Name   :: printData    
Input           :: pointer to structure input and pointer to integer size for size of array
Return value    :: NONE
Function prints the data in structure read from file

*/


void printData(input* data,int *size)
{
	int i;
	for(i=0; i < *size;i++)
	{
		printf("%d %f\n",data[i].a,data[i].b);
	}

}

/*
Function Name	:: cleanData
Input		:: pointer to structure input
Return value	:: NONE
Function frees the memory allocated on heap

*/

void cleanData(input* data)
{
	free(data);
}

