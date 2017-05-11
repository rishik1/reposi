#ifndef _TREE_H_
#define _TREE_H_
#include <stdlib.h>
#include <stdio.h>
#include "queue.h"

/*
Structure tree has pointer to queue and pointer to leaf node root
*/
typedef struct{
  leaf *root;
  queue *q;
}tree;

/*
Name		:: createTree
Input		:: void
Return's	:: pointer to tree
allocate space for tree struct and set vars
*/
tree* createTree();


/*
Name		:: createTree
Input		:: pointer to tree
Return's	:: pointer to tree
malloc new leaf and set vars
*/
leaf* createLeaf(data *d);

/*
Name		:: createLeaf
Input		:: pointer to data
Return's	:: pointer to leaf created using data pointer
malloc data and set vars
*/
data* createData(float val);

/*
Name		:: addLeaf
Input		:: pointer to tree and pointer to data
add a leaf to the tree.  leaf must stay complete at all times.
*/
void addLeaf(tree *t,data *d);

/*
Name		:: treeSize
Input		:: pointer to tree
Return's	:: int as tree size
get the size of the tree from recursive function
*/
int treeSize(tree *t);
/*
Name		:: treeSize_r
Input		:: pointer to leaf
Return's	:: int
this function calculates size of tree recursively
*/

int treeSize_r(leaf *l);
/*
Name		:: inOrderPrint
Input		:: pointer to tree
Return's	:: None
print data in in order by calling recursive function
*/
void inOrderPrint(tree *t);

/*
Name		:: inOrderPrint_r
Input		:: pointer to leaf
Return's	:: None
print data in order recursively
*/
void inOrderPrint_r(leaf *l);

/*
Name            :: inOrderPrint
Input           :: pointer to tree
Return's        :: None
print data in pre order by calling recursive function
*/
void preOrderPrint(tree *t);


/*
Name            :: inOrderPrint_r
Input           :: pointer to leaf
Return's        :: None
print data  pre order recursively   
*/
void preOrderPrint_r(leaf *l);

/*
Name            :: inOrderPrint
Input           :: pointer to tree
Return's        :: None
print data in post order by calling recursive function
*/
void postOrderPrint(tree *t);

/*
Name            :: inOrderPrint_r
Input           :: pointer to leaf
Return's        :: None
print data post order recursively   
*/
void postOrderPrint_r(leaf *l);


/*
Name		:: removeRoot
Input		:: pointer to tree
Return's	:: pointer to remove leaf node
remove root. Tree must remain complete and can only be done through accessing the tree.
*/ 
leaf* removeRoot(tree *t);

/*
Name		:: balancedTree
Input		:: pointer to tree
Return's	:: int
check to see if tree is balenced.  if balanced return 1 and if not return -1.
*/
int balancedTree(tree *t);

/*
 Name :: balancedTree_r
 Input :: pointer to leaf
 Return's :: int 
recursively check to see if tree is balenced.  if balanced return 1 and if not return -1.
*/
 int balancedTree_r(leaf *l);
 
/*
Name		:: treeClean
Input		:: pointer to tree
Return's	:: None
code clean
*/
 void treeClean(tree *t); 

/*
Name		:: treeClean_r
Input		:: pointer to leaf
Return's	:: None
clean code recursively
*/
 void treeClean_r(leaf *l);

/*
Name		:: breathFirstSearch
Input		:: pointer to leaf i.e root node
Return's	:: pointer to leaf
Applys breathFirst Search to find last element of the tree
*/
 leaf* breathFirstSearch(leaf *lf);

/*
Name		:: treeArray
Input		:: pointer to tree
Return's	:: pointer to float array
This function takes in the tree and put elements of tree in array by traversing it
*/
 float* treeArray(tree *t);

#endif
