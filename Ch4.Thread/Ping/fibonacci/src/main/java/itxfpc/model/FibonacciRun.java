package itxfpc.model;

import main.java.itxfpc.model.Fibonacci;

public class FibonacciRun extends Fibonacci implements Runnable {

    public FibonacciRun(){
        super();
    }
    public FibonacciRun(Integer sequenceLength){
        super(sequenceLength);
    }
    public FibonacciRun(String sequenceLength){
       super(sequenceLength);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
}