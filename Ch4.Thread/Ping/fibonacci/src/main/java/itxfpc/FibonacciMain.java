package itxfpc;

import java.util.ArrayList;
import java.util.Scanner;

import itxfpc.model.FibonacciRun;
import itxfpc.model.FibonacciThread;

/**
 * Hello world!
 *
 */
public class FibonacciMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> fibonacciSequence = new ArrayList<Integer>();
        System.out.println("Please input a number: ");
        Integer length = scanner.nextInt();
        System.out.println("Length is " + length);
        FibonacciThread fbcRun = new FibonacciThread(length, fibonacciSequence);
        fbcRun.start();
        try {
            fbcRun.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Hello Fibonacci Thread!");
        for (Integer i : fibonacciSequence) {
            System.out.print(i + " ");
        }
        

    }
}
