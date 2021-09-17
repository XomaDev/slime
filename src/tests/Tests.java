import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;

public class Tests {
    public static void main(String[] args) throws Exception {
        Space space = new Space();
        Slime slime = new Slime(space);

        final String a = "a = 1 + 1";
        slime.execBlock(a);

        System.out.println("Test 1: " + ((double) space.get("a") == 2.0D));

        final String b = "a = max(7, 8) + 1";
        slime.execBlock(b);

        System.out.println("Test 2: " + ((double) space.get("a") == 9.0D));

        final String c = "b = (max(7, min(100, 200)) * 28) - 700";
        slime.execBlock(c);
        System.out.println("Test 2: " + ((double) space.get("b") == 2100.0D));

        final String d = "a = 123456 is 123456";
        slime.execBlock(d);
        System.out.println("Test 3: " + ((boolean) space.get("a")));

        final String e = "a = -123456 is 123456";
        slime.execBlock(e);
        System.out.println("Test 3: " + !(boolean) space.get("a"));

        final String f = "a = ((1 + (7 + 1)) * 171) + 191";
        slime.execBlock(f);
        System.out.println("Test 4: " + ((double) space.get("a") == 1730.0D));
    }
}
