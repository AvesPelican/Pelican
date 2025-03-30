package com.zero.pelican.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    //private static final String OPERATOR_CHARS = "+-*/";
    /* private static final TokenType[] OPERATOR_TOKENS = {
    TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH
    };*/

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
            else if (current == '+'){
                addToken(TokenType.PLUS, "+");
                next();
            }else if (current == '-'){
                addToken(TokenType.MINUS, "-");
                next();
            }else if (current == '*'){
                addToken(TokenType.STAR, "*");
                next();
            }else if (current == '/'){
                addToken(TokenType.SLASH, "/");
                next();
            }else if (current == '=') {
                addToken(TokenType.EQ, "=");
                next();
            }else if (current == '(') {
                    addToken(TokenType.LPAREN, "(");
                    next();
            } else if (current == ')'){
                addToken(TokenType.RPAREN, ")");
                next();
            }/*else if (current == '!'){
                addToken(TokenType.BANG, "!");
                next();
            }*/ else if (Character.isAlphabetic(current)){
                tokenizeIdOrKeyWord();
                next();
            }else if (current == '\"'){
                tokenizeStringLiteral();
                next();
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
    private boolean next(char next_sym){
        char current_next_sym = peek(1);
        return current_next_sym == next_sym;
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
        while (true){
            if (current == '.'){
                if (buffer.indexOf(".") != -1 ) throw new RuntimeException("Invalid float number");
            } else if (!Character.isDigit(current)){break;}

            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER_LITERAL, buffer.toString());
    }

    private void tokenizeIdOrKeyWord(){
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (Character.isLetterOrDigit(current) || current == '_' || current == '$'){
            buffer.append(current);
            current = next();
        }
        String finalstring = buffer.toString();
        if (finalstring.equals("var")){
            addToken(TokenType.VAR, finalstring);
        } else {
            addToken(TokenType.ID, finalstring);
        }
    }
    private void tokenizeStringLiteral(){
        next();
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (current != '\"'){
            buffer.append(current);
            current = next();
        }

        addToken(TokenType.STRING_LITERAL, buffer.toString());
    }

}
