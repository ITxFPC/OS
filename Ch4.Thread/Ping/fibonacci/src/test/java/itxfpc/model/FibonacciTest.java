package itxfpc.model;



import java.util.ArrayList;
import org.junit.Test;



public class FibonacciTest{


    @Test
    public void countSequenceTest(){
        int number =5;
        Fibonacci fibonacci = new Fibonacci(number);
        fibonacci.countSequnce();
        fibonacci.printSequence();

    }
    @Test
    public void listFibonacciTest(){
        int number1 =5;
        int number2 =10;
        ArrayList<Fibonacci> list = new ArrayList<Fibonacci>();
        Fibonacci f1 = new Fibonacci(number1);
        list.add(f1);
        f1.countSequnce();
        Fibonacci f2 = list.get(0);
        System.out.println("f1");
        f1.printSequence();
        System.out.println("\nf2");
        f2.printSequence();
        System.out.println("\nTest2");
        f2.setSequenceLength(number2);
        f2.countSequnce();
        System.out.println("\nf1");
        f1.printSequence();
        System.out.println("\nf2");
        f2.printSequence();
        System.out.println("\nlsit");
        list.get(0).printSequence();
        System.out.println("\nTest3");
        f2.getFibonacciSequence().remove(0);
        System.out.println("\nf1");
        f1.printSequence();
        System.out.println("\nf2");
        f2.printSequence();
        System.out.println("\nlsit");
        list.get(0).printSequence();
        System.out.println("\nTest4");
        if(f1.equals(f2)){
            System.out.println("f1=f2");
        }
        if(f2.equals(list.get(0))){
            System.out.println("f2=list.get(0)");
        }
        if(f1.equals(list.get(0))){
            System.out.println("f1=list.get(0)");
        }
        

    }
    
}