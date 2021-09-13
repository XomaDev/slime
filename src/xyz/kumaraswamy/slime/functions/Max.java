package xyz.kumaraswamy.slime.functions;

import java.util.ArrayList;

import static xyz.kumaraswamy.slime.functions.MaxMin.assertAndGet;

/**
 * class for the max(args) function
 * to get the max number in the arguments provided
 */

public class Max extends Function {

    /**
     *
     * @param parms Input arguments in numbers
     * @return max value in @parm parms int/double number array
     * @throws Exception One of the value provided is not a number or
     * the parms is len < 2
     */

    @Override
    public Object handle(final ArrayList<Object> parms) throws Exception {
        final double[] doubles = assertAndGet(parms);
        return doubles[doubles.length - 1];
    }
}
