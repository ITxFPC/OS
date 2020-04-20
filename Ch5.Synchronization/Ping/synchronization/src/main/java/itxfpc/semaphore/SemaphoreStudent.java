package itxfpc.semaphore;

public class SemaphoreStudent implements Runnable {
    private String name;
    private int status = 0; // 0:normal, 1: at the seat, 2: find TA
    private SemaphoreChair chairs;
    private SemaphoreTA ta;
    private long sleepTime;

    public SemaphoreStudent(String name, long sleepTime, SemaphoreChair chairs, SemaphoreTA ta) {
        this.name = name;
        this.sleepTime = sleepTime;
        this.chairs = chairs;
        this.ta = ta;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(this.sleepTime);
                // 沒有此條件會導致重複進入座位而 deadlock
                if (this.status == 0) {
                    // P -> semaphore chair
                    this.chairs.getSeat(this);
                }
                // critical section
                this.changeStatus();
                if (this.status == 2) {
                    break;
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public String getName() {
        return this.name;
    }

    public int getStatus() {
        return this.status;
    }

    public long getSleepTime() {
        return this.sleepTime;
    }

    // critical section
    private void changeStatus() throws InterruptedException {
        int check = this.chairs.getSeatIndex(this);
       // System.out.println(this.name + " is at number " + check + " seats");
        if (0 == check) {
            // P -> semaphore ta
            if(this.ta.checkStatus() == -1){
                System.out.println("[Student] Does "+this.name + " wake up the TA? " + this.ta.wakeUp());
            }
            this.ta.askToTeach(this);
            this.status = 2;
        } else if (check > 0) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }



}
