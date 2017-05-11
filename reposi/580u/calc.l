%{
#include <stdio.h>
#include"calc.tab.h"
#include<string.h>


int line_error;
char name[20];



%}

digit	  ([0-9])
ID       [a-z]
%%

"print"       {       return(TOK_PRINTLN);}


{digit}+	{
		sscanf(yytext, "%d", &(yylval.int_val));
		return TOK_NUM;
		}
"var"        { return TOK_VAR;}

{ID}+       {strcpy(name,yytext);  return TOK_ID;}

"="         {return TOK_EQUAL;}

";"	{	return(TOK_SEMICOLON);	}

"+"	{	return(TOK_ADD);	}
"*"	{	return(TOK_MUL);	}
"/"	{	return(TOK_DIV);	}

[ \n] 	{}
[\t]    {}

"{"     {return TOK_OPARA;}

"}"     {return  TOK_CPARA;} 

.	{printf("Invalid character '%c ', ignored\n", 
			yytext[0]);
          line_error=yylineno;
	}

%%

