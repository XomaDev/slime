package xyz.kumaraswamy;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;

import java.util.Scanner;

@SuppressWarnings("InfiniteLoopStatement")
public class Main {

    public static void main(String[] args) throws Exception {
        final Scanner scanner = new Scanner(System.in);
        final Slime slime = new Slime(new Space());

        while (true) {
            System.out.print("»» ");
            slime.exec(scanner.nextLine());
        }
    }
}
