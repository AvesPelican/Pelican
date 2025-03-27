package com.zerp.pelican.lexer;

public class Token {
    private TokenType type;
    private String value = null;

    public Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type){
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }


}
