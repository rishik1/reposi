#ifndef EMPLOYEE_H
#define EMPLOYEE_H

#include "linklist.h"
#define debug1 0
linkList* loadData(char *fileName);
int countFirstName(linkList *ll, char *first);
void removeLastName(linkList *ll, char *last);
void removeFirstName(linkList *ll,char *first);
void clean_emp(linkList *ll);
#endif

