package itxfpc;

import java.util.Scanner;

import itxfpc.model.FibonacciRun;

/**
 * Hello world!
 *
 */
public class FibonacciMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input a number: ");
        Integer length = scanner.nextInt();
        FibonacciRun fbcRun = new FibonacciRun(length);
        
    }
}
