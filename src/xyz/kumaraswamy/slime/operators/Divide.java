package xyz.kumaraswamy.slime.operators;

/**
 * class for arithmetic operator '/'
 */

public class Divide extends Operator {

    /**
     * @return divides @parm first by @parm second
     * @throws Exception @parm first or @parm second is not a number
     */

    @Override
    public Object handle(Object first, Object second) throws Exception {
        assertNums(first, second);
        return asNum(first) / asNum(second);
    }
}
