package xyz.kumaraswamy.slime;

import xyz.kumaraswamy.slime.node.Node;

import java.text.DecimalFormat;

public class Processor {
    private final Space space;

    public Processor(final Space space) {
        this.space = space;
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

            Node node1 = node;
            while (count++ < i) {
                node1 = node1.getLeft();
            }

            final Node rightNode = node1.getRight();

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

            result = switch (node1.getValue() + "") {
                case "+" -> isDouble(result, right)
                        ? number(result) + number(right)
                        : result + "" + format(right);
                case "-" -> (double) result - (double) right;
                case "*" -> (double) result * (double) right;
                case "/" -> (double) result / (double) right;
                default -> result;
            };
        }
        return result;
    }

    private String format(Object num) {
        if (num instanceof Double) {
            return new DecimalFormat("###.#").format(num);
        }
        return String.valueOf(num);
    }

    private double number(Object number) {
        return Double.parseDouble(String.valueOf(number));
    }

    private boolean isDouble(Object obj, Object obj1) {
        if (obj instanceof Double
                && obj1 instanceof Double) {
            return true;
        }
        try {
            Double.parseDouble(obj.toString());
            Double.parseDouble(obj1.toString());
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
