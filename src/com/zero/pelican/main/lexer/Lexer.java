package com.zero.pelican.main.lexer;

import com.zero.pelican.main.Pelican;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0; // Символ с начала сканирования текущей лексемы
    private int current = 0; // Текущий символ
    private int line = 1;
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("var", TokenType.VAR);
    }

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            start = current; // Начинаем смотреть лексему говорим что старт у этой лексемя равен текущему
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char current = advance();
        switch (current) {
            case '(': addToken(TokenType.LPAREN); break;
            case ')': addToken(TokenType.RPAREN); break;
            case '{': addToken(TokenType.LBRACE);break;
            case '}': addToken(TokenType.RBRACE);break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT);  break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '*': addToken(TokenType.STAR);break;
            case '/':
                if (match('/')) {
                while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
                break;
            case '!': addToken(match('=') ? TokenType.BANG_EQ : TokenType.BANG);break;
            case '=': addToken(match('=') ? TokenType.EQ_EQ : TokenType.EQ);break;
            case '<': addToken(match('=') ? TokenType.LESS_EQ : TokenType.LESS);break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQ : TokenType.GREATER);break;
            case ' ':
            case '\r':
            case '\t': break;
            case '\n': line++;break;
            case '"': tokenizeString(); break;
            default:
                if (isDigit(current)) {
                    tokenizeNumber();
                } else if (isAlpha(current)) {
                    tokenizeIdOrKeyword();
                } else {
                Pelican.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private void tokenizeIdOrKeyword(){

        while (isAlphaNumeric(peek())){
            advance();

        }

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.ID;
        addToken(type);

    }

    private void tokenizeNumber() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && Character.isDigit(peek(1))) {
            // Consume the "."
            advance();

            while (Character.isDigit(peek())) advance();
        }

        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char peek(int checkPos) {
        if (current + checkPos >= source.length()) return '\0';
        return source.charAt(current + checkPos);
    }

    private void tokenizeString() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Pelican.error(line, "Unterminated string.");
            return;
        }
        advance();

        // Удаляем кавычки
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || Character.isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}