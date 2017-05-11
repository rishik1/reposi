#include <stdio.h>
#include <stdlib.h>
#include "time_functions.h"

void read_char(char src[], char dest[])
{
	char c;
        FILE* fp = fopen(src,"r");
        FILE* fp1 = fopen(dest,"w+");
	if(fp == NULL)
	{
		printf("Cannot open the file\n");
	}
        start_timing();
	while(c = fgetc(fp) != EOF)
        {
                fputc(c,fp1);
		c = fgetc(fp);
        }
	stop_timing();
	printf("Character by character wall clock time is %0.5f seconds.\n",get_wall_clock_diff());
	printf("Character by character CPU time is %0.5f seconds.\n",get_CPU_time_diff());
	fclose(fp);
	fclose(fp1);
}

void read_line(char src[], char dest[])
{
        FILE* fp = fopen(src,"r");
        FILE* fp1 = fopen(dest,"w+");
	if(fp == NULL)
	{
		printf("Cannot open the file\n");
	}
        char c[80];
	start_timing();
        while(fgets(c,80,fp) != NULL)
        {
                fputs(c,fp1);
        }
	stop_timing();
	printf("Line by Line wall clock time is %0.5f seconds.\n",get_wall_clock_diff());
	printf("Line by line CPU time is %0.5f seconds.\n", get_CPU_time_diff());
	fclose(fp);
	fclose(fp1);
}


int main(int argc, char* argv[])
{
	int mode = atoi(argv[1]);
	printf("%d \n",mode);
	char src[20];
	char dest[20];
	printf("Enter source filename : \n");
	scanf("%s",src);
	printf("Enter destination filename : \n");
	scanf("%s", dest);
	switch(mode)
	{
		case 0 :read_char(src,dest);
			break;
		case 1 : read_line(src,dest);
			break;
	}
	return 0;
}
