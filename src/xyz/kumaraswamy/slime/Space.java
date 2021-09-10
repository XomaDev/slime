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
     * Puts the name and the value to hashmap, values can be
     * overwritten to assign a new value to it
     * @param name Name of the variable (a-zA-Z) characters are expected
     * @param value A value for the @parm name key
     */

    public void add(String name, Object value)  {
        variables.put(name, value);
    }

    /**
     * Gets the key of the variable expected
     * @param name Name of the key
     * @return value of the @parm name key
     * @throws Exception if the key is not present
     */

    public Object get(String name) throws Exception {
        if (!variables.containsKey(name)) {
            throw new Exception("Cannot find symbol '" + name + "'");
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
    }
}