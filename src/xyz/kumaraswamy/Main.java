package xyz.kumaraswamy;

import xyz.kumaraswamy.slime.Interpreter;

public class Main {

    public static void main(String[] args) throws Exception {
        final Interpreter interpreter = new Interpreter();
        interpreter.exec("a = '1' + (1 + 2)");
    }
}
