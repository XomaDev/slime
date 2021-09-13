package xyz.kumaraswamy;

import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;
import xyz.kumaraswamy.slime.functions.Function;
import xyz.kumaraswamy.slime.operators.Is;
import xyz.kumaraswamy.slime.operators.Operator;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;

public class Main {
    public static void main(String[] args) throws Exception {
        final Scanner scanner = new Scanner(System.in);
        final Slime slime = new Slime(new Space());

        slime.defineConstant("cakes", "50");

        slime.setOperator("?", new Operator() {
            @Override
            public Object handle(Object first, Object second) {
                return new Is().handle(first, second);
            }
        });

        slime.defineFunction("case", new Function() {
            @Override
            public Object handle(ArrayList<Object> parms) {
                if (parms.size() != 2) {
                    throw new IllegalArgumentException("Expected only two parameter!");
                }
                final String value = parms.get(0) + "", toCase = parms.get(1) + "";
                final boolean toLowerCase;

                if (toCase.equals("true") || toCase.equals("false")) {
                    toLowerCase = toCase.equals("true");
                } else if (toCase.equalsIgnoreCase("lower")) {
                    toLowerCase = true;
                } else if (toCase.equalsIgnoreCase("upper")) {
                    toLowerCase = false;
                } else {
                    throw new IllegalArgumentException("Not a valid argument '" + toCase + "'");
                }

                return toLowerCase
                        ? value.toLowerCase()
                        : value.toUpperCase();
            }
        });

        while (true) {
            System.out.print("»» ");
            final String text = scanner.nextLine();
            if (text.equals("end")) {
                break;
            }
            long nano = System.nanoTime();
            slime.execBlock(text);
            System.out.println(((System.nanoTime() - nano) / 1E6) + "ms");
        }
    }
}
