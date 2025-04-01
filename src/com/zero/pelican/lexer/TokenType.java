package com.zero.pelican.lexer;

public enum TokenType {
    STAR,
    SLASH,
    PLUS,
    MINUS,
    LPAREN, // (
    RPAREN, // )


    NUMBER_LITERAL,
    STRING_LITERAL,

   //кейворды
   PRINT,

    ID,
    VAR,
    EQ,

    EOF,
}
