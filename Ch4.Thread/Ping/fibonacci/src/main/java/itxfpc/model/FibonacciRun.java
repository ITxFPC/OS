package itxfpc.model;



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
        this.countSequnce();
    }
}