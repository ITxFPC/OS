package itxfpc.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

public class FibonacciRunTest {
    @Test
    public void runFibonacciRunTest() {
        int length = 25;
        FibonacciRun fbcRun = new FibonacciRun(length);
        Thread t = new Thread(fbcRun);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fbcRun.printSequence();



    }


}
