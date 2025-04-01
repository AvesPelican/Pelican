package com.zero.pelican.main.parser.ast;

import com.zero.pelican.main.lib.Variables;

public class AssignmentStatement implements Statement{
    private final String variable;
    private final Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final double result = expression.eval();
        Variables.set(variable, result);
    }

    @Override
    public String toString() {
        return String.format("%s = %s" , variable, expression);
    }
}
