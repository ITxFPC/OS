package itxfpc.sort;

public class TopDownMergeSort implements SortMethod {
    int[] sequence;
    int length;
    int[] result;

    public TopDownMergeSort(int[] sequence) {
        this.sequence = sequence;
        this.length = sequence.length;
        this.result = new int[length];
    }

    @Override
    public int[] sort(int[] sequence) {
        mergeRecursive(this.sequence, this.result, 0, this.length - 1);
        return this.sequence;
    }

    private void mergeRecursive(int[] sequence, int[] result, int start, int end) {
        if (start >= end) {
            return;
        }
        int leftStart = start;
        int leftEnd = start + (end - start) / 2;
        int rightStart = start + (end - start) / 2 +1;
        int rightEnd = end;
        //System.out.println(leftStart + " " + leftEnd + " " + rightStart + " " + rightEnd);
        this.mergeRecursive(this.sequence, this.result, leftStart, leftEnd);
        this.mergeRecursive(this.sequence, this.result, rightStart, rightEnd);
        int position = start;
        while (leftStart <= leftEnd && rightStart <= rightEnd) {
            if (this.sequence[leftStart] < this.sequence[rightStart]) {
                this.result[position] = this.sequence[leftStart];
                position = position + 1;
                leftStart = leftStart + 1;
            } else {
                this.result[position] = this.sequence[rightStart];
                position = position + 1;
                rightStart = rightStart + 1;
            }
        }
        while (leftStart <= leftEnd) {
            this.result[position] = this.sequence[leftStart];
            position = position + 1;
            leftStart = leftStart + 1;
        }
        while (rightStart <= rightEnd) {
            this.result[position] = this.sequence[rightStart];
            position = position + 1;
            rightStart = rightStart + 1;
        }
        for (int i = start; i <= end; i++) {
            this.sequence[i] = this.result[i];
        }
    }

}
