//This function will read the data from a file and into a binary tree. The name of the file will be read in from the command line.
tree* readData(char *file);
//This function will create an array of floats that is the size of the number of elements in the tree.
//read the float value in each of the data struct in each leaf in the array.Finally return the array.
float* makeArray(tree* t);
// Print out all elements of the array.
void printArray(float *arr, int size);
// Sort the data in the array using insertion sort.
void insertionSort(float* arr, int size);
