package xyz.kumaraswamy.slime.operators;

// a class to compare two things
// and return condition accordingly

import java.util.Objects;

import static java.lang.String.valueOf;

public class Is extends Operator {

    /**
     * @return @parm first == @parm second
     */

    @Override
    public Object handle(Object first, Object second)  {
        return Objects.equals(valueOf(first), valueOf(second));
    }
}
