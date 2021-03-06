package xyz.kumaraswamy.slime.parse;

import java.util.Objects;

/**
 * Class for the arithmetic and
 * dynamically added operators
 *
 * A class to identify if a value is an operator
 */

public class Operator {
    private final String operator;
    private final int hashCode;

    public Operator(String operator) {
        this.operator = operator;
        hashCode = Objects.hash(operator);
    }

    @Override
    public String toString() {
        return operator;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Operator
                && o.hashCode() == hashCode;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}