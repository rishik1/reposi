/*
Name :Rishikesh Khire
Class :cs550 -4
project1
compile by :: g++ os1.cpp
run        :: ./a.out
In Visual studio please Write
NOTE  :: Visual studio results with consideration of using at top of File
#define _CRT_SECURE_NO_DEPRECATE 
*/

#include<stdio.h>
#include <iostream>
#include "time_functions.h"
using namespace std;

void charbychar(char input[],char output[])
{
	char ch;
	int value;
	FILE* fp;
	FILE* fp1;
	double diff_wall;
        double diff_CPU;
	fp=fopen(input,"r");

	if(fp == NULL)
	{
		cout << "Cant open the file " << input << endl;
	}	
	
	
	fp1=fopen(output,"w+");
	
	if(fp == NULL)
	{
		cout<<" Cant open the file "<<output<<endl;
	}
	start_timing();

	ch = fgetc(fp);
	while(!feof(fp))
	{
		fputc(ch,fp1);
		ch = fgetc(fp);
	}
	stop_timing();

	diff_wall = get_wall_clock_diff();
	diff_CPU = get_CPU_time_diff();
	
	cout<<"the time from 'ftime' for compound 1 is "<< diff_wall <<" seconds"<<endl;
	cout<<"the time from CPUtime" << diff_CPU << "seconds"<<endl;  // good for seconds of wall clock time
	

fclose(fp);
fclose(fp1);

}

void linebyline(char input[],char output[])
{
	char line[85];
	FILE* fp;
	FILE* fp1;
	double diff_wall;     
        double diff_CPU;       

        fp=fopen(input,"r");
        if(fp == NULL)
        {
                cout<<"Can't open the file "<<input;
        }       
        

        fp1=fopen(output,"w+");

        if(fp == NULL)
        {
                cout<<" Can't open the file "<<output;
        }

	start_timing();

	while(fgets(line,81,fp)!=NULL)
		{
			fputs(line,fp1);
		}

	stop_timing();
	
	diff_wall = get_wall_clock_diff(); 
        diff_CPU = get_CPU_time_diff();
        
	cout<<"the time from 'ftime' for compound 1 is "<< diff_wall <<" seconds"<<endl;         
        cout<<"the time from CPUtime" << diff_CPU << "seconds"<<endl;  // good for seconds of wall clock time    

fclose(fp);
fclose(fp1);

}

int main(void)
{
	char inputfile[20];
	char outputfile[20];
	int mode;

	cout<< endl <<"Enter the file name to be read"<<endl;
	cin>>inputfile;
	
	cout<<endl<< "Enter the name of the outputfile :::"<<endl;
	cin>>outputfile;

	cout<<" MODE of the file"<<endl;
	cout<<" 0 - For character by character"<<endl;
	cout<<" 1 - For line by line"<<endl;
	cout<<" Enter the mode :: "<<endl;
	cin>>mode;	

switch(mode)
{
	case 0: charbychar(inputfile,outputfile);
		break;

	case 1: linebyline(inputfile,outputfile);
		break;


}


return 0;
}




