package com.zero.pelican.main;
import java.io.IOException;

import com.zero.pelican.main.lexer.Lexer;
import com.zero.pelican.main.lexer.Token;
import com.zero.pelican.main.lib.Variables;
import com.zero.pelican.main.parser.Parser;
import com.zero.pelican.main.parser.ast.Statement;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final String input = new String( Files.readAllBytes(Paths.get("C:\\Users\\user\\Desktop\\Pelican\\src\\com\\zero\\pelican\\test\\test.pl")), "UTF-8");
       final List<Token> tokens = new Lexer(input).tokenize();
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
       /*System.out.printf("%s = %f\n" , "petr" , Variables.get("petr"));
       System.out.printf("%s = %f" , "word2" , Variables.get("word2"));*/
    }
}
