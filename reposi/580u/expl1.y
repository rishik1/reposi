%{
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define total 50


typedef struct{
		int scope;
		int value;
		char name[20];

	}table;

table ST[total];
int levl = 0;
int i=0;
int acc = 0;
int search(char s[]);
int set_value(char s[]);

%}

%union {int val;
	char *v_name;	
	}
%type <val> Expr
%token <val> TOK_NUM TOK_SUB 
%token <v_name> TOK_VARNAME 
%token TOK_VAR TOK_PRINTLN TOK_SEMICOLON TOK_EQUAL TOK_ADD TOK_MUL TOK_OPEN TOK_CLOSE
%left TOK_ADD TOK_SUB
%left TOK_MUL
%% 


Program :Stmts
;
Stmts : |Stmt Stmts
	|Stmt TOK_SEMICOLON Stmts
;

Stmt :	 TOK_OPEN			{levl++;}
	| TOK_CLOSE TOK_SEMICOLON	{levl--;
					 //printf("level:::::: %d\n",levl);
					}
	| TOK_VAR TOK_VARNAME	{	i= set_value($2);

					//printf("i::::::::::%d\n",i);

					if(i!=1000)
					{	
						
						ST[i].value =0;	
						strcpy(ST[i].name,$2);
						//printf("%d SCOPE::::%d\n",ST[i].value,ST[i].scope);
					}
					else
					{
						strcpy(ST[acc].name,$2);
						ST[acc].scope = levl;
						//printf("%s SCOPE::::%d\n",ST[acc].name,ST[acc].scope);
						acc++;
					}
				}
	|TOK_VARNAME TOK_EQUAL Expr{
					i=search($1);
					
					//printf("i:::%d\n",i);
					
					if(i!=1000)
					{
						ST[i].value = $3;
						//printf("%d\n",ST[i].value);
					}
					else{
						printf("Undeclared variable");
					}
				   }
	|TOK_PRINTLN TOK_VARNAME{	i=search($2);
					if(i!=1000)
					{
						printf("%d\n",ST[i].value);
					}
					else{
						printf("Undeclared variable\n");
					}
					
				}

;

Expr : Expr TOK_ADD  Expr	{$$ = $1 + $3;}
	|Expr TOK_SUB Expr	{if($1<$3)
					printf("Parsing error::: line number %d",$2);
				  else
					$$ = $1 - $3;
				}
	|Expr TOK_MUL Expr	{$$ =$1*$3;}
	|TOK_NUM		{$$ =$1;}	
	|TOK_VARNAME 		{
				 i=search($1);
					if(i!=1000)
					{
						$$= ST[i].value;
							
					}
					else{
						printf("undeclared variable\n");
					}
				}

;

%%
int search(char s[])
{	
	int j=0;
	while(j<total)
	{	
		if((strcmp(ST[j].name,s)==0) && (ST[j].scope == levl))
			return j;
		j++;
	}
	j =0;
	while(j<total)
	{
		if((strcmp(ST[j].name,s)==0) && (ST[j].scope == levl-1))
			return j;
		j++;
	}
	return(1000);
}
int set_value(char s[])
{	
	int j=0;
	while(j<total)
	{	
		if((strcmp(ST[j].name,s)==0) && (ST[j].scope == levl))
			return j;
		j++;
	}
	return(1000);
}

int yyerror(char *s)  {
	fprintf(stderr, "SYNTAX ERROR %s \n");
	return 0;
}

int main(void)	{
	yyparse();
	return 0;
}

