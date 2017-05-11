#include "statistics.h"

/*
Function Name	:: inputData
input		:: pointer to file
return's	:: pointer to bag
This function will take in the name of the file to open. Once the file is open, read data in from the file and into the newly created bag.
 Finally return the bag.
*/
bag* inputData(char *file)
{
        FILE *fp = fopen(file,"r");
        bag *s = createBag();
        float input;

        if(fp == NULL)
        {
                printf("Error Opening file or file not found!!!\n");
                exit(0);
        }
	data *element =NULL;
 
       while(fscanf(fp,"%f",&input)!=EOF)
	{
                element = createData(input);
                addData(s,element);
		if(debug1 == 1)
		{
			printf("inputed value = %f\n",input);
        	}
	}
	if(debug1 ==1)
	{
        	printBag(s);
	}
	fclose(fp);
	return(s);
}

/*
Function Name	:: Mean
input		:: pointer to bag
return's	:: float
This function will take in a bag that has data read into it. The function will then calculate the average or mean. Using the normal distribution formula to find the mean. Finally returns the mean.

mean = sum of all  values/number of values
*/
float Mean(bag *bg)
{

	int length = getsize(bg);
	int i;
	float sum = 0;
	float mean;
	data *dat = NULL;
	for(i=0 ;i<length ;i++)
	{
		dat = getItem(bg,i);
		sum = sum + dat->val;
	}

	if(debug1 == 1)
	{
		printf("sum = %f, size = %d\n",sum,length);
	}

	mean = (float) sum/length;

return mean;
}

/*
Function Name	:: StandarDeviation
input		:: pointer to bag
return's	:: float
This function will take in a bag as input.
This function will then calculate the standard deviation of the data in the bag.
Using the formula for the standard deviation of a normal distribution.
Return the standard deviation calculated.

standard deviation = sqrt((sum of square of  all deviation/(number of elemnts -1)))
*/
float StandardDeviation(bag *bg)
{
	int length = getsize(bg);
	int i;
	float mean = Mean(bg);
	float sum = 0;
	float difference = 0;
	data *dat = NULL;
	for(i=0;i< length;i++)
	{
		dat = getItem(bg,i);
		difference = (mean - dat->val);
		if(difference < 0)
		{
			difference = (-1 * difference);
		}
		sum = sum + (difference *difference);
	}

	float deviation = (float)sum/(length-1);

	deviation = sqrtf(deviation);

return (deviation);

}

/*
Function Name	:: Mode
input		:: pointer to bag
return's	:: float
This function calculates statistical mode of the data in the bag and returns it.
*/
float Mode(bag *bg)
{

	int length = getsize(bg);
	int i;
	int j;
	int max = 0;
	int *maxim = (int *)malloc(length *sizeof(int));

	for(i = 0; i < length ; i++)
	{
		maxim[i] = 0;
	}

	data *dat1 = NULL;
	data *dat2 = NULL;

	for(i = 0; i<length ;i++)
	{
		for(j=0;j<length;j++)
			{
				dat1 = getItem(bg,i);
				dat2 = getItem(bg,j);

				if(dat1->val == dat2->val)
					{
					maxim[i]++;
					}
			}
		if(debug1 ==1)
			{
			printf(" %d ",maxim[i]);
			}

	}

	for(i=0;i<length;i++)
	{
		if(maxim[max] < maxim[i])
		{
			max = i;
		}
	}
	if(debug1 == 1)
	{
		printf("\nindex:::%d\n",max);
	}

	dat1 = getItem(bg,max);
	free(maxim);
	
return(dat1->val);

}
