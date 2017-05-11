#ifndef _BTREE_H_
#define _BTREE_H_

#include <stdio.h>
#include <stdlib.h>


// structure to store data value
typedef struct
{
  float val;
}data;

// struct leaf pointer contains pointer to data leaf_ptr right,leaf_ptr left to create node of tree with left and right pointers
typedef struct leaf_ptr
{
  data *d;
  struct leaf_ptr *right;
  struct leaf_ptr *left;
} leaf;

// structure which contains pointer to root node
typedef struct
{
  leaf* root;
}btree;


/*
Name	:: createBtree
Input	:: NONE
Return's:: pointer to btree
create btree and initialize to NUll
*/
btree* createBtree();

/*
Name		:: createLeaf
Input		:: pointer to data
Return's	:: pointer to Leaf
createLeaf function to create leaf node from input data pointer
*/
leaf* createLeaf(data *d);

/*
Name		:: createData
Input		:: float value
Return's	:: pointer to Data
function creates Data pointer using input float val
*/
data* createData(float val);

/*
Name		:: addLeaf
Input		:: pointer to Leaf, pointer to data
Return's	:: None
function creates leaf from input data and add to btree
*/
void addLeaf(btree* bt,data *d);

/*
Name		:: findPos
Input		:: pointer to Leaf,float val
Return's	:: pointer to Leaf
function find the positon of the Leaf whose value is "val" and return the Leaf
*/
leaf* findPos(leaf *l,float val);

/*
Name		:: printInOrder
Input		:: pointer to btree
Return's	:: None
function calls recurive Inorder print
*/
void printInOrder(btree *t);

/*
Name		:: printInOrder_r
Input		:: pointer to Leaf
Return's	:: None
function prints all the value in the tree recursively
*/
void printInOrder_r(leaf *l);

/*
Name		:: printPostOrder
Input		:: pointer to btree
return's	:: None
function prints all the value recursively
*/
void printPostOrder(btree *t);

/*
Name		:: printPostOrder_r
Input		:: pointer to leaf
Return's	:: None 
function printd tree values in Post order way
*/
void printPostOrder_r(leaf *l);
/*
Name		:: printPreOrder
Input		:: pointer to leaf
Return's	:: None
function printed tree values in Pre Order
*/

void printPreOrder(btree *t);

/*
Name 		:: printPreOrder
Input		:: pointer to leaf
Return's	:: pointer to leaf
function printed tree values in PreOrder
*/
void printPreOrder_r(leaf *l);

/*
Name		:: getMin
Input		:: pointer to tree
Return's	:: float
function finds Mininmum value in tree
*/
float getMin(btree *t);

/*
Name		:: getMin_r
Input		:: pointer to leaf
Return's	:: float
function finds Minimum value in tree
*/
leaf* getMin_r(leaf *l);

/*
Name		:: getMax
Input		:: pointer to btree
Return's	:: float
function finds Maximum value in tree
*/
float getMax(btree *t);

/*
Name		:: getMax_r
Input		:: pointer to leaf
Return's	:: pointer to leaf
function find the Maximum value in tree
*/
leaf* getMax_r(leaf *l);

/*
Name		:: cleanTree
Input		:: pointer to btree
Return's	:: None
function free the memory location on heap
*/

void cleanTree(btree *t);
/*
Name		:: cleanTree_r
Input		:: pointer to Leaf
Return's	:: None
function free the memory on heap recursively
*/ 
void cleanTree_r(leaf *t);

/*
Name		:: cleanTreeMerge
Input		:: pointer to btree
Return's	:: None
function free Memory on the heap for ONLY MERGED TREE
*/
void cleanTreeMerge(btree *t);

/*
Name		:: cleanTreeMerge_r
Input		:: pointer to leaf
Return's	:: None
function free Memory on the heap recursivley for only MERGED TREE
*/
void cleanTreeMerge_r(leaf *t);

/*
Name		:: seach
Input		:: pointer to btree,float value
Return's	:: pointer to Leaf 
function seach searches the value in the tree and returns NULL if not found
*/
leaf* seach(btree *t,float val);

/*
Name            :: search_r
Input           :: pointer to btree,float value  
Return's        :: pointer to Leaf     
function seach searches the value in the tree and returns NULL if not found
*/
leaf* search_r(leaf *f,float val);

/*
Name		:: printBound
Input		:: pointer to btree,float lower_limit,float upper_limit
Return's	:: None
function print values inthe range fro lower to upper
*/
void printBound(btree *t,float lower,float upper);

/*
Name            :: printBound 
Input           :: pointer to leaf,float lower_limit,float upper_limit
Return's        :: None       
function print values inthe range fro lower to upper by recursevely calling itself
*/
void printBound_r(leaf *l,float lower,float upper);

/*
Name		:: InOrderMerge
Input		:: pointer to btree one and pointer to btree second
Returns'	:: pointer to btree
function do merge of two btrees and return's the resulting btree
*/
btree* InOrderMerge(btree *t,btree *t1);

/*
Name		:: InOrderMerge_r
Input		:: pointer to leaf,pointer to btree
Return's	:: None
function do merge of  btree one recursively into the resulting btree which is NULL at the starting,
i.i only put 1st btree int the resulting tree
*/        
void InOrderMerge_r(leaf *l,btree *res);
/*
Name            :: InOrderMerge_r        
Input           :: pointer to leaf,pointer to btree        
Return's        :: None        
function do merge of  btree one recursively into the resulting btree which contains only btree1  at the starting,
i.i only put 2nd btree int the resulting tree while eliminating common values
*/   
void InOrderMerge_r1(leaf *l,btree *res); 
#endif
