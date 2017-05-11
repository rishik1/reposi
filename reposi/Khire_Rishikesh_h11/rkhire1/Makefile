all: btree

btree.o: btree.h btree.c
	gcc -c btree.c

readData.o: readData.h readData.c
	gcc -c readData.c

main.o: main.c
	gcc -c -Wall -g -c main.c

btree: btree.o  readData.o main.o
	gcc btree.o readData.o main.o -o main

clean:
	rm *.o *~ main
