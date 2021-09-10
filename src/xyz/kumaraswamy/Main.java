package xyz.kumaraswamy;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;
import xyz.kumaraswamy.slime.operators.Is;
import xyz.kumaraswamy.slime.operators.Operator;

import java.util.Scanner;

@SuppressWarnings("InfiniteLoopStatement")
public class Main {

    public static void main(String[] args) throws Exception {
        final Scanner scanner = new Scanner(System.in);
        final Slime slime = new Slime(new Space());

        slime.setOperator("?", new Operator() {
            @Override
            public Object handle(Object first, Object second) {
                return new Is().handle(first, second);
            }
        });

        while (true) {
            System.out.print("»» ");
            final String text = scanner.nextLine();
            slime.exec(text);
        }
    }
}
