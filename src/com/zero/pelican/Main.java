package com.zero.pelican;

import com.zero.pelican.lexer.Lexer;
import com.zero.pelican.lexer.Token;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       final List<Token> tokens = new Lexer("var a = \"lexer top\"").tokenize();
       for (Token token : tokens){
           System.out.print(token.getType());
           System.out.print(" ");
           System.out.print(token.getValue());
           System.out.print("\n");
       }
    }
}
