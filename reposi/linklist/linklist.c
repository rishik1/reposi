
#include "linklist.h"

/*
Name		:: createlinkList
Input		:: None
Return's	:: pointer to linkList
The Function reserve space for a new linklist, set initial variables to NULL, and return the linklist
*/
linkList* createlinkList()
{

	linkList *s= (linkList *)malloc(sizeof(linkList));
	s->head =NULL;
	s->tail =NULL;
return s;
}

/*
Name		:: createNode
Input		:: pointer to data
Return's	:: pointer to node
Reserve space for new node, set variables and return
*/
node* createNode(data *d)
{

	node *temp =(node *)malloc(sizeof(node));
	temp->next = NULL;
	temp->d = d;

return temp;
}

/*
Name            :: createData
Input           :: pointer to char as firstname ,pointer to char as lastname ,integer as age and integer as id
Return's        :: pointer to data
The function reserve space for a new data struct, set variables firstname lastname age and id and return data pointer
*/
data* createData(char *first, char *last, int age, int id) 
{
	data *d = (data *)malloc(sizeof(data));
	strcpy(d->first,first);
	strcpy(d->last,last);
	d->age = age;
	d->id = id;

return(d);
}

/*
Name            :: addFront
Input           :: pointer to linkList and pointer to data
Return's        :: None
The function createc a new node, set the data pointer, and add to front of linklist
*/
void addFront(linkList *ll,data *d)
{

	node *temp = createNode(d);

	if(ll->head == NULL)
	{
		ll->head = temp;
		ll->tail = temp;
	}
	else
	{
		temp->next = ll->head;
		ll->head = temp;
	}
}

/*
Name            :: addBack
Input           :: pointer to linkList and pointer to data
Return's        :: None
The function create a new node, set the data pointer, and add to back of linklist
*/
void addBack(linkList *ll,data *d)
{
	node *temp= createNode(d);


	if(ll->head== NULL)
	{
		ll->head = temp;
		ll->tail = temp;
	}
	else
	{
		ll->tail->next = temp;
		ll->tail = temp;
		ll->tail->next = NULL;
	}

}

/*
Name            :: printList
Input           :: pointer to linkList
Return's        :: None
Print values in the data structures in the linklist
*/
void printList(linkList *ll)
{
	node *temp = ll->head;
	if(ll->head ==NULL)
	{	
		printf("\nList is empty");
	}
	else if(temp->next == NULL)
	{
		printf("%s %s %d %d\n",temp->d->first,temp->d->last,temp->d->age,temp->d->id);
	}
	else{
		while(temp!= NULL)
		{
			printf("%s %s %d %d\n",temp->d->first,temp->d->last,temp->d->age,temp->d->id);
			temp = temp->next;
		}
       	   }

}

/*
Name            :: listSize
Input           :: pointer to linkLitst
Return's        :: integer
the function count the number of elements in the linklist and return the count
*/
int listSize(linkList *ll)
{
	int count = 0;
	node *temp = ll->head;
	if(ll->head == NULL)
	{
		count = 0;
	}
	else{
		while(temp!=NULL)
		{
			count++;
			temp= temp->next;
		}
	   }
	return(count);

}


/*
Name            :: popFront
Input           :: pointer to linkList 
Return's        :: pointer to data
The function remove node from the front of the list and return the data element
*/
data* popFront(linkList *ll)
{
	if(ll->head ==  NULL)
	{
		data *t = NULL;
		return(t);
	}
	else
	{
		node *temp = ll->head;
		data *dat = temp->d;
		temp = temp->next;
		free(ll->head);
		ll->head = temp;
	return(dat);
	}
}


/*
Name            :: popBack
Input           :: pointer to linkList
Return's        :: pointer to Data
The function remove node from the front of the list and return the data element
*/
data* popBack(linkList *ll)
{
	//float value;
	if(ll->head == NULL)
	{
		data *t = NULL;
		return(t);
	}
	else
	{

	node *temp = ll->head;
	//node *prev = NULL;

	while(temp->next->next!=NULL)
	{
		temp = temp->next;
	}

		data *dat = temp->next->d;
		ll->tail = temp;
		temp->next = NULL;
	return(dat);
	}
}


/*
Name            :: cleanList
Input           :: pointer to linkList
Return's        :: None
Free all memory in the linklist
*/
void cleanList(linkList *ll)
{
	node *temp = NULL;

	while(ll->head !=NULL)
	{
		temp = ll->head;
		free(ll->head->d);
		ll->head = ll->head->next;
		free(temp);
	}
ll->head = NULL;
ll->tail = NULL;
free(ll);

}

/*
Name            :: reverseList
Input           :: pointer to linkList
Return's        :: None
Reverse the elements of the linklist
*/
void reverseList(linkList *ll)
{

	//node *temp = ll->head;
	linkList *new_list = createlinkList();
	data *dat = NULL;
	while(ll->head !=NULL)
	{
		dat = popFront(ll);
		addFront(new_list,dat);	
	}

	ll->head = new_list->head;
	ll->tail = new_list->tail;

}

