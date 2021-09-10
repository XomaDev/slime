package xyz.kumaraswamy.slime.operators;

import static xyz.kumaraswamy.slime.Processor.areNums;

public abstract class Operator {
    public abstract Object handle(final Object first,
                                  final Object second) throws Exception;

    public void assertNums(final Object first,
                           final Object second) throws Exception {
        if (areNums(first, second)) {
            return;
        }
        throw new Exception("Cannot perform this operation");
    }

    public double asNum(final Object object) {
        return Double.parseDouble(String.valueOf(object));
    }
}
