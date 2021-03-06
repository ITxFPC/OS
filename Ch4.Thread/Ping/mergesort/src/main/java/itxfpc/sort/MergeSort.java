package itxfpc.sort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MergeSort {
    private final ForkJoinPool pool;

    private static class MergeSortTask extends RecursiveAction {
        private final int[] array;
        private final int low;
        private final int high;
        private static final int THRESHOLD = 1000;

        /**
         * Creates a {@code MergeSortTask} containing the array and the bounds of the array
         *
         * @param array the array to sort
         * @param low   the lower element to start sorting at
         * @param high  the non-inclusive high element to sort to
         */
        protected MergeSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
            // System.out.println(this.low+" "+this.high);
        }

        @Override
        protected void compute() {
            if (high - low <= THRESHOLD) {
                // Arrays.sort(array, low, high);
                int middle = low + ((high - low) >> 1);
                if(middle == low){
                    return;
                }
                new MergeSortTask(array, low, middle).compute();
                new MergeSortTask(array, middle, high).compute();
                merge(middle);
            } else {
                int middle = low + ((high - low) >> 1); // >> 向右移位可以除以二
                // Execute the sub tasks and wait for them to finish
                invokeAll(new MergeSortTask(array, low, middle),
                        new MergeSortTask(array, middle, high));
                // Then merge the results
                merge(middle);
            }
        }

        /**
         * Merges the two sorted arrays this.low, middle - 1 and middle, this.high - 1
         * 
         * @param middle the index in the array where the second sorted list begins
         */
        private void merge(int middle) {
            if (array[middle - 1] < array[middle]) {
                return; // the arrays are already correctly sorted, so we can skip the merge
            }
            int[] copy = new int[high - low];
            // src , start, copy, copy_start, wanted_copy_lenghth
            System.arraycopy(array, low, copy, 0, copy.length);
            int copyLow = 0;
            int copyHigh = high - low;
            int copyMiddle = middle - low;

            for (int i = low, p = copyLow, q = copyMiddle; i < high; i++) {
                if (q >= copyHigh || (p < copyMiddle && copy[p] < copy[q])) {
                    array[i] = copy[p++];
                } else {
                    array[i] = copy[q++];
                }
            }
        }
    }

    /**
     * Creates a {@code MergeSort} containing a ForkJoinPool with the indicated parallelism level
     * 
     * @param parallelism the parallelism level used
     */
    public MergeSort(int parallelism) {
        pool = new ForkJoinPool(parallelism);
    }

    /**
     * Sorts all the elements of the given array using the ForkJoin framework
     * 
     * @param array the array to sort
     */
    public void sort(int[] array) {
        ForkJoinTask<Void> job = pool.submit(new MergeSortTask(array, 0, array.length));
        job.join();
    }
}
