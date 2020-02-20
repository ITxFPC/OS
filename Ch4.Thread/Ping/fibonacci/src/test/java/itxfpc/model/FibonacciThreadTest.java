package itxfpc.model;

import java.util.ArrayList;
import org.junit.Test;

public class FibonacciThreadTest {
    @Test
    public void runFibonacciThreadTest() {
        ArrayList<Integer> fibonacciSequence;
        Integer length = 10;
        fibonacciSequence = new ArrayList<Integer>(length);
        FibonacciThread fbcThread = new FibonacciThread(length, fibonacciSequence);
        fbcThread.start();
        try {
            fbcThread.join();
            for (Integer f : fibonacciSequence) {
                System.out.print(f + ",");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void nullRelationTest(){
        ArrayList<Integer> fibonacciSequence;
        Integer length = 10;
        fibonacciSequence = new ArrayList<Integer>(length);
        FibonacciThread fbcThread = new FibonacciThread(length, fibonacciSequence);
        fbcThread.start();
        try {
            fbcThread.join();
            for (Integer f : fibonacciSequence) {
                System.out.print(f + ",");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        fibonacciSequence = null;
        if (null == fbcThread.getFibonacciSequence()) {
            System.out.println("\nThe sequence is going to null!");
        } else {
            System.out.println("\nThe array member still exists in class");
            for (Integer f : fbcThread.getFibonacciSequence()) {
                System.out.print(f + ",");
            }
        }
    }
    @Test
    public void removeArrayListTest(){
        ArrayList<Integer> fibonacciSequence;
        int length = 10;
        fibonacciSequence = new ArrayList<Integer>(length);
        FibonacciThread fbcThread = new FibonacciThread(length, fibonacciSequence);
        fbcThread.start();
        try {
            fbcThread.join();
            for (Integer f : fibonacciSequence) {
                System.out.print(f + ",");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        fibonacciSequence.removeAll(fibonacciSequence);
        if (fbcThread.getFibonacciSequence().isEmpty()) {
            System.out.println("\nThe sequence is empty!");
        } else {
            System.out.println("\nThe array member still exists in class");
            for (Integer f : fbcThread.getFibonacciSequence()) {
                System.out.print(f + ",");
            }
        }
    }
}
