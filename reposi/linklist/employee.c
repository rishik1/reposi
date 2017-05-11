#include "linklist.h"
#include "employee.h"

/*
Name            :: loadData
Input           :: pointer to cahr as filename
Return's        :: pointer to linkList
This function opens a file for reading.
Then it will load the data for each employee into a data element and load it into a linkList.
Finally the linkList is returned.
*/
linkList* loadData(char *fileName)
{

	char first1[128];
	char last1[128];
	int age1;
	int id1;

	FILE *fp = fopen(fileName,"r");

	linkList *ll = createlinkList();

        if(fp == NULL)
        {
                printf("Error Opening file or file not found!!!\n");
                exit(0);
        }
	data *element =NULL;

	 while(fscanf(fp,"%s %s %d %d",first1,last1,&age1,&id1)!=EOF)
        {
                element = createData(first1,last1,age1,id1);
                addBack(ll,element);
       
	         if(debug1 == 1)
                 {
                        printf("inputed value = %s %s %d %d",first1,last1,age1,id1);
                 }
        }
        if(debug1 ==1)
        {
                printList(ll);
        }
        fclose(fp);

return(ll);
}


/*
Name            :: countFirstName
Input           :: pointer to linkList and pointer to char as firstname
Return's        :: integer as count
This function counts everyone in the linkList who has the same first name as the input variable first.
The counted number is returned.
*/
int countFirstName(linkList *ll, char *first)
{
	int count = countFirst(ll,first);
	return count;
}


/*
Name            :: removeLastName
Input           :: pointer to linklist and pointer to char last
Return's        :: None
This function removes from the linkList anyone who has the same last name as the input variable last.
*/
void removeLastName(linkList *ll, char *last)
{

	searchRemoveLast(ll,last);

}


/*
Name            :: removeFirstName
Input           :: pointer to linkList and pointer to character
Return's        :: None
This function removes from the linkList anyone who has the same first name as the input variable last. 
*/
void removeFirstName(linkList *ll, char *first)
{

        searchRemoveFirst(ll,first);

}


/*
Name            :: clean_emp
Input           :: pointer to linkList
Return's        :: None
This function cleans all the memory allocations on heap
*/
void clean_emp(linkList *ll)
{
	cleanList(ll);
}



