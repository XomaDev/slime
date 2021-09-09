package xyz.kumaraswamy.slime;

import java.util.HashMap;

@SuppressWarnings("unused")
public class Space {
    private final HashMap<String, Object> variables = new HashMap<>();

    public void add(String name, Object value)  {
        variables.put(name, value);
    }

    public Object get(String name) throws Exception {
        if (!variables.containsKey(name)) {
            throw new Exception("Cannot find symbol '" + name + "'");
        }
        return variables.get(name);
    }

    public void close() {
        variables.clear();
    }
}