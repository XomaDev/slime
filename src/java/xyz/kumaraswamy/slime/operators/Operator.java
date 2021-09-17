package xyz.kumaraswamy.slime.operators;

/**
 * Class to declare an operator.
 * handle(Object, Object) will be called on
 * extending class
 */

public abstract class Operator {
    public abstract Object handle(final Object first,
                                  final Object second) throws Exception;
}
