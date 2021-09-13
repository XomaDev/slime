package xyz.kumaraswamy.slime.functions;

import java.util.ArrayList;

/**
 * to add a new function by the Slime.defineFunction(String, Function)
 * method, a class should be extending the methods
 */

public abstract class Function {

    /**
     * The registered function is called by the
     * @link Processor.class
     * @param parms The parameters for the function or the
     *              method call
     * @return The extending class is expected to return a value back
     * @throws Exception Something went wrong while processing
     */

    public abstract Object handle(final ArrayList<Object> parms) throws Exception;
}
