package itxfpc;

import java.io.IOException;
import org.junit.Test;
import itxfpc.read.ReadData;
import itxfpc.sort.MergeSort;

public class TestReadData {
    @Test
    public void TestRead() {
        String path = "D:\\讀書會資料\\coding\\OS\\Ch4.Thread\\Ping\\mergesort\\test.txt";
        Long start, end;
        try {
            for (int i = 1; i < 20; i++) {
                int[] data = ReadData.read(path);
                //int [] data = {1,23,1,41,5,43,4,0,444,13,543,7};
                MergeSort sort = new MergeSort(i);
                start = System.nanoTime();
                sort.sort(data);
                end = System.nanoTime();
                System.out.println("P= " + i + " ,Time = " + (end - start) / 1000 + "ms");
                //for (int index = 0; index < data.length; index++) {
                //    System.out.println(data[index]);
                //}

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
