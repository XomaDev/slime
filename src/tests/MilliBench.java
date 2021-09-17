import xyz.kumaraswamy.slime.Slime;
import xyz.kumaraswamy.slime.Space;

public class MilliBench {
    public static void main(String[] args) throws Exception {
        final int times = (((((100 * 10) * 10)) * 10) * 10) * 100;
        System.out.println(times);
        final String expression = "83 * 827";

        Slime slime = new Slime(new Space());

        long time = 0;
        for (int i = 0; i < times; i++) {
            final long start = System.nanoTime();
            slime.execBlock(expression);
            time += (System.nanoTime() - start);
        }
        System.out.println(time + "ns");
        System.out.println("Estimated time per execution: " + (((System.nanoTime() - times) / 1E6) / times));
    }
}
