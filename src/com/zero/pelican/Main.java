package com.zero.pelican;

import com.zero.pelican.lexer.Lexer;
import com.zero.pelican.lexer.Token;
import com.zero.pelican.parser.Parser;
import com.zero.pelican.parser.ast.Expression;

import java.util.List;

public class Main {
    public static void main(String[] args) {
       final List<Token> tokens = new Lexer("5!").tokenize();
       /*for (Token t : tokens){
           System.out.println(t);
       }*/
       final List<Expression> expressions = new Parser(tokens).parse();
       for (Expression expr : expressions){
           System.out.println(expr.eval());
       }
    }
}
