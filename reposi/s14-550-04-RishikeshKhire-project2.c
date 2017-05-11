#include<stdlib.h>
#include<stdio.h>
#include<pthread.h>
#include<semaphore.h>
#include "time_functions.h"


char buff[10][90];// 2d array of buffer
int lines =0;
pthread_t produce1;
pthread_t consume1;
int pro_index =0;
int con_index=0;

sem_t empty_mutex; //producer semaphore
sem_t full_mutex; //consumer semaphore
sem_t mutex;	// communication semaphore	

void *producer(void *p)
{
	int i=0;

	FILE *fp =(FILE *)p;
	while(1)
	{

		if(fgets(buff[pro_index],80,fp)!=NULL)		// reading the file into buffer 
		{
		sem_wait(&empty_mutex);                 // decrementing producer mutex and making it producer wait when buffer empty
                 sem_wait(&mutex);                       // communication sempahore make to wait when producer in crictical section


			pro_index++;

			if(pro_index ==10)			// circular buffer
			{				
				pro_index=0;			// producer index set to zero when reached limit =10	
			}
		sem_post(&mutex);				// communication sempahore is incremented when producer out of citical section 
		sem_post(&full_mutex);				// semaphore consummer is incremented when producer reads data inot buffer 
		}
		else
		{
			buff[pro_index][0]='$';

			sem_post(&mutex);               // semaphore consummer is incremented when producer reads data inot buffer
			sem_post(&full_mutex);		// communication sempahore is incremented when producer out of citical section
			break;
		}
	}
return(NULL);
	
}

void *consumer(void *p)
{
        FILE *fp1 =(FILE *)p;

        while(1)
	{
	
		sem_wait(&full_mutex);		// decrementing Consmer mutex and making it producer wait when buffer full
                sem_wait(&mutex);		 // communication sempahore make to wait when producer in crictical section

		
		if(buff[con_index][0]=='$')
		{
			break;
		}
		if(fputs(buff[con_index],fp1)>0)
		{
			con_index++;

			if(con_index ==10)
			{
				con_index=0;
			}
		}
		sem_post(&mutex);		// communication sempahore is incremented when producer out of citical section
        	sem_post(&empty_mutex);		 // semaphore producer is incremented when producer reads data from buffer
	}  

return(NULL);
}




int main(int argc,char* argv[])
{
	FILE *fp1;
	FILE *fp;

	double clock_diff;
	double cpu_diff;

	if(argc < 2)
	{
		printf("\n improper number of arguments\n");
		exit(0);
	}

	sem_init(&empty_mutex,0,10);
	sem_init(&full_mutex,0,0);
	sem_init(&mutex,0,1);
	
	fp = fopen(argv[1],"r");
	if(fp == NULL)
	{
		printf("\n can't open input file\n");
		exit(0);
	}
	
	fp1 = fopen(argv[2],"w+");
        if(fp1 == NULL)
	{
                printf("\n can't open output file\n");
		exit(0);
 	}
	
	start_timing();

	pthread_create(&produce1,NULL,&producer,fp);
	pthread_create(&consume1,NULL,&consumer,fp1);

	pthread_join(produce1,NULL);
	pthread_join(consume1,NULL);
	

	stop_timing();			

	clock_diff =(double) get_wall_clock_diff();
	cpu_diff = (double)get_CPU_time_diff();

	printf("WALL CLOCK TIMING::: %lf seconds \n ",clock_diff);
	printf("the time from 'getCPUtime' %lf seconds\n",cpu_diff);	

	sem_destroy(&empty_mutex);
  	sem_destroy(&full_mutex);
	sem_destroy(&mutex);
	
	pthread_exit(NULL);
	
return 0;

}
