all: vuln_program attack-string

vuln_program: vuln_program.c
	gcc vuln_program.c -ggdb -fno-stack-protector -z execstack -static -o vuln_program


attack-string: attack-string.c
	gcc attack-string.c -o attack-string
clean:
	rm vuln_program attack-string
