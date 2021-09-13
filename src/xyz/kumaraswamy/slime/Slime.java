package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.functions.Function;
import xyz.kumaraswamy.slime.operators.Operator;
import xyz.kumaraswamy.slime.lex.Lex;
import xyz.kumaraswamy.slime.node.Node;
import xyz.kumaraswamy.slime.node.NodeCreator;
import xyz.kumaraswamy.slime.parse.block.BlockParser;
import xyz.kumaraswamy.slime.parse.Parse;
import xyz.kumaraswamy.slime.parse.Token;

import java.lang.reflect.Method;
import java.util.HashMap;

import static java.lang.String.valueOf;

/**
 * class that connects various processes to
 * perform the eval operation, defining variables and
 * handling method calls
 */

@SuppressWarnings("unused")
public class Slime {

    // space to store things and process the
    // node provided by the NodeCreator class

    private final Space space;
    private Processor processor = null;

    private SlimeMethods methods = null;

    /**
     * Map to define, remove and set operators
     * `Data.operators` returns the default set of operators
     */

    private HashMap<String, Operator> operators = Data.operators;

    /**
     * A map to define and remove functions dynamically
     * `Data.functions` returns the default set of functions
     */

    private final HashMap<String, Function> functions = Data.functions;

    public Slime(final Space space) throws Exception {
        this.space = space;
        init();
    }

    /**
     * Initialize with custom slime methods provided externally
     * @param methods Any class extending @link SlimeMethods.class
     */

    public Slime(final Space space, final SlimeMethods methods) throws Exception {
        this.space = space;
        this.methods = methods;
        init();
    }

    /**
     * Initializes the slime with predefined
     * variable(s)
     */

    private void init() throws Exception {
        // the PI value
        defineConstant("PI", valueOf(Math.PI));

        // define variables as conditions
        // they will print themselves while in use
        defineConstant("true", "true");
        defineConstant("false", "false");
    }

    /**
     * Sets the custom operator map
     * @param operators Map provided externally
     */

    public void setOperators(final HashMap<String, Operator> operators) {
        this.operators = operators;
    }

    /**
     * @return operators map
     */

    public HashMap<String, Operator> getOperators() {
        return operators;
    }

    /**
     * A method to dynamically define an operator.
     * Operator.handle(Object left, Object right) will be
     * called to perform the operations
     *
     * @param symbol The operator
     * @param operator Operator class to handle operator and return a value
     */

    public void setOperator(final String symbol, final Operator operator) {
        operators.put(symbol, operator);
    }

    /**
     * Removes the operator from the Map
     * @param symbol Operator to remove
     */

    public void removeOperator(final String symbol) {
        operators.remove(symbol);
    }

    /**
     * @param check Called by Lex and Parse class to
     *              verify the object is an operator
     * @return if @parm check is an operator
     */

    public boolean isOperator(final String check) {
        return check.equals("=")
                || operators.containsKey(check);
    }

    /**
     * Checks if @parm name is a valid
     * existing function name
     * @param name The name to check on
     * @return if @parm name is a function name
     */

    public boolean isFunctionName(final String name) {
        return name != null && functions.containsKey(name);
    }

    /**
     * Dynamically defines a function
     * @param name The name of the function
     * @param function The @link Function.class argument.
     *                 A class should be extended it, and handle(ArrayList<Object> parms)
     *                 will be called on it.
     */

    public void defineFunction(final String name, Function function) {
        functions.put(name, function);
    }

    /**
     * @return Defined function
     */

    public HashMap<String, Function> getFunctions() {
        return functions;
    }

    public SlimeMethods getMethod() {
        return methods;
    }

    /**
     * Defines the unchangeable constant variable
     * that never changes
     * @param name The name of/for the constant value
     * @param value The constant value, should not be an expression
     */

    public void defineConstant(final String name, final String value) throws Exception {
        space.define(name, value);
    }

    /**
     * Defines the variable with the Evaluated result
     * of the @parm expression
     *
     * @param name The name of the variable
     * @param expression Value to define
     * @throws Exception Throws an exception (if the expression is an
     * assign statement or a method call) or name of the variable is invalid
     */

    public void define(final String name, final String expression) throws Exception {
        if (!Lex.alphabets(name)) {
            throw new Exception("Name should only contain a-zA-Z characters in range");
        }
        final Object[] results = processObjects(expression, true);

        if (results == null)
            return;

        if (results[1] != null) {
            throw new Exception("Cannot be a method call or value assign statement");
        }

        final Object result = processor.process((Node) results[0]);
        if (result == null)
            return;

        space.add(name, result, this);
    }

    /**
     * The same as the method exec(String), but
     * the parsing is done with the @link BlockParser.class
     * that provides additional functionality to parse function
     * calls
     * @param text The text to be executed
     */

    public void execBlock(final String text) throws Exception {
        exec(text, false);
    }

    /**
     * Evaluates the string, handles assign and method call statements
     * @param text Expression to evaluate
     * @throws Exception The syntax of the @parm text is
     * wrong or not a valid statement
     */

    public void exec(final String text) throws Exception {
        exec(text, true);
    }

    public void exec(final String text, boolean useRegularParse) throws Exception {
        final Object[] results = processObjects(text, useRegularParse);

        if (results == null) {
            return;
        }

        // processes the node tree into a result
        final Object result = processor.process((Node) results[0]);

        if (result == null || results[1] == null) {
            return;
        }

        final String[] names = (String[]) results[1];
        final int len = names.length;


        // check if it is a method call or
        // a variable assign and move accordingly

        if (len == 2 && names[1].equals("=") && Lex.alphabets(names[0])) {
            // a statement to assign a variable

            // name of the variable
            final String name = names[0];

            // check if the name is valid
            if (Lex.alphabets(names[0])) {
                space.add(name, result, this);
                return;
            }
        } else if (len == 1) {
            // it is a method call
            invoke(names[0], result);
            return;
        }
        throw new Exception("Invalid syntax provided");
    }

    /**
     * Tokenizes the text, labels the tokens and
     * creates a node tree out of it
     * @param regularParse if to parse the tokens with the
     *                     normal @link Parse.class,
     *                     for the methods calls, its false,
     *                     and we use the BlockParser.class
     * @throws Exception Something went wrong while processing
     */

    private Object[] processObjects(String text, boolean regularParse) throws Exception {
        final String[] lexed = new Lex(text, this).lex();

        if (lexed.length == 0) {
            return null;
        }

        final Token[] tokens = regularParse
                ? Parse.parse(lexed, this)
                : new BlockParser().parse(lexed, this);

        if (methods == null) {
            methods = new SlimeMethods();
        }

        final Object[] results = (Object[]) NodeCreator.createNode(
                tokens, true, methods);

        if (processor == null) {
            processor = new Processor(space, operators, functions);
        }
        return results;
    }

    /**
     * Invokes the method using reflection.
     * A method in a (extended) class always takes Object as the parm
     * @throws Exception The method name doesn't exist
     */

    private void invoke(final String name, final Object result) throws Exception {
        final Method method = methods.getClass().getMethod(name, Object.class);
        method.invoke(methods, result);
    }
}
