#include "dlinklist.h"


dlinklist* createlinklist()
{
  dlinklist* ll = (dlinklist*)malloc(sizeof(dlinklist));
  ll->head = NULL;
  ll->tail = NULL;
  return ll;
}


node* createNode(data *d)
{
  node *n = (node*)malloc(sizeof(node));
  n->d = d;
  n->next = NULL;
  n->prev = NULL;
  return n;
}

data* createData(char val)
{
  data *d = (data*)malloc(sizeof(data));
  d->val = val;
  return d;
}



//create a new node, set the data pointer, and add to front of dlinklist
void addFront(dlinklist *ll,data *d)
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
		ll->head->prev = temp;
		ll->head = temp;
	}

}


//create a new node, set the data pointer, and add to back of dlinklist
void addBack(dlinklist *ll,data *d)
{
	node *temp = createNode(d);
	if(ll->head == NULL && ll->tail == NULL)
	{
		ll->head = temp;
		ll->tail = temp;
	}
	else
	{
		ll->tail->next = temp;
		temp->prev = ll->tail;
		ll->tail = temp;
	}
}



//print values in the data structures in the dlinklist
void printList(dlinklist *ll)
{
	node *temp = ll->head;

	while(temp!= NULL)
	{
		printf("%c",temp->d->val);
		temp =temp->next;
	}
	printf("\n");
}

//count the number of elements in the dlinklist and return the count
int listSize(dlinklist *ll)
{
	node *temp =ll->head;
	int count = 0;
	while(temp!=NULL)
	{
		count++;
		temp=temp->next;
	}
return count;

}


//remove node from the front of the list and return the data element
data* popFront(dlinklist *ll)
{
	node *temp = ll->head;
	data *dat =NULL;
	if(ll->head == NULL)
	return (NULL);

	if(ll->head == ll->tail)
	{
		dat = temp->d;
		free(ll->head);
		ll->head=NULL;
		ll->tail=NULL;
		return(dat);
	}
	else
	{
		ll->head = ll->head->next;
		dat = temp->d;
		free(temp);
		return(dat);
	}

}


//remove node from the front of the list and return the data element
data* popBack(dlinklist *ll)
{
	node *temp =ll->tail;
	data *dat = NULL;	
	if(ll->head == NULL)
	return NULL;

	if(ll->head == ll->tail)
	{
		ll->head = NULL;
		ll->tail = NULL;
		dat = temp->d;
		free(temp);
		return(dat);
	}
	else
	{
		ll->tail = temp->prev;
		ll->tail->next = NULL;
		dat = temp->d;
		free(temp);
		return(dat);
	}

}


//free all memory in the dlinklist
void cleanList(dlinklist *ll)
{
        node *temp =NULL;    
        while(ll->head != NULL) 
        {
                temp=ll->head;
                free(ll->head->d);
                ll->head=ll->head->next;
                free(temp);
        }
        ll->tail=NULL;
        free(ll);
	//ll->head =NULL;
}



//reverse the elements of the dlinklist
void reverseList(dlinklist *ll)
{
	data *dat = NULL;
	dlinklist *s = createlinklist();

	while(ll->head!=NULL)
	{
		dat = popFront(ll);
		addFront(s,dat);
	}
	cleanList(ll);
	ll->head = s->head;
	ll->tail = s->tail;
}

//search for element in dlinklist, if found remove.
void searchRemove(dlinklist *ll,char val)
{

	node *temp =ll->head;
	node *prev1 = NULL;
	if(ll->head == NULL)
	{
		printf("\n List is empty");

	}
	else if(temp->next == NULL && temp->d->val == val)
	{
		free(ll->head->d);
		free(ll->head);
		ll->head = NULL;
		ll->tail = NULL;
	}
	else{

		while(temp!=NULL)
		{
			if(ll->head == temp && temp->d->val == val)
			{
				temp = ll->head->next;
				free(ll->head->d);
				free(ll->head);
				ll->head = temp;
			}
			if(temp->d->val == val)
			{

				if(temp->next == NULL)
				{
					free(temp->d);
					free(temp);
					ll->tail = prev1;
					ll->tail->next = NULL;
					break;
				}
				prev1 = temp->prev;
				prev1->next = temp->next;
				temp->next->prev =prev1;
				free(temp->d);
				free(temp);
				temp = prev1->next;
				continue;
			}
		prev1 = temp;	
		temp = temp->next;
		}

	    }

}

