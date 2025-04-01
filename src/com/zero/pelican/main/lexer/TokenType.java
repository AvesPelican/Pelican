package com.zero.pelican.main.lexer;

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
   PRINTLN,

    ID,
    VAR,
    EQ,

    EOF,
}
