/*
Rishikesh Khire
OS project 5
class cs550 -04 
*/
#include<stdio.h>
#include<stdlib.h>
#include "time_functions.h"
#include<pthread.h>
#define MAX 10

time_t secs;
unsigned short mmm;
int x;
int y; 
pthread_mutex_t monitor = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t canWrite = PTHREAD_COND_INITIALIZER;
pthread_cond_t canRead = PTHREAD_COND_INITIALIZER;
pthread_mutex_t readMonitor = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t writeMonitor = PTHREAD_MUTEX_INITIALIZER;

int activeReader=0;  //active readers
int activeWriter=0;  //active writers
int waitingReader=0;  //waiting readers
int waitingWriter=0;  //waiting writers
int ReadCount, WriteCount;
/**
Name :: begin Write
Input :: int write_no
Return's :: NONE
This function writes the value to database variable 
and print the value to console
The beginWrite lock the awctiveWriter ar waitingWriter wr till initial writer doe not go into critical section
so that one writer will write at a time and other will wait till then
other writers and readers can come in monitor lock section  when one writer is in critical section and 
will wait for the writer to complete the critical section 
The critical section is also put in a seperate lock so that if one writer is writing others cannot acces 
and make the printf statement thread safe
*/
void beginWrite(int write_no)
{
	long int ss;
	//int wrt_no = *write_no;
	// check in 
	pthread_mutex_lock(&monitor); 
	while ( activeWriter + activeReader > 0) 
	{ 		// check if safe to write 
			// if any readers or 
			// writers, wait 
		waitingWriter++; 
		pthread_cond_wait(&canWrite,&monitor); 
		waitingWriter--; 
	} 

	activeWriter++; 
	pthread_mutex_unlock(&monitor);
 	
	pthread_mutex_lock(&writeMonitor);
	get_wall_clock(&secs, &mmm);
	ss = (long)secs;
	printf("\n *** DB value set to: %ld: %u by writer number: %d ",ss,mmm,write_no);
	pthread_mutex_unlock(&writeMonitor);

}
/**
Name                                    :: endWrite
Input                                   :: NONE
RETURN"S                                :: NONE
This function will put a lock on activeWriter ar waitingWriter wr usage when a writer is exiting so that other readers and writer wont update it valuie
this will wakeup the writers with priority if any
else will wake up the readers
*/
void endWrite()
{
	pthread_mutex_lock(&monitor);
        activeWriter--;
        if (waitingWriter > 0)                     // give priority to other writers
                pthread_cond_signal(&canWrite);
        else if (waitingReader > 0)
                pthread_cond_broadcast(&canRead);

        pthread_mutex_unlock(&monitor);

}
/**
Name    :: beginRead
INput   :: int readno gives that which reader thread is running
Return's:: NONE
This function writes the value to database variable 
and print the value to console
The beginRead lock the activeWriter ar waitingWriter wr till initial reader does not go into critical section
so that once Reader will read and other writer if comes after reader in C.S will wait till then
other writers and readers can come in monitor lock section  when one writer is in critical section and 
will wait for this READER to complete the critical section 
The critical section is also put in a seperate lock so that if one READER is Reading others cannot acces 
and make the printf statement thread safe

*/
void beginRead(int read_no)
       {
	long int ss;
	//int rd_no = x;
       	pthread_mutex_lock(&monitor);
	
	       //writer has priority
             while (activeWriter+waitingWriter>0)
             {
		//check if there is safe to read, wait if there is writer waiting
                    waitingReader++;				      //accumulate readers
                    pthread_cond_wait(&canRead,&monitor);    //wait for permission
                    waitingReader--; 				    //update waiting readers
             }
      
       activeReader++;
        pthread_mutex_unlock(&monitor);			 // allow access to the critical section
       							//here you get the DB value from wherever it is
	
	pthread_mutex_lock(&readMonitor);
	     ss = (long)secs;
             printf("\n >>> DB value read =: %ld:%u  by reader number: %d",ss,mmm,read_no);
	pthread_mutex_unlock(&readMonitor);
	

} 					   // end of BeginRead     

/**
Name       ::endRead
Input      :: NONE
RETURN"S   :: NONE
This function will put a lock on activeWriter waitingWriter wr ar so that other thread cannot change it 
This decrement the reader and will make sure that if any writer is waiting it will be wake up on priority
and reader will wait  and at last release the lock so that other threads reader or writer can go on and wait for 
respective conditions to become true
*/
void endRead()
{
       pthread_mutex_lock(&monitor);
       activeReader--;
       if (activeReader+waitingReader==0 && waitingWriter >0)
                pthread_cond_signal(&canWrite);
        pthread_mutex_unlock(&monitor);

}
/**
NAME    :: reader
Input   :: args if any but none here
Return's   :: NONE
This is the reader thread function when reader thread created then this function will be evoke for 
each thread .
It call begin read and endRead for 10 times each and we put the user given dealy at the end of each
*/
void* reader(void *arg)
{
int k;
int read_no;
time_t delay;
read_no = x++; 
	printf("\nRead of thread numnber : %d ",read_no);
	for(k=0;k<10;k++)
	{
		beginRead(read_no);
		endRead();
		millisleep(ReadCount);	
	}
pthread_exit(0);
return ;

}
/**
NAme   :: writer
Input  :: args if any but none here
This is the writer thread function whenever writer thread created then this function will be evoke for 
each thread .
It call beginWrite and endWrite for 10 times each and we put the user given dealy at the end of each
*/
void* writer(void *arg)
{
int k;
int write_no ;
time_t delay;
write_no =y++;

	printf("\nWrite of thread numnber : %d",write_no);
	for(k=0;k<10;k++)
	{
		beginWrite(write_no);
		endWrite();
		millisleep(WriteCount);
	}
pthread_exit(0);
return ;
}
/**
Main fucntion which takes 
Input :: argc int as number of arguments , char array for input arguments
1st argument  = number of reader thread
2nd argumnet = number of writer thread
3rd argument = DELAY IN MILLISECONDS for Reader
4th argument = DELAY IN MILLISECONDS FOr Writer
Create thread reader and writer
wait for reader and writer threads to complete
Exit
*/
int main(int argc,char *argv[])
{
	int r = atoi(argv[1]);
	int w = atoi(argv[2]);
	int i,j;
	int m,n;
	
	

	pthread_t *read = (pthread_t *)malloc(r*sizeof(pthread_t));
	pthread_t  *write = (pthread_t *)malloc(w*sizeof(pthread_t));
	ReadCount = atoi(argv[3]);
	WriteCount = atoi(argv[4]);
	for(i=0;i<w;i++)
        {
                pthread_create(&write[i],NULL,writer,NULL);
        }
		
	for(j=0;j<r;j++)
	{
		pthread_create(&read[j],NULL,reader,NULL);
	}

	for(m=0;m<w;m++)
	{
		pthread_join(write[m],NULL);
	}
	for(n=0;n<r;n++)
        {
                pthread_join(read[n],NULL);
        }
	printf("\n");

//	system("pause");
return 0;
}
