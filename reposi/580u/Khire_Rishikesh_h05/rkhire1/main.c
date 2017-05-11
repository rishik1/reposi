/* HOMEWORK 5
Rishikesh Khire
*/

#include<stdio.h>
#include<stdlib.h>
#include<time.h>
#define debug 0
#define len 5					//legth of array structure

/*
structure conatains 2 floating point variable X & Y
and one integer Z
*/
typedef struct{
	float x;
	float y;
	int z;
}Astructure;


int pointer_add(int *,int *);
Astructure *random_alloc();
void free_alloc(Astructure *);
Astructure *array_Astructure(int );
void print_Astructure(Astructure *);

/*main function*/
int main(void)
{
srand(time(NULL));
int i;

	
	Astructure *p = random_alloc();

	Astructure *q = array_Astructure(10);
	
	print_Astructure(p);

	Astructure *temp;	
	temp = q;
	printf("\n");
	for(i=0;i<len;i++)
                {
                        print_Astructure(temp);
			temp++;
		}

	
	free_alloc(p);
	free_alloc(q);
	
	if(debug == 1)
	{
		printf("\n Address after free of p\n");
		printf("%f %f %d\n",p->x,p->y,p->z);
		for(i=0;i<len;i++)
		{
		 printf("%f %f %d\n",q->x,q->y,q->z);
		 q++;
		}
	}

return 0;
}

/*
NAME		:: random_alloc
INPUT		:: None
RETURN's	:: Astructure *
The function allocate memory on heap to a Astructure
and variables x , y and z are filled with random values
*/
Astructure *random_alloc()
{
	Astructure *ptr =(Astructure *)malloc(sizeof(Astructure));

	ptr->x = (float) rand();
	ptr->y = (float) rand();
	ptr->z = rand();
	return(ptr);
}

/*
NAME		:: free_alloc
INPUT		:: pointer to Astructure
RETURN's	:: None
The function free's the structure whose memory is allocated  on Heap
*/
void free_alloc(Astructure *ptr)
{
	free(ptr);
}

/*
NAME		:: array_Astructure
INPUT		:: integer length as size of array
RETURN's	:: pointer to Astructure
The function allocate memoery to array of Astructure on Heeap.
and fill Astructure array variables with random values. 
*/
Astructure *array_Astructure(int length)
{
	int i;
	Astructure *ptr1;
	
	ptr1 = (Astructure *)malloc(length *sizeof(Astructure));

	for(i=0;i<length;i++)
	{
		ptr1[i].x = (float) rand();
		ptr1[i].y = (float) rand();
		ptr1[i].z = rand();

	}

	return(ptr1);
}

/*
NAME		:: print_Atsructure
INPUT		:: pointer to Astructure
RETURN's	:: None
The function prints value of variables of Astructure
*/
void print_Astructure(Astructure *s)
{
        printf("%f %f %d\n",s->x,s->y,s->z);
}

