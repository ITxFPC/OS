package itxfpc.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreTA implements Runnable {
    private final static int MAX_TEACH_STUDENTS = 1;
    private Semaphore semaphore = new Semaphore(MAX_TEACH_STUDENTS, true);
    private int status = 0; // 0:normal, 1:busy, -1:sleep
    SemaphoreStudent student = null;
    SemaphoreChair chair;

    public SemaphoreTA(SemaphoreChair chair) {
        this.chair = chair;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                if (this.status == 0) {
                    this.goSleep();
                } else if (this.status == 1) {
                    this.goTeaching();
                    this.releaseStudent();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    // 不可以 synchronized 否則會 deadlock
    // 下個第 0 位置的人會呼叫此函數
    // P Operation
    public void askToTeach(SemaphoreStudent student) throws InterruptedException {
        this.semaphore.acquire();
        this.student = student;
        System.out.println("[TA] Hello " + this.student.getName() + ".");
        this.status = 1;
        this.chair.releaseSeatSource(student);
    }

    // V Operation
    synchronized private void releaseStudent() {
        System.out.println("[TA] GoodBye " + this.student.getName() + ".");
        this.student = null;
        this.status = 0;
        this.semaphore.release();
    }

    private void goTeaching() {
        if (null != this.student) {
            try {
                System.out.println("[TA] Teaching " + this.student.getName());
                System.out
                        .println("[TA] Please wait " + this.student.getSleepTime() / 1000 + " sec.");
                Thread.sleep(this.student.getSleepTime());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("[TA] ERROR!!!");
        }

    }

    synchronized private void goSleep() {
        if (this.chair.isEmpty()) {
            if (null == this.student) {
                this.status = -1;
                System.out.println("[TA] I'm sleeping.");
            }
        }
    }

    synchronized public boolean wakeUp() {
        if (this.checkStatus() == -1) {
            this.status = 0;
            return true;
        }
        return false;
    }


    synchronized public int checkStatus() {
        return this.status;
    }



}
