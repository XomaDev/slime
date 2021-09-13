package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.functions.Function;
import xyz.kumaraswamy.slime.operators.Operator;
import xyz.kumaraswamy.slime.node.Node;
import xyz.kumaraswamy.slime.parse.block.Block;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;

public class Processor {

    private final Space space;

    // externally added and pre added
    // operators are declared here

    private final HashMap<String, Operator> operators;
    private final HashMap<String, Function> functions;

    public Processor(Space space, HashMap<String, Operator> operators, HashMap<String, Function> functions) {
        this.space = space;
        this.operators = operators;
        this.functions = functions;
    }

    /**
     * Process the node tree and returns the result
     * @param node Node tree to process
     * @throws Exception Something went wrong while processing
     */

    public Object process(final Node node) throws Exception {

        if (node == null) {
            return null;
        }

        // find the len of the left node
        // of the head node

        Node head = node;
        int len = 0;
        do {
            head = head.getLeft();
            len++;
        } while (head != null && head.hasNextLeft());

        // if head is null (null when there is just a
        // single left value for node)
        // get the node value.

        // value is a starting point value
        // for the operations

        // check if value is integer, if
        // so, check if it's a string or a variable
        // accordingly

        Object result;
        final Object value =
                head == null ? node.getValue() : head.getValue();

        if (value instanceof String) {
            final String cast = (String) value;
            final Object object = Help.isValue(cast);

            result = (object != null) ? object : space.get(cast);
        } else if (value instanceof Double) {
            result = value;
        } else if (value instanceof Block) {
            result = handleFunction((Block) value);
        } else {
            throw new IllegalArgumentException();
        }

        // do a reverse loop till [len] times
        for (int i = len - 1; i >= 0; i--) {
            int count = 0;

            // get the last left node of
            // the parm node to process with
            // the min (first) right node

            Node lastLeftNode = node;
            while (count++ < i) {
                lastLeftNode = lastLeftNode.getLeft();
            }

            // the right node to operator
            // with the left node

            final Node rightNode = lastLeftNode.getRight();

            if (rightNode == null) {
                continue;
            }

            final Object object = rightNode.getValue();
            Object right =
                    object instanceof Double
                            ? (double) object
                            : process(rightNode);

            if (result == null) {
                throw new NullPointerException();
            }

            // operator to work with

            final String type = lastLeftNode.getValue() + "";
            final Operator operator = operators.get(type);

            // if operator is null, the operator
            // cannot be found

            if (operator == null) {
                throw new Exception("Cannot find unknown operator '" + type + "'");
            }

            // calls the handle(Object, Object) method on
            // the extended class of operators/Operator.class\

            if (result instanceof Block) {
                result = handleFunction((Block) result);
            }
            if (right instanceof Block) {
                right = handleFunction((Block) right);
            }

            result = operator.handle(result, right);
        }
        if (result instanceof Block) {
            result = handleFunction((Block) result);
        }
        return result;
    }

    /**
     * Handles the function (Block.class)
     * if any function needs to be called during
     * the execution time
     * @return The handled result
     */

    private Object handleFunction(Block block) throws Exception {
        final String name = block.getName();
        final Function function = functions.get(name);

        if (function == null) {
            throw new Exception("Cannot find symbol '" + name + "'");
        }
        final ArrayList<Object> parms = new ArrayList<>();
        for (Node node : block.getNodes()) {
            parms.add(process(node));
        }
        return function.handle(parms);
    }

    /**
     * Checks if two numbers can be used as numbers
     * this is used to auto cast objects
     */

    public static boolean areNums(Object first, Object second) {
        if (first instanceof Double
                && second instanceof Double) {
            return true;
        }
        try {
            // try parsing the two objects to number
            parseDouble(valueOf(first));
            parseDouble(valueOf(second));
        } catch (NumberFormatException ignored) {
            // just ignore the exception
            return false;
        }
        return true;
    }
}
