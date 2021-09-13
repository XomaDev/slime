package xyz.kumaraswamy.slime.operators;

import static java.lang.String.valueOf;
import static xyz.kumaraswamy.slime.Help.asNum;
import static xyz.kumaraswamy.slime.Help.format;
import static xyz.kumaraswamy.slime.Processor.areNums;

/**
 * class for the arithmetic and
 * string concatenation operator '+'
 */

public class Add extends Operator {

    /**
     adds two values together
     if @parm first and @parm second value are numbers
     then it acts as an arithmetic operator
     else it appends the strings
    */

    @Override
    public Object handle(final Object first, final Object second) {
        if (areNums(first, second)) {
            return asNum(first) +
                    asNum(second);
        }
        return format(valueOf(first)).concat(format(valueOf(second)));
    }
}
