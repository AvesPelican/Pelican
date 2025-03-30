package com.zero.pelican;

import com.zero.pelican.lexer.Lexer;
import com.zero.pelican.lexer.Token;
import com.zero.pelican.lib.Variables;
import com.zero.pelican.parser.Parser;
import com.zero.pelican.parser.ast.Expression;
import com.zero.pelican.parser.ast.Statement;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       final List<Token> tokens = new Lexer("petr = 2+2\nword2 = PI + petr").tokenize();
       /*for (Token t : tokens){
           System.out.print(t);
           System.out.print(" ");
           System.out.print(t.getValue());
           System.out.println();
       }*/
       final List<Statement> statements = new Parser(tokens).parse();
        /*for (Statement statement : statements){
            System.out.println(statement);
        }*/
       for (Statement statement : statements){
            statement.execute();
        }
        System.out.printf("%s = %f\n" , "petr" , Variables.get("petr"));
       System.out.printf("%s = %f" , "word2" , Variables.get("word2"));
    }
}
