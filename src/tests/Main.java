import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;
import xyz.kumaraswamy.slime.functions.Function;
import xyz.kumaraswamy.slime.operators.Is;
import xyz.kumaraswamy.slime.operators.Operator;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) throws Exception {
        final Scanner scanner = new Scanner(System.in);

        final Slime slime = new Slime(new Space()) {{
            defineFunction("case", new Function() {
                @Override
                public Object handle(ArrayList<Object> parms) {
                    if (parms.size() != 2)
                        throw new IllegalArgumentException("Expected only two parameter!");
                    final Object toCase = parms.get(1);
                    if (toCase == "true" || toCase.equals("lower"))
                        return valueOf(parms.get(0)).toLowerCase();
                    else if (toCase == "false" || toCase.equals("upper"))
                        return valueOf(parms.get(0)).toUpperCase();
                    throw new IllegalArgumentException("Not a valid argument '" + toCase + "'");
                }
            });

            defineConstant("cakes", "50");

            setOperator("?", new Operator() {
                @Override
                public Object handle(Object first, Object second) {
                    return new Is().handle(first, second);
                }
            });
        }};
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
