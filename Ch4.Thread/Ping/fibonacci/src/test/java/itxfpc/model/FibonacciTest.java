package itxfpc.model;



import org.junit.Test;



public class FibonacciTest{


    @Test
    public void countSequenceTest(){
        int number =5;
        Fibonacci fibonacci = new Fibonacci(number);
        fibonacci.countSequnce();
        fibonacci.printSequence();

    }

}