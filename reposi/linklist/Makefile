all:main

linklist.o: linklist.h linklist.c
	gcc -Wall -g -c linklist.c

employee.o: employee.h employee.c
	gcc -Wall -g -c employee.c

main.o: main.c
	gcc -Wall -g -c main.c

main: linklist.o employee.o main.o
	gcc -Wall -g linklist.o employee.o main.o -o main

clean:
	rm *.o main
