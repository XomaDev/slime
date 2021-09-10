package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.operators.Operator;
import xyz.kumaraswamy.slime.node.Node;

import java.util.HashMap;

import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;

public class Processor {

    private final Space space;
    private final HashMap<String, Operator> operators;

    public Processor(final Space space, final HashMap<String, Operator> operators) {
        this.space = space;
        this.operators = operators;
    }

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

        Object result = null;
        final Object value =
                head == null ? node.getValue() : head.getValue();

        if (value instanceof String) {
            final String cast = (String) value;
            final Object object = Help.isValue(cast);

            result = (object != null) ? object : space.get(cast);
        } else if (value instanceof Double) {
            result = value;
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

            final Node rightNode = lastLeftNode.getRight();

            if (rightNode == null) {
                continue;
            }

            final Object object = rightNode.getValue();
            final Object right =
                    object instanceof Double
                            ? (double) object
                            : process(rightNode);

            if (result == null) {
                throw new NullPointerException();
            }

            // operator to work with
            final String type = lastLeftNode.getValue() + "";
            final Operator operator = operators.get(type);

            if (operator == null) {
                throw new Exception("Cannot find unknown operator '" + type + "'");
            }
            result = operator.handle(result, right);
        }
        return result;
    }

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
