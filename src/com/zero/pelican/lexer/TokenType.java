package com.zero.pelican.lexer;

public enum TokenType {
    STAR,
    SLASH,
    PLUS,
    MINUS,
    LPAREN, // (
    RPAREN, // )
    BANG,

    NUMBER_LITERAL,
    STRING_LITERAL,

    INT,
    STRING,

    ID,
    VAR,
    ASSIGN,

    EOF,
}
