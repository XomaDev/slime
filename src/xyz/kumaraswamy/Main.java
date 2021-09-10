package xyz.kumaraswamy;

import xyz.kumaraswamy.slime.operators.Operator;
import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.String.valueOf;

@SuppressWarnings("InfiniteLoopStatement")
public class Main {

    public static void main(String[] args) throws Exception {
        final Scanner scanner = new Scanner(System.in);
        final Slime slime = new Slime(new Space());

        slime.setOperator("is", new Operator() {
            @Override
            public Object handle(Object first, Object second) {
                return Objects.equals(valueOf(first), valueOf(second));
            }
        });

        slime.setOperator("or", new Operator() {
            @Override
            public Object handle(Object first, Object second) {
                return valueOf(first).equals("true")
                        || valueOf(second).equals("true");
            }
        });

        while (true) {
            System.out.print("»» ");
            final String text = scanner.nextLine();
            slime.exec(text);
        }
    }
}
