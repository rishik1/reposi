all:main

dlinklist.o: dlinklist.h dlinklist.c
	gcc -Wall -g -c dlinklist.c

stack.o: stack.h stack.c
	gcc -Wall -g -c stack.c

queue.o: queue.h queue.c
	gcc -Wall -g -c queue.c

postfix.o: postfix.h postfix.c
	gcc -Wall -g -c -lm  postfix.c

main.o: main.c
	gcc -Wall -g -c main.c

main: dlinklist.o stack.o queue.o postfix.o main.o
	gcc -Wall -g dlinklist.o stack.o queue.o postfix.o main.o -o main -lm

clean:
	rm *.o main
