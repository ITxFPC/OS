package itxfpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import org.junit.Test;
import itxfpc.prototype.Student1;
import itxfpc.prototype.TechingAssistant1;

public class Test1 {
    @Test
    public void firstTest() throws InterruptedException {
        TechingAssistant1 ta = new TechingAssistant1();
        Student1 t1 = new Student1("t1", 8000, ta);
        Student1 t2 = new Student1("t2", 5000, ta);
        Student1 t3 = new Student1("t3", 7000, ta);
        Student1 t4 = new Student1("t4", 4000, ta);
        Student1 t5 = new Student1("t5", 6000, ta);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(ta);
        executorService.execute(t1);
        executorService.execute(t2);
        executorService.execute(t3);
        executorService.execute(t4);
        executorService.execute(t5);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(100000);
        }

    }

}
