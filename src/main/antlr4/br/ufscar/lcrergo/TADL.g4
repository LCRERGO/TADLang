grammar TADL;

/* lexer rules */
/* categories */
/*
CATEGORY: 
    'house-chores' |
    'test' |
    'work' |
    'homework' |
    'event' |
    'leisure' |
    'others';
    */

/* List delimeters */
LPAREN: '(';
RPAREN: ')';

/* Skippable Tokens */
WS: [ \t\r\n] -> skip;
COMMENT: ';' COMMENT_FRAGMENT* '\n' -> skip;
fragment
COMMENT_FRAGMENT: ~[\n\r];

/* Common lexemes */
MONTH_DAY: [0-9][0-9];
YEAR: [0-9][0-9][0-9][0-9];
SYMBOL: [a-z][a-z0-9-]*;
STRING: '"' STRING_FRAGMENT* '"';
fragment
STRING_FRAGMENT: ~["];

/* Lexer error capture */
STRING_NOT_CLOSED: '"' STRING_FRAGMENT* EOF;
ERROR: . ; 

/* syntatic rules */
application:
    schedule EOF;
schedule:
    LPAREN 'schedule' SYMBOL task* RPAREN;
// task :== ('task' name description place date category)
task:
    LPAREN 'task' SYMBOL STRING STRING datetime SYMBOL RPAREN;
datetime: 
    MONTH_DAY '/' MONTH_DAY '/' YEAR;
