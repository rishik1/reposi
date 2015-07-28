#include "array.h"


/*
Function Name 		:: createArray
Input			:: integer size
Return Value		:: pointer to structure arrayData
Function take size of array to be created and allocated memory on heap of value size .
*/
arrayData* createArray(int size)
{

	arrayData *s=(arrayData*)malloc(sizeof(arrayData));

	s->array = (int *)malloc(size * sizeof(int));

	s->size = size;

return(s);
}

/*
Function Name		:: setArray
Input 			:: pointer to structure arraytData
Return Value		:: NONE
Function sets the value of arrayData structure variable value with random values between 0 and 50
*/
void setArray(arrayData *data)
{
	srand(time(NULL));
	int i=0;

	for(i=0;i < data->size;i++)
	{
		data->array[i]= rand()%50;

	}

}

/*
Function Name		:: printArray
Input			:: pointer to structure arrayData
Return Value		:: None
Function printArray prints the value in structure arrayData pointed by pointer data.
*/
void printArray(arrayData *data)
{
	int i=0;
	
	for(i=0;i < data->size;i++)
	{
		printf("%d\n",data->array[i]);
	} 

}

/*
Function Name		:: freeMem
Input			:: pointer to arrayData structure
Return value		:: None
Function free's the memory allocated on heap to arrayDaata structure

*/
void freeMem(arrayData *data)
{
	free(data->array);

	free(data);
}
