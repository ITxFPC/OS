package itxfpc.model;

import java.util.ArrayList;

public class FibonacciThread extends Thread {
    private Integer sequenceLength;
    private ArrayList<Integer> fibonacciSequence;

    public FibonacciThread(Integer sequenceLength, ArrayList<Integer> fibonacciSequence) {
        this.sequenceLength = sequenceLength;
        this.fibonacciSequence = fibonacciSequence;
    }

    @Override
    public void run() {
        if (check()) {
            this.countSequnce();
        }
    }

    public ArrayList<Integer> getFibonacciSequence(){
        return this.fibonacciSequence;
    }

    public Integer getSequenceLength(){
        return this.sequenceLength;
    }


    public Boolean check() {
        if (null == fibonacciSequence||null==sequenceLength) {
            return false;
        } else {
            return true;
        }
    }

    public void countSequnce() {
        int f;
        for (int i = 0; i < this.sequenceLength; i++) {
            if (i == 0) {
                f = 0;
            } else if (i == 1) {
                f = 1;
            } else {
                f = this.fibonacciSequence.get(i - 2) + this.fibonacciSequence.get(i - 1);
            }
            this.fibonacciSequence.add(i, f);
        }
    }
}
