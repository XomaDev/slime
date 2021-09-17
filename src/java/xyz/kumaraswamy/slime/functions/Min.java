package xyz.kumaraswamy.slime.functions;

import java.util.ArrayList;

/**
 * class for the min(args) function
 * to get the max number in the arguments provided
 */

public class Min extends Function {
    /**
     *
     * @param parms Input arguments in numbers
     * @return min value in @parm parms int/double number array
     * @throws Exception One of the value provided is not a number or
     * the parms is len < 2
     */

    @Override
    public Object handle(final ArrayList<Object> parms) throws Exception {
        final double[] doubles = MaxMin.assertAndGet(parms);
        return doubles[0];
    }
}
