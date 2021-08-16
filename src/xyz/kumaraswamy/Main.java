package xyz.kumaraswamy;

import xyz.kumaraswamy.slime.Interpreter;
import xyz.kumaraswamy.slime.Space;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        final Space space = new Space();
        final Interpreter interpreter = new Interpreter(space);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("»» ");
            interpreter.exec(scanner.nextLine());
        }
    }
}
