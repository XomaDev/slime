package xyz.kumaraswamy.slime.functions;

import java.util.ArrayList;
import java.util.Arrays;

import static xyz.kumaraswamy.slime.Help.asNum;
import static xyz.kumaraswamy.slime.Help.assertNums;

/**
 * a class that helps @link Max.class and
 * the @link Min.class and performs operations for them
 */

public class MaxMin {

    /**
     * Asserts that all the parms are a valid numbers
     * and sorts and return the sorted souble
     * @throws Exception The @parm parms size is < 2
     */

    public static double[] assertAndGet(final ArrayList<Object> parms) throws Exception {
        final int len = parms.size();
        if (len < 2) {
            throw new IllegalArgumentException("Expected at-least two elements to find the x max value");
        }
        double[] doubles = new double[len];

        for (int i = 0; i < len; i++) {
            final Object parm = parms.get(i);
            assertNums(parm, 1D);
            doubles[i] = asNum(parm);
        }
        Arrays.sort(doubles);
        return doubles;
    }
}
