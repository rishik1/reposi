all:main

bag.o: bag.h bag.c
	gcc -c -g -Wall bag.c

statistics.o: statistics.h statistics.c
	gcc -c -g -Wall -lm statistics.c

main.o: main.c
	gcc -c -g -Wall main.c

main: bag.o statistics.o  main.o
	gcc -g -Wall -lm bag.o statistics.o main.o -o main

clean:
	rm *.o main
