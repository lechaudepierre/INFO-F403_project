package src;

import LexicalUnit;
import Symbol;



%%
%class LexicalAnalyzer
%unicode
%line
%standalone
%column
// the return type of the lexical analyzer
%type Symbol  
//name of the function 
%function nextSymbol

%xstate YYINITIAL, SHORT_COMMENT, LONG_COMMENT


VARNAME = [a-z][a-zA-Z0-9]*
NUMBER = [1-9][0-9]*|0
PROGNAME = [A-Z][a-zA-Z]*[_][a-zA-Z]*
WHITESPACE = [ \t\r\n]+

%eofval{
    if (yystate() == LONG_COMMENT) {
        System.out.println("no closing comment token found");
        System.exit(1);
    }
    return new Symbol(LexicalUnit.EOS, yyline, yycolumn);
%eofval}
%%


// initial state
<YYINITIAL> {
    
    {WHITESPACE} { }
    "LET" {return new Symbol(LexicalUnit.LET, yyline, yycolumn, yytext());} 
    "BE" {return new Symbol(LexicalUnit.BE, yyline, yycolumn, yytext());}
    {PROGNAME} {return new Symbol(LexicalUnit.PROGNAME, yyline, yycolumn, yytext());}
    "IN" {return new Symbol(LexicalUnit.INPUT, yyline, yycolumn, yytext());}
    "OUT" {return new Symbol(LexicalUnit.OUTPUT, yyline, yycolumn, yytext());}
    "REPEAT" {return new Symbol(LexicalUnit.REPEAT, yyline, yycolumn, yytext());}
    "(" {return new Symbol(LexicalUnit.LPAREN, yyline, yycolumn, yytext());}
    ")" {return new Symbol(LexicalUnit.RPAREN, yyline, yycolumn, yytext());}
    {VARNAME} {return new Symbol(LexicalUnit.VARNAME, yyline, yycolumn, yytext());}
    {NUMBER} {return new Symbol(LexicalUnit.NUMBER, yyline, yycolumn, yytext());}
    ":" {return new Symbol(LexicalUnit.COLUMN, yyline, yycolumn, yytext());}
    "WHILE" {return new Symbol(LexicalUnit.WHILE, yyline, yycolumn, yytext());}
    "<" {return new Symbol(LexicalUnit.SMALLER, yyline, yycolumn, yytext());}
    "=" {return new Symbol(LexicalUnit.ASSIGN, yyline, yycolumn, yytext());}
    "<=" {return new Symbol(LexicalUnit.SMALEQ, yyline, yycolumn, yytext());}
    "{" {return new Symbol(LexicalUnit.LBRACK, yyline, yycolumn, yytext());}
    "}" {return new Symbol(LexicalUnit.RBRACK, yyline, yycolumn, yytext());}
    "-" {return new Symbol(LexicalUnit.MINUS, yyline, yycolumn, yytext());}
    "END" {return new Symbol(LexicalUnit.END, yyline, yycolumn, yytext());}
    "!!" {yybegin(LONG_COMMENT);}
    "$" {yybegin(SHORT_COMMENT);}
    . {System.out.println("Illegal character: " + yytext()); System.exit(1);}


    
}
// comment states
<SHORT_COMMENT> {
    \n {yybegin(YYINITIAL);}
    . { } 
}
<LONG_COMMENT> {
    "!!" {yybegin(YYINITIAL);} //
    \n { }
    . { }
    
}


// ignore everything else