package com.zero.pelican.main.parser.ast;

import com.zero.pelican.main.lib.Variables;

public class VariableExpression implements Expression{
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double eval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Constant or Variables does not exists");
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s [%f]", name, Variables.get(name));
    }
}
