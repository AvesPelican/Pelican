package com.zero.pelican.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private static final String OPERATOR_CHARS = "+-*/";
    private static final TokenType[] OPERATOR_TOKENS = {
    TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH
    };

    private final String input;
    private final List<Token> tokens;
    private int pos;

    public Lexer(String input){
        this.input = input;
        tokens = new ArrayList<>();
    }

    public List<Token> tokenize(){
        while (pos < input.length()){
            final char current = peek(0);
            if(Character.isDigit(current)) tokenizeNumber();
            else if (OPERATOR_CHARS.indexOf(current) != -1){
                tokenizeOperator();
            }  else if (Character.isWhitespace(current)) {
                next();  // Пропускаем пробелы
            } else {
                throw new RuntimeException("Unexpected character: " + current);
            }
        }
        addToken(TokenType.EOF);  // Добавляем маркер конца файла
        return tokens;
    }

    private char next(){
        pos++;
        return peek(0);
    }


    private char peek(int relativePosition){
        final int position = pos + relativePosition;
        if (position >= input.length()) return '\0';
        return input.charAt(position);
    }

    private void addToken(TokenType type, String value){
        tokens.add(new Token(type,value));
    }
    private void addToken(TokenType type){
        tokens.add(new Token(type));
    }

    private void tokenizeNumber(){
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (Character.isDigit(current)){
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER_LITERAL, buffer.toString());
    }

    private void tokenizeOperator(){
        final int position = OPERATOR_CHARS.indexOf(peek(0));
        addToken(OPERATOR_TOKENS[position]);
        next();
    }

}
