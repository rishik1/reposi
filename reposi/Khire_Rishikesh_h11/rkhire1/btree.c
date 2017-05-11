#include "btree.h"


data* createData(float val)
{
  data* d = (data*)malloc(sizeof(data));
  d->val = val;
  return d;
}


leaf* createLeaf(data *d)
{
  leaf* l = (leaf*)malloc(sizeof(leaf));
  l->d = d;
  l->right = NULL;
  l->left = NULL;
  return l;
}

btree* createBtree()
{
  btree *tree = (btree*)malloc(sizeof(btree));
  tree->root = NULL;
  return tree;
}

void addLeaf(btree* bt,data *d)
{
	leaf *lf = createLeaf(d);
	if(bt->root == NULL)
	{
		bt->root = lf;
	}
	else
	{
		leaf *l = findPos(bt->root,d->val);
		if(l->left == NULL && (l->d->val > d->val))
		l->left =lf;
		else if(l->right == NULL && (l->d->val < d->val))
		l->right =lf;
	}
}

leaf* findPos(leaf *l,float val)
{
		if(l->d->val > val)
		{
			if(l->left == NULL)
			{
				return(l);
			}
			else
			l = findPos(l->left,val);
		}
		else
		{
			if(l->right == NULL)
			{
				return(l);
			}
			else
			l = findPos(l->right,val);
		}

}
void printInOrder(btree *t)
{
	printInOrder_r(t->root);
}
void printInOrder_r(leaf *l)
{
	 if(l != NULL)
        {
                printInOrder_r(l->left);
                printf("%f\n",l->d->val);
                printInOrder_r(l->right);
        }

}

btree* InOrderMerge(btree *t,btree *t1)
{
	btree *res =createBtree();
        InOrderMerge_r(t->root,res);
	InOrderMerge_r1(t1->root,res);

return(res);
}        

void InOrderMerge_r(leaf *l,btree *res)      
{                
        if(l != NULL)
        {
                InOrderMerge_r(l->left,res);
                addLeaf(res,l->d);
                InOrderMerge_r(l->right,res);
        }

}
void InOrderMerge_r1(leaf *l,btree *res)        
{                
	if(l == NULL)
	return;

         if(l != NULL)
        {                       
               InOrderMerge_r1(l->left,res);
		leaf *lf = seach(res,l->d->val);

		if(lf == NULL)
		{
                	addLeaf(res,l->d);
		}
                InOrderMerge_r1(l->right,res);     
        }

}



void  printBound(btree *t,float lower,float upper)
{
        printBound_r(t->root,lower,upper);
}           


void  printBound_r(leaf *l,float lower,float upper)
{
         if(l != NULL)
        {
		if(l->left !=NULL)
		{
			if(l->d->val >= lower)
               			 printBound_r(l->left,lower,upper);
		}

		if(l->d->val <= upper && l->d->val >= lower)
                printf("%f\n",l->d->val);
		
		if(l->right !=NULL)
		{
			if(l->d->val <= upper)
			{
               	 		printBound_r(l->right,lower,upper);
			}
		}
        }

}

void printPostOrder(btree *t)
{
	printPostOrder_r(t->root);
}

void printPostOrder_r(leaf *l)
{
	 if(l != NULL)
        {
                printf("%f\n",l->d->val);
		printPostOrder_r(l->left);
                printPostOrder_r(l->right);
        }

}

void printPreOrder(btree *t)
{
	printPreOrder_r(t->root);
}

void printPreOrder_r(leaf *l)
{
	if(l != NULL)
        {
                printf("%f\n",l->d->val);
                printPreOrder_r(l->left);
                printPreOrder_r(l->right);
        }

}

float getMin(btree *t)
{
	if(t->root->left == NULL)
	return(t->root->d->val);

	leaf *lf = getMin_r(t->root);
	return(lf->d->val);

}

leaf* getMin_r(leaf *l)
{

	if(l->left != NULL)
	{
		l = getMin_r(l->left);
		return(l);
	}
	return(l);

}

float getMax(btree *t)
{
	if(t->root->right == NULL)
	{
		return(t->root->d->val);
	}
	leaf *lf = getMax_r(t->root);
	return(lf->d->val);
}

leaf* getMax_r(leaf *l)
{
	if(l->right != NULL)
	{
		l = getMax_r(l->right);
	}
return(l);
}

void cleanTree(btree *t)
{
	cleanTree_r(t->root);
	free(t);

}

void cleanTree_r(leaf *l)
{
	if(l!=NULL)
	{
		cleanTree_r(l->left);
		cleanTree_r(l->right);
		free(l->d);
		free(l);
	}
}

void cleanTreeMerge(btree *t)
{
        cleanTreeMerge_r(t->root);
        free(t);
}

void cleanTreeMerge_r(leaf *l)
{
        if(l!=NULL)
        {
                cleanTreeMerge_r(l->left);
                cleanTreeMerge_r(l->right);
                free(l);
        }
}

leaf* seach(btree *t,float val)
{
	if(t->root == NULL)
	return(NULL);

	if(t->root->d->val == val)
	return(t->root);

	leaf *lf = search_r(t->root,val);
	return(lf);
}

leaf* search_r(leaf *l,float val)
{

	if(l == NULL)
	return(NULL);

	if(l->d->val == val)
	return(l);

	else if(l->d->val > val)
	{
		search_r(l->left,val);
	}
	else if(l->d->val < val)
	{
		search_r(l->right,val);
	}

}

