package itxfpc.model;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.junit.Test;

public class FibonacciCallableTest {

    @Test
    public void callFibonaccCallableTest() {
        Integer length = 20;
        FibonacciCallable fbcCallalbe = new FibonacciCallable(length);
        // 創建 FutureTask 存放 Callable 即將回傳的值
        FutureTask<List<Integer>> fbcTask = new FutureTask<List<Integer>>(fbcCallalbe);
        // 執行 FutureTask 取得運算的回傳值
        Thread t = new Thread(fbcTask);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            for (Integer i : fbcTask.get()) {
                System.out.print(i + " ");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
