package itxfpc;
import org.junit.Test;
import itxfpc.sort.SortMethod;
import itxfpc.sort.TopDownMergeSort;



public class TopDownMergeSortTest{
    @Test
    public void testMergeSort(){
        int [] sequence = {1,23,1,41,5,43,4,0,444,13,543,7};
        SortMethod sortMethod = new TopDownMergeSort(sequence);
        int [] result = sortMethod.sort(sequence);
        for(int number: result){
            System.out.println(number);
        }
    }
}