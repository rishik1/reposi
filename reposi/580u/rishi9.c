#include<stdio.h>
#include<stdlib.h>
#define debug 0
#define len 10

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

int main(void)
{
srand(time(NULL));
int a=10;
int b=20;
int res;
int i;
	res= pointer_add(&a,&b);
	printf("%d\n",res);

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
	i=0;
	while(i<len)
	{
                free_alloc(q);
		i++;
		q++;
        }

return 0;
}


int pointer_add(int *a,int *b)
{

	int res;
	res= *a + *b;
	return res;
}


Astructure *random_alloc()
{
	Astructure *ptr =(Astructure *)malloc(sizeof(Astructure));

	ptr->x = (float) rand();
	ptr->y = (float) rand();
	ptr->z = rand();
	return(ptr);
}

void free_alloc(Astructure *ptr)
{
	free(ptr);
}

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

void print_Astructure(Astructure *s)
{
        printf("\n%f %f %d",s->x,s->y,s->z);
}
