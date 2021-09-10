package xyz.kumaraswamy.slime.operators;

// a class to check if any in two conditions
// is true

import static java.lang.String.valueOf;

public class Or extends Operator {

    /**
     * @return (@parm first || @parm second) is true
     */

    @Override
    public Object handle(Object first, Object second) {
        return valueOf(first).equals("true")
                || valueOf(second).equals("true");
    }
}
