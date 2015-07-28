all:output
array.o:array.c array.h
	gcc -Wall -c array.c
readInput.o:readInput.c readInput.h
	gcc -Wall -c readInput.c
main.o:main.c array.h readInput.h
	gcc -Wall -c main.c
output:main.o array.o readInput.o
	gcc array.o readInput.o  main.o -o out
clean: 
	rm -f "*.o out"

