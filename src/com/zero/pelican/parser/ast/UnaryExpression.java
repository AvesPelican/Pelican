package com.zero.pelican.parser.ast;

public class UnaryExpression implements Expression{
    private final Expression expr1;
    private final char operation;

    public UnaryExpression(char operation, Expression expr1) {
        this.operation = operation;
        this.expr1 = expr1;
    }

    private static int getFactorial(int f) {
        if (f <= 1) {
            return 1;
        }
        else {
            return f * getFactorial(f - 1);
        }
    }

    @Override
    public double eval() {
        switch (operation){
            case '-' : return  -expr1.eval();
            case '!' :
                if (expr1.eval() < 0) throw new RuntimeException("Factorial of negative number");
                return getFactorial((int)expr1.eval());
            case '+' :
            default:
                return expr1.eval();
        }
    }
}
