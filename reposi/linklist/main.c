#include "employee.h"
#include "linklist.h"
#define debug 0

int main(int argc, char* argv[])
{


	int i = 0;

        linkList *s = NULL;
        if(argc > 4)
        {
                printf("more input tha expected\n");
        }

        if(debug ==1)
        {
        printf("number of arguments::::%d\n",argc);
                for(i=0; i < argc ; i++)
                {
                        printf("arguments are ::%s\n",argv[i]);
                }
        }

        s  = loadData(argv[1]);
	printList(s);	

	int count = countFirstName(s,argv[2]);
	printf("%d\n",count);
	
	removeLastName(s,argv[3]);
	printList(s);
	clean_emp(s);	

  return 0;
}


