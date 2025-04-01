package com.zero.pelican.main.parser;

import com.zero.pelican.main.lexer.Token;
import com.zero.pelican.main.lexer.TokenType;
import com.zero.pelican.main.parser.ast.*;

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

    public List<Statement> parse(){
        final List<Statement> result = new ArrayList<>();
        while (!match(TokenType.EOF)){
            result.add(statement());
        }
        return result;
    }

    private Statement statement(){
        if(match(TokenType.PRINTLN)){
            return  new PrintStatement(expression());
        }
        return assignmentStatement();
    }

    private Statement assignmentStatement(){
        final Token current = peek(0);
        if (current.getType() == TokenType.ID && peek(1).getType() == TokenType.EQ){
            consume(TokenType.ID);
            final String variable = current.getValue();
            consume(TokenType.EQ);
            return new AssignmentStatement(variable,expression());
        }
        throw new RuntimeException("Unknown statement - " + peek(0));
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
        /*if (match(TokenType.BANG)) {
            return new UnaryExpression('!', primary());
        }*/
        return primary();
    }

    private Expression primary(){
        final Token current = peek(0);
        if (match(TokenType.NUMBER_LITERAL)) {
            return new NumberExpression(Double.parseDouble(current.getValue()));
        }
        if (match(TokenType.ID)) {
                return new VariableExpression(current.getValue());}
        if (match(TokenType.LPAREN)){
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }
            throw new RuntimeException("Unknown expression " + peek(0));

    }

    private Token consume(TokenType type){
        final Token current = peek(0);
        if (type != current.getType()) throw new RuntimeException("Token " + current + " doesn't match " + type);
        pos++;
        return current;
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
