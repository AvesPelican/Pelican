package com.zero.pelican.main.lexer;

public enum TokenType {
    /* Операторы и скобочки */
    LPAREN, // (
    RPAREN, // )
    LBRACE, // {
    RBRACE, // }
    PLUS, // +
    MINUS, // -
    STAR, // *
    SLASH, // /
    SEMICOLON, // ;
    COLON, // :
    COMMA, // ,
    DOT, // .

    // Булевы операторы + присвоить
    BANG_EQ,
    BANG,
    EQ_EQ,
    EQ,
    LESS_EQ,
    LESS,
    GREATER_EQ,
    GREATER,

    // Литералы
    ID, // a,b,c
    STRING, // "hello world"
    NUMBER, // 1,2,3,4

    // Кейворды
    VAR,

    EOF, // end of file
}
