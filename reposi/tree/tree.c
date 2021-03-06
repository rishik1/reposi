#include "tree.h"
#include<assert.h>


data* createData(float val)
{
  data* d = (data*)malloc(sizeof(data));
  d->val = val;
  return d;
}

//allocate space for tree struct and set vars
tree* createTree()
{

	tree *t = (tree*)malloc(sizeof(tree));
	t->root = NULL;
	t->q = createQueue();
	return (t);
}

//malloc new leaf and set vars
leaf* createLeaf(data *d)
{
	leaf *l = (leaf*)malloc(sizeof(leaf));
	l->right = NULL;
	l->left = NULL;
	l->d = d;
return(l);
}

//add a leaf to the tree.  leaf must stay complete at all times.
void addLeaf(tree *t,data *d)
{
	leaf *l = NULL;
	leaf *temp = NULL;
		if(t->root == NULL)
		{
			l = createLeaf(d);
			t->root = l;
			queuePush(t->q,l);
		}
		else
		{
			 l = queueFront(t->q);
			if(l != NULL)
			{
				if(l->left == NULL)
				{
					temp = createLeaf(d);
					l->left = temp;
					queuePush(t->q,temp);
				}
				else if(l->right == NULL)
				{
					assert(l->right == NULL);
					temp = createLeaf(d);
					l->right = temp;
					queuePush(t->q,temp);
					queuePop(t->q);
				}
			}
		}

}

//get the size of the tree from recursive funtion
int treeSize(tree *t)
{
	int size = treeSize_r(t->root);
return size;
}
//calculates tree size by traversing tree recursively 
int treeSize_r(leaf *l)
{
int size;
	if(l ==NULL)
		return 0;
	else
	{
	size = treeSize_r(l->left) + treeSize_r(l->right) +1;
	return size;
	}

}

//print data in in order by calling recursive function
void inOrderPrint(tree *t)
{
	inOrderPrint_r(t->root);
}
// prints in order i.e left root right way by recursively traversing tree
void inOrderPrint_r(leaf *l)
{

	if(l != NULL)
	{
		inOrderPrint_r(l->left);
		printf("\n%f",l->d->val);
		inOrderPrint_r(l->right);
	}

}

//print data in pre order by calling recursive function
void preOrderPrint(tree *t)
{
	preOrderPrint_r(t->root);

}
//print data in pre order i.e root left right  way by recursively traversing
void preOrderPrint_r(leaf *l)
{
	if(l != NULL)
	{
		printf("\n%f",l->d->val);
		preOrderPrint_r(l->left);
		preOrderPrint_r(l->right);
	}

}

//print data in post order by calling recursive function
void postOrderPrint(tree *t)
{
	postOrderPrint_r(t->root);
}
// print data in post order i.e left right root way by recursively traversing 
void postOrderPrint_r(leaf *l)
{
	if(l != NULL)
	{
		postOrderPrint_r(l->left);
		postOrderPrint_r(l->right);
		printf("\n%f",l->d->val);
	}
}

/*
Name		:: treeClean
Input		:: pointer to tree
Return's	:: None
clean's memory allocation on heap by cleaning recursively
*/
void treeClean(tree *t)
{
	treeClean_r(t->root);
	queueClean(t->q);
	free(t);

}
// recursive cleaning
void treeClean_r(leaf *l)
{
	if(l != NULL)
	{
		treeClean_r(l->left);
		treeClean_r(l->right);

		free(l->d);
		free(l);
	}

}


// function search the last element of tree and return's it by breaking links to its parent node
leaf* breathFirstSearch(leaf *lf)
{
	leaf *l = NULL;
	queue *temp = createQueue();
	queue *temp1 = createQueue();
	queuePush(temp,lf);
	
	while((lf = queueFront(temp)) != NULL)
	{	
		
		queuePush(temp,lf->left);	
		queuePush(temp,lf->right); 
		l = lf;
		queuePush(temp1,lf);
		queuePop(temp);
	}
	

	 while((lf = queueFront(temp1)) != NULL)
        {       
                if(lf->left == l || lf->right == l) 
                {
                       break;       
                }     
		queuePop(temp1);  
        }

	if(lf->left == l)
	{
		lf->left =NULL;
	}
	if(lf->right == l)
	{
		lf->right = NULL;
	}
	queuePop(temp1);

queueClean(temp);
queueClean(temp1);

return(l);

}


//remove root. Tree must remain complete and can only be done through accessing the tree
leaf* removeRoot(tree *t)
{

	leaf *r = t->root;

	leaf *lf = breathFirstSearch(t->root);

	lf->left = t->root->left;
	lf->right =t->root->right;
	t->root->left =NULL;
	t->root->right =NULL;
	t->root = lf;
	

return(r);

}



//check to see if tree is balenced by calling recursive function.  if balanced return 1 and if not return -1.
int balancedTree(tree *t)
{
	int bal = balancedTree_r(t->root);
	return bal;

}
//find  if tree is balenced by traversing recursively.  if balanced return 1 and if not return -1.
int balancedTree_r(leaf *l)
{
		int left = treeSize_r(l->right);
		int right = treeSize_r(l->left);
		if(left == right)
		{
			return 1;
		}
		else{
		return -1;
		}
}

//cretaes array of float by putting all elements of tree in floating point array and returns it
float* treeArray(tree *t)
{
	int size = treeSize(t); 
	int i=0;
	float *array = (float*)malloc(size*sizeof(float));
	leaf *lf = NULL;
        queue *temp = createQueue();
        queuePush(temp,t->root);

		
	while((lf = queueFront(temp)) != NULL)
        {       
                queuePush(temp,lf->left);       
                queuePush(temp,lf->right); 
                array[i] = lf->d->val;
                queuePop(temp);
	i++;
        }
queueClean(temp);
return(array);

}
