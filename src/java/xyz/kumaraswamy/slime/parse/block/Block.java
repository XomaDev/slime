package xyz.kumaraswamy.slime.parse.block;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.node.Node;
import xyz.kumaraswamy.slime.node.NodeCreator;
import xyz.kumaraswamy.slime.parse.Label;
import xyz.kumaraswamy.slime.parse.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to hold the functions parsed down by
 * the @link BlockParser.class
 *
 * Preprocesses the processed tokens into node array
 * if separated by a comma to indicate multiple parameter
 * for the function
 */

public class Block {

    // the function name
    private final String name;

    // the processed nodes for the tokens
    private final Node[] nodes;

    /**
     * Initializes the Block class
     * @param name The function or the method call name
     * @param tokens The arguments for the method or
     *               the function call
     * @param slime @link Slime.class to create the nodes
     *              for the tokens
     */

    public Block(String name, Token[] tokens, Slime slime) throws Exception {
        this.name = name;

        // separate the tokens with the
        // ',' comma token

        final List<List<Token>> result = new ArrayList<>();
        List<Token> elements = new ArrayList<>();

        for (Token token : tokens) {
            if (elements.isEmpty() || token.getLabel() != Label.COMMA) {
                elements.add(token);
                continue;
            }
            result.add(elements);
            elements = new ArrayList<>();
        }

        // add the existing elements

        if (!elements.isEmpty()) {
            result.add(elements);
        }

        // parse the comma separated tokens
        // into node array

        final Node[] nodes = new Node[result.size()];

        for (int i = 0; i < result.size(); i++) {
            nodes[i] = (Node) NodeCreator.createNode(result.get(i).toArray(new Token[0]),
                    false, slime.getMethod());
        }
        this.nodes = nodes;
    }

    /**
     * @return The method name
     */

    public String getName() {
        return name;
    }

    /**
     * @return The node parms
     */

    public Node[] getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "block{" +
                "name='" + name + '\'' +
                ", node=" + Arrays.toString(nodes) +
                '}';
    }
}
