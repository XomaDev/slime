package xyz.kumaraswamy.slime;

import java.util.HashMap;

/**
 * class that stores variables values (nullable)
 * in hashmaps, operated by @link Slime.class
 */

public class Space {
    /**
     * A hashmap to store variables
     */
    private final HashMap<String, Object> variables = new HashMap<>();

    /**
     * A hashmap to store constants
     */

    private final HashMap<String, Object> constants = new HashMap<>();

    /**
     * Puts the name and the value to hashmap, values can be
     * overwritten to assign a new value to it
     * @param name Name of the variable (a-zA-Z) characters are expected
     * @param value A value for the @parm name key
     * @throws Exception The name is a reserved operator name
     */

    public void add(final String name, final Object value, final Slime slime) throws Exception {
        if (slime.isOperator(name) || constants.containsKey(name)) {
            throw new Exception("Cannot use name '" + name + "' to store");
        }
        variables.put(name, value);
    }

    /**
     * Defines the constant that never changes
     * @param name Name of the constant
     * @param value Value for the @parm name keu
     * @throws Exception The @parm name is already
     * used to define a constant
     */

    public void define(final String name, final Object value) throws Exception {
        if (variables.containsKey(name)) {
            throw new Exception("Variable name cannot be accepted");
        }
        constants.put(name, value);
    }

    /**
     * Gets the key of the variable expected
     * @param name Name of the key
     * @return value of the @parm name key
     * @throws Exception if the key is not present
     */

    public Object get(final String name) throws Exception {
        if (!variables.containsKey(name)) {
            if (!constants.containsKey(name)) {
                throw new Exception("Cannot find symbol '" + name + "'");
            }
            return constants.get(name);
        }
        return variables.get(name);
    }

    /**
     * Clears the keys and values from the hashmap
     * to save memory
     */

    @SuppressWarnings("unused")
    public void close() {
        variables.clear();
        constants.clear();
    }
}