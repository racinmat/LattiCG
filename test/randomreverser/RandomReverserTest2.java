package randomreverser;

import randomreverser.call.java.Next;
import randomreverser.device.JavaRandomDevice;
import randomreverser.device.LCGReverserDevice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomReverserTest2 {
    public static void main(String[] args) {
        JavaRandomDevice device = new JavaRandomDevice();
        Random r = new Random();

        long seed = r.nextLong()  & ((1L << 48) - 1);
        System.out.println("Seed: " +seed);

        r.setSeed(seed ^ 0x5deece66dL);
        long structureSeed = r.nextLong() & ((1L << 48) - 1);

        //Dark arts to make the first entry a valid seed. The consume nextFloat calls are entirely to go back in time. Not sponsored usage.
        device.skip(1);
        device.addCall(Next.inModRange((structureSeed & 0xffff_ffffL) << 16, (((structureSeed+1) & 0xffff_ffffL) << 16) - 1, (1L << 48)));
        device.skip(-2);

        if((structureSeed & 0x8000_0000L) == 0) { //Was the lower half negative
            device.addCall(Next.inModRange((structureSeed >> 32) << 16, ((1 + (structureSeed >> 32)) << 16) - 1, (1L << 32)));
        } else {
            device.addCall(Next.inModRange((1 + (structureSeed >> 32)) << 16, ((2 + (structureSeed >> 32)) << 16) - 1, (1L << 32)));
        }

       // device.setVerbose(true);
        long start = System.nanoTime();

        AtomicInteger count = new AtomicInteger(0);
        device.streamSeeds(LCGReverserDevice.Process.EVERYTHING).forEach(s -> {
            count.incrementAndGet();
            System.out.println(s);
        });
        if (count.get() == 1)
            System.out.println("Found " + count + " seed.");
        else System.out.println("Found " + count + " seeds.");

        long end = System.nanoTime();

        System.out.printf("elapsed: %.2fs%n", (end - start) * 1e-9);
    }
}
