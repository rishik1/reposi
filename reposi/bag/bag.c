#include "bag.h"

/*
Function Name	:: createBag
input		:: None
return's 	:: pointer to bag
function malloc's a bag and initializes the variables of the bag to base values
*/
bag* createBag()
{

	bag *s= (bag*)malloc(sizeof(bag));
	s->array=NULL;
	s->size=0;
return(s);
}

/*
Function Name	:: createData
input		:: float 
return's	:: pointer to data
The function malloc's a data element and fill values
*/
data* createData(float v)
{

	data *d = (data*)malloc(sizeof(data));
	d->val = v;

return(d);
}

/*
Function Name	:: addData
input		:: pointer to bag and pointer to data
return's	:: None
The function increase bag size,creates new array,adds old data,adds new data,frees old array.
*/
void addData(bag* b, data* d)
{

int i=0;
int newsize = b->size+1;

	data **temp = b->array;

			b->array = (data**)malloc(newsize*sizeof(data*));

	for(i=0;i< b->size;i++)
	{
		b->array[i] = temp[i];
	}

	b->array[i] = d;
	if(debug2 ==1)
	{
		printf("\nvalue = %f\n",d->val);
	}
	b->size = newsize;

	free(temp);
}
/*
Function Name	:: printBag
input		:: pointer to Bag
return's	:: None
The function prints values in data elements of the given bag.
*/
void printBag(bag *b)
{
	int i=0;
	for(i=0;i < (b->size);i++)
	{
		printf("%f\n",b->array[i]->val);
	}
}

/*
Function Name	:: removeBag
input		:: pointer to bag
return's	:: None
the function removes element from back
*/
void removeBack(bag *b)
{
	int newsize = b->size-1;
	int i=0;
        data **temp = b->array;
	free(temp[newsize]);

        b->array = (data**)malloc(newsize*sizeof(data*));
	b->size = newsize;

	for(i=0;i<newsize;i++)
	{
	           b->array[i] = temp[i];
			if(debug2 == 1)
			{
			printf("value =%f  index = %d\n",b->array[i]->val,i);
			}
	}
	free(temp);

}
/*
Function Name	:: removeFront
input		:: pointer to bag
return's	:: None
the funtion removes element from front.
*/
void removeFront(bag *b)
{

	int i=0;
	int newsize = b->size-1;
	data **temp = b->array;
        b->array = (data**)malloc(newsize*sizeof(data*));

        for(i=1; i < b->size ;i++)
        {
                   b->array[i-1] = temp[i];
        }
	b->size = newsize;
        
	free(temp[0]);
        free(temp);
}
/*
Function Name	:: getItem
input		:: pointer to bag and integer  index of required value
return's	:: float value
This function get the data at the position index and returns it
*/

data* getItem(bag *b,int index)
{
	return(b->array[index]);
}


/*
Funtion Name	:: getsize
input		:: pointer to bag
return's	:: integer
the funtion return the size of the bag
*/
int getsize(bag *b)
{
	return(b->size);
}


/*
Function Name	:: cleanBag
input		:: pointer to bag
return's	:: None
This function cleans all the memory allocated by bag
*/
void cleanBag(bag *b)
{

int i;

	for(i=0;i< b->size;i++)
	{
		free(b->array[i]);

	}
free(b->array);
free(b);

}

/*
Function Name	:: searchBag
input		:: pointer to bag and a float value
return's	:: integer positon of value send
This function searches for element in bag,
if found the return first position it was found,
if not return -1.
*/
int searchBag(bag *b,float v)
{
	int i;
	for(i=0 ; i< b->size ;i++)
	{
		if(b->array[i]->val == v)
		{
			return(i);
		}
	}
return(-1);
}

/*
Function Name	:: removeItem
input		:: pointer to bag  and a float value
return's 	:: None
This function use search to find item.
if found,
frees data item,
decrement bag size,
create new array,
copy all non-removed data
*/
void removeItem(bag *b, float v)
{

	int newsize = b->size-1;

	int i=0;
	int j=0;
	int pos = searchBag(b,v);
	if(pos != -1)
	{
        data **temp = b->array;
        b->array = (data**)malloc(newsize*sizeof(data*));


        	for(i=0;i < b->size;i++)
        	{
			if(i == pos)
			{
			continue;
			}
			else{
                	     b->array[j] = temp[i];
			}
		j++;
		}

   	b->size =newsize;
        
	free(temp[pos]);
	free(temp);
	}
	if(debug2 ==1)
	{
		printf("Inside remove item :: size = %d\n",b->size);
	}

}

