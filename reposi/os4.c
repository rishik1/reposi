#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<unistd.h>
#include <linux/unistd.h> 

#define __NR_faultCount 310

typedef struct{
	int a;
	int b;
	int c;
}s;

/*
Name    :: badCase
Input	:: NONE
Return  :: NONE
This function is for bad case in which array are
three array taken a,b,c and multiplication of array a & b is put into c
start faultCount before multiplication and stop faultCount 
and print the count
*/
void badCase()
{
int a[100000];
        int b[100000];
        int c[100000];
        int ret;
        int i;
	printf("VERSION 1 BAD CASE:::\n");
	ret = syscall(__NR_faultCount,0);
        for(i=0;i<100000;i++)
        {
                a[i] = 1;
                b[i] = 1;
        }
 
                // making the system call
                for(i=0;i<100000;i++)
                {
                        c[i] = a[i]*b[i];
                }
                ret = syscall(__NR_faultCount,1);
                printf("Fault count :: %d\n",ret);

}

/*
Name :: goodCase
Input   :: NONE
Return  :: NONE
This function is for good case in which array are
structure s has three variable  a,b,c and multiplication of
structure array elements  a & b is put into c
start faultCount before multiplication and stop faultCount 
and print the count
*/
void goodCase()
{
int i;
int ret;
s arrays[100000];
    	 printf("\nVERSION 2 GOOD CASE\n");

	 ret = syscall(__NR_faultCount,0);
         for(i=0;i<100000;i++)
                {                
                        arrays[i].a =1;
                        arrays[i].b = 1;
                }

        for(i=0;i<100000;i++)
                {
                        arrays[i].c = arrays[i].a*arrays[i].b;
                }
        ret = syscall(__NR_faultCount,1);
        printf("Fault count :: %d\n",ret);

}

/*
Name     :: main
Input    :: None
return's :: 0
This function calls badCase and goodCase
*/
int main(void) 
{ 

	badCase();
	goodCase();   

	return 0;
}

