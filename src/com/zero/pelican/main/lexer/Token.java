package com.zero.pelican.main.lexer;

public class Token {
    private TokenType type;
    private String value = "";

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

    @Override
    public String toString() {
        return "" + type;

    }
}
