grammar jsh;

//PARSER
option : OPTION_CHAR;
quoted : DOUBLEQUOTE | BACKQUOTE | SINGLEQUOTE;
argument : (quoted | UNQUOTED | UNQUOTEDRESERVEDCHAR)+;
redirection : '<' WHITESPACE argument | '>' WHITESPACE argument;
fullCall : WHITESPACE* UNQUOTEDRESERVEDCHAR (WHITESPACE option)* (WHITESPACE argument)* (WHITESPACE option)* (WHITESPACE argument)* (WHITESPACE redirection)* WHITESPACE*;
seq : fullCall ';' fullCall | seq ';' fullCall;
pipe : fullCall '|' fullCall | pipe '|' fullCall;
command : pipe | seq | fullCall;

//LEXER
OPTION_CHAR : '-' [A-Za-z]+;
WHITESPACE : (' ' | '\t')+;
DOUBLEQUOTE : '"' ( ('`' ~('\n' | '`')* '`') | ~('"' | '`' | '\n' )  )* '"';
SINGLEQUOTE : '\'' (~('\n' | '\''))* '\'';
BACKQUOTE : '`' (~('\n' | '`'))* '`';
UNQUOTEDRESERVEDCHAR : (~( ' ' | '\t' | '"' | '\'' | '\n' | '`' | ';' | '|' | '<' | '>' | '#'))+;
UNQUOTED : (~( ' ' | '\t' | '\n' | ';' | '|' | '<' | '>' | '#'))+;
