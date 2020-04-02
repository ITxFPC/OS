package itxfpc.model;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    protected Integer sequenceLength;
    protected List<Integer> fibonacciSequence;

    public Fibonacci() {
        this.sequenceLength = 0;
        this.fibonacciSequence = new ArrayList<Integer>();
    };

    public Fibonacci(Integer sequenceLength) {
        this.sequenceLength = sequenceLength;
        this.fibonacciSequence = new ArrayList<Integer>(sequenceLength);
    }

    public Fibonacci(String sequenceLength) {
        this.sequenceLength = Integer.getInteger(sequenceLength);
        this.fibonacciSequence = new ArrayList<Integer>(this.sequenceLength);
    }

    public void setSequenceLength(String sequenceLength) {
        this.sequenceLength = Integer.getInteger(sequenceLength);
    };

    public void setSequenceLength(Integer sequenceLength) {
        this.sequenceLength = sequenceLength;
    };

    public Integer getSequenceLength() {
        return this.sequenceLength;
    }

    public void setFibonacciSequence(ArrayList<Integer> fibonacciSequence) {
        this.fibonacciSequence = fibonacciSequence;
    }

    public List<Integer> getFibonacciSequence() {
        return this.fibonacciSequence;
    }

    public void countSequnce() {
        if (check()) {
            int f;
            this.fibonacciSequence.clear();
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
        } else {
            System.err.println("[Error] Null member!");
        }

    }

    private Boolean check() {
        if (null == fibonacciSequence || null == sequenceLength) {
            return false;
        } else {
            return true;
        }
    }

    public void printSequence() {
        if (!this.fibonacciSequence.isEmpty()) {
            for (int f : this.fibonacciSequence) {
                System.out.print(f+", ");
            }
        }
    }

}
