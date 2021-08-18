package xyz.kumaraswamy.slime.node;

import xyz.kumaraswamy.slime.Space;
import xyz.kumaraswamy.slime.parse.Help;

import java.text.DecimalFormat;

public class NodeReader {
    private final Space space;

    public NodeReader(Space space) {
        this.space = space;
    }

    public Object parse(Node node) throws Exception {
        if (node == null) {
            return null;
        }
        Node head = node;
        int len = 0;
        do {
            head = head.getLeft();
            len++;
        } while (head != null && head.hasNext());

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

        for (int i = len - 1; i >= 0; i--) {
            int count = 0;

            Node thisNode = node;
            while (count++ < i) {
                thisNode = thisNode.getLeft();
            }

            final Node objRight = thisNode.getRight();

            if (objRight != null) {
                final Object object = objRight.getValue();
                final Object right =
                        object instanceof Double
                                ? (Double) object
                                : parse(objRight);
                if (result == null) {
                    throw new NullPointerException();
                }
                result = switch (thisNode.getValue() + "") {
                    case "+" -> isDouble(result, right)
                            ? number(result) + number(right)
                            : result + "" + format(right);
                    case "-" -> (double) result - (double) right;
                    case "*" -> (double) result * (double) right;
                    case "/" -> (double) result / (double) right;
                    default -> result;
                };
            }
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
        return Double.parseDouble(number.toString());
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
