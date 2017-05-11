%{
	#include <stdio.h>
	#include<string.h>
	#include"expl1.tab.h"
	int lineno = 1;
	int level = 0;
	
%}

%%

"var" {return(TOK_VAR);}

"println" {return(TOK_PRINTLN);}

[a-z]+  { 
		yylval.v_name = strdup(yytext);
		return(TOK_VARNAME);
	}
[0-9]+ 	{
		yylval.val  = atoi(yytext);
		return(TOK_NUM);
	}
";"	{return (TOK_SEMICOLON);}

"="	{return(TOK_EQUAL);}

"+"     {return (TOK_ADD);}

"-"	{yylval.val = lineno;
	return (TOK_SUB);
	}
"{"	{
	 return(TOK_OPEN);
	}

"}"	{return(TOK_CLOSE);
	}

"*"	{return(TOK_MUL);}
[ \t]   ;
[\n] 	{lineno++;}

.	;
%%


