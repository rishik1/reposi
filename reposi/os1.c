#include<stdio.h>
#include<stdlib.h>
#include "time_functions.h"

void charbychar(char input[],char output[])
{
	char ch;
	int value;
	printf("filename :%s",input);

	FILE* fp=fopen(input,"r");
	if(fp == NULL)
	{
		printf("\nCan't open the file %s",input);
	}	
	
	
	FILE* fp1=fopen(output,"w");
	
	if(fp == NULL)
	{
		printf("\n Can't open the file %s",output);
	}
	start_timing();

	while((ch= fgetc(fp)) != EOF)
	{
		fputc(ch,fp1);
	}
	stop_timing();
	printf("the time from 'CPUtime' = %10.3f seconds\n",get_CPU_time_diff());  // good for seconds of wall clock time
	

fclose(fp);
fclose(fp1);

}

void linebyline(char input[],char output[])
{
	char line[82];
        printf("filename :%s",input);

        FILE* fp=fopen(input,"r");
        if(fp == NULL)
        {
                printf("\nCan't open the file %s",input);
        }       
        

        FILE* fp1=fopen(output,"w");

        if(fp == NULL)
        {
                printf("\n Can't open the file %s",output);
        }
	start_timing();
	while(1)
	{
		if(fgets(line,80,fp)!=NULL)
		{
			fputs(line,fp1);
		}
		else
		break;
	}
	stop_timing();

	printf("the time from 'CPUtime' =  %10.3f seconds\n",get_CPU_time_diff());  // good for seconds of wall clock time

fclose(fp);
fclose(fp1);

}

int main(void)
{
	char inputfile[20];
	char outputfile[20];
	int mode;

	printf("\n Enter the file name to be read :::\n");
	scanf("%s",inputfile);
	
	printf("\n Enter the name of the outputfile ::: \n");
	scanf("%s",outputfile);

	printf("\n MODE of the file\n");
	printf("\n 0 - For character by character\n");
	printf("\n 1 - For line by line\n");
	printf("\n Enter the mode :: \n");
	scanf("%d",&mode);	

switch(mode)
{
	case 0: charbychar(inputfile,outputfile);
		break;

	case 1: linebyline(inputfile,outputfile);
		break;


}


return 0;
}
