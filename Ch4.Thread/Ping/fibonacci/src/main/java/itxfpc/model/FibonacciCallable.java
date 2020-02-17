package itxfpc.model;

import java.util.List;
import java.util.concurrent.Callable;

import main.java.itxfpc.model.Fibonacci;

public class FibonacciCallable extends Fibonacci implements Callable<List<Integer>>{
    FibonacciCallable(){
        super();
    }
    FibonacciCallable(Integer sequenceLength){
        super(sequenceLength);
    }
    FibonacciCallable(String sequenceLength){
        super(sequenceLength);
    }

    @Override
    public List<Integer> call() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}