/*
Name            :: countFirst
Input           :: pointer to linkList,pointer to char 
Return's        :: integer
The function count the number of occurence of first name in the list
*/
int countFirst(linkList *ll,char *first)
{
	int count = 0;
        node *temp = ll->head;
        while(temp!=NULL)
        {
                if(strcmp(temp->d->first,first)==0)
                {
                        count++;        
                }
        temp = temp->next;
        }       
return (count);
}

/*
Name            :: searchRemoveFirst
Input           :: pointer to linkList and pointer to char
Return's        :: None
The function search for firstname in linkList, if found remove those occurence
*/
void searchRemoveFirst(linkList *ll,char *first)
{
	node *prev = NULL;
	node *temp = ll->head;

	if(temp == NULL)
	{
		printf("\no data in list");
	}
	else if(temp->next == NULL && strcmp(temp->d->first,first)==0)
	{
		free(ll->head);
		ll->tail = NULL;
		ll->head = NULL;
	}
	else{
	
		while(temp!=NULL)
		{

			if (strcmp(temp->d->first,first)==0 && (ll->head == temp))
                        {
					prev = temp;
					temp=temp->next;
					free(prev->d);
					free(prev);
                       			ll->head = temp;
					prev =NULL;
					continue;
                        }       
        

			if (strcmp(temp->d->first,first) == 0)
			{

					prev->next = temp->next;
					if(temp->next == NULL)
                                        {
                                                ll->tail = prev;
						prev->next = NULL;
						free(temp->d);
						free(temp);
                                                break;
                                        }
					free(temp->d);
					free(temp);
					temp = prev->next;
					continue;
			}	
	
			prev =temp;
			temp =temp->next;
		}
           }

}
/*
Name            :: searchRemoveLast
Input           :: pointer to linkList and pointer to last
Return's        :: None
The function search for last name  in linkList, if found remove the occurence from list
*/
void searchRemoveLast(linkList *ll,char *last)
{
	node *prev = NULL;
	node *temp = ll->head;

	if(temp == NULL)
	{
		printf("\no data in list");
	}
	else if(temp->next == NULL && strcmp(temp->d->last,last) == 0)
	{
		free(ll->head);
		ll->tail = NULL;
		ll->head = NULL;
	}
	else{
	
		while(temp!=NULL)
		{

			if (strcmp(temp->d->last,last) == 0 && (ll->head == temp))
                        {
					prev = temp;
					temp=temp->next;
					free(prev->d);
					free(prev);
                       			ll->head = temp;
					prev =NULL;
					continue;
                        }       
        

			if (strcmp(temp->d->last,last) == 0)
			{

					prev->next = temp->next;
					if(temp->next == NULL)
                                        {
                                                ll->tail = prev;
						prev->next = NULL;
						free(temp->d);
						free(temp);
                                                break;
                                        }
					free(temp->d);
					free(temp);
					temp = prev->next;
					continue;
			}	
	
			prev =temp;
			temp =temp->next;
		}
           }

}

/*
Name            :: searchRemoveAge
Input           :: pointer to linkList and integer as age
Return's        :: None
The function search Age in linkList, if found remove the elemnts of its occurence
*/
void searchRemoveAge(linkList *ll,int age)
{
	node *prev = NULL;
	node *temp = ll->head;

	if(temp == NULL)
	{
		printf("\no data in list");
	}
	else if(temp->next == NULL && temp->d->age == age)
	{
		free(ll->head);
		ll->tail = NULL;
		ll->head = NULL;
	}
	else{
	
		while(temp!=NULL)
		{

			if (temp->d->age == age && (ll->head == temp))
                        {
					prev = temp;
					temp=temp->next;
					free(prev->d);
					free(prev);
                       			ll->head = temp;
					prev =NULL;
					continue;
                        }       
        

			if (temp->d->age == age)
			{

					prev->next = temp->next;
					if(temp->next == NULL)
                                        {
                                                ll->tail = prev;
						prev->next = NULL;
						free(temp->d);
						free(temp);
                                                break;
                                        }
					free(temp->d);
					free(temp);
					temp = prev->next;
					continue;
			}	
	
			prev =temp;
			temp =temp->next;
		}
           }

}

/*
Name            :: searchRemoveId
Input           :: pointer to linkList and integer as id
Return's        :: None
The function search for element in linkList whose id is equal to inputed id, if found then remove those element
*/
void searchRemoveId(linkList *ll,int id)
{
	node *prev = NULL;
	node *temp = ll->head;

	if(temp == NULL)
	{
		printf("\no data in list");
	}
	else if(temp->next == NULL && temp->d->id == id)
	{
		free(ll->head);
		ll->tail = NULL;
		ll->head = NULL;
	}
	else{
	
		while(temp!=NULL)
		{

			if (temp->d->id == id && (ll->head == temp))
                        {
					prev = temp;
					temp=temp->next;
					free(prev->d);
					free(prev);
                       			ll->head = temp;
					prev =NULL;
					continue;
                        }       
        

			if (temp->d->id == id)
			{

					prev->next = temp->next;
					if(temp->next == NULL)
                                        {
                                                ll->tail = prev;
						prev->next = NULL;
						free(temp->d);
						free(temp);
                                                break;
                                        }
					free(temp->d);
					free(temp);
					temp = prev->next;
					continue;
			}	
	
			prev =temp;
			temp =temp->next;
		}
           }

}



