package itxfpc;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.junit.Test;
import itxfpc.semaphore.SemaphoreStudent;
import itxfpc.semaphore.SemaphoreTA;
import itxfpc.semaphore.SemaphoreChair;
public class SemaphoreTest {


    @Test
    public void SemaphoreSituationTest() throws InterruptedException {
        SemaphoreChair chairs = new SemaphoreChair();
        SemaphoreTA ta = new SemaphoreTA(chairs);
        SemaphoreStudent s1 = new SemaphoreStudent("S1",2000,chairs,ta);
        SemaphoreStudent s2 = new SemaphoreStudent("S2",2000,chairs,ta);
        SemaphoreStudent s3 = new SemaphoreStudent("S3",2000,chairs,ta);
        SemaphoreStudent s4 = new SemaphoreStudent("S4",2000,chairs,ta);
        SemaphoreStudent s5 = new SemaphoreStudent("S5",2000,chairs,ta);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //executorService.execute(chairs);
        executorService.execute(ta);
        executorService.execute(s1);
        executorService.execute(s2);
        executorService.execute(s3);
        executorService.execute(s4);
        executorService.execute(s5);

        Thread.sleep(600000);
  
    }

}
