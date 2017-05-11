all:main

dlinklist.o: dlinklist.h dlinklist.c
	gcc -Wall -g -c dlinklist.c

queue.o: queue.h queue.c
	gcc -Wall -g -c queue.c

tree.o: tree.h tree.c
	gcc -Wall -g -c tree.c

analyzeData.o: analyzeData.h analyzeData.c
	gcc -Wall -g -c analyzeData.c

main.o: main.c
	gcc -Wall -g -c main.c

main: dlinklist.o queue.o tree.o analyzeData.o  main.o
	gcc -Wall -g dlinklist.o queue.o tree.o analyzeData.o main.o -o main

clean:
	rm *.o
