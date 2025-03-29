package com.zero.pelican.parser;

import com.zero.pelican.lexer.Token;
import com.zero.pelican.lexer.TokenType;
import com.zero.pelican.parser.ast.BinaryExpression;
import com.zero.pelican.parser.ast.Expression;
import com.zero.pelican.parser.ast.NumberExpression;
import com.zero.pelican.parser.ast.UnaryExpression;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    private final Token EOF = new  Token(TokenType.EOF, "");
    private final List<Token> tokens;
    private final int size;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public List<Expression> parse(){
        final List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)){
            result.add(expression());
        }
        return result;
    }


    private Expression expression(){
        return additive();
    }

    private Expression additive(){
        Expression result = multiplicative();
        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression multiplicative(){
        Expression result = unary();
        while (true) {
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression unary(){
        if (match(TokenType.MINUS)){
            return new UnaryExpression('-', primary());
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        if (match(TokenType.BANG)) {
            return new UnaryExpression('!', primary());
        }
        return primary();
    }

    private Expression primary(){
        final Token current = peek(0);
        if (match(TokenType.NUMBER_LITERAL)) {
            Expression number = new NumberExpression(Double.parseDouble(peek(-1).getValue()));

            // Обрабатываем постфиксные операторы (например, факториал)
            while (true) {
                if (match(TokenType.BANG)) {
                    number = new UnaryExpression('!', number);
                    continue;
                }
                break;
            }
            return number;
        }
        if (match(TokenType.LPAREN)){
            Expression result = expression();
            if (!match(TokenType.RPAREN)){
            throw new RuntimeException("Missing closing parent");}
            return result;
        }
        else {

            throw new RuntimeException("Unknown expression " + peek(0));
        }

    }


    private boolean match(TokenType type){
        final Token current = peek(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }

    private Token peek(int relativePosition){
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }


}
