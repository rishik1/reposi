#ifndef STATISTICS_H
#define STATISTICS_H

#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include "bag.h"
#define debug1 0

bag* inputData(char *file);

float Mean(bag *bg);
float StandardDeviation(bag *bg);
float Mode(bag *bg);


#endif
