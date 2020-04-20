package itxfpc.prototype;



public class TechingAssistant1 implements Runnable {
    Student1[] seat = new Student1[3];
    Student1 desk;
    long teachTime = 10000;
    boolean status; // true: is sleeping, false: active

    public TechingAssistant1() {
        this.status = true;
    }

    public TechingAssistant1(long time) {
        this.teachTime = time;
        this.status = true;
    }

    @Override
    public void run() {
        while (true) {
            if (this.status) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (!this.checkIdle()) {
                if (null == desk) {
                    nextStudent();
                } else {
                    waiting();
                    nextStudent();
                }
                waiting();
                this.desk.notify();
                this.desk = null;
            }
        }


    }

    private void waiting() {
        System.out.println("TA is sleeping!");
        try {
            Thread.sleep(this.teachTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean checkIdle() {
        System.out.println("TA is checking seat!");
        if (null == desk) {
            for (Student1 s : seat) {
                if (null == s) {
                    this.status = true;
                } else {
                    this.status = false;
                    break;
                }
            }
        } else {
            this.status = false;
        }
        return this.status;
    }

    private void nextStudent() {
        this.desk = seat[0];
        System.out.println("The current student is " + this.desk.getName() + " !");
        for (int i = 0; i < seat.length - 1; i++) {
            seat[i] = seat[i + 1];
            seat[i + 1] = null;
        }
    }

    public boolean getStatus() {
        return this.status;
    }

    synchronized public int getFreeSeat() {
        for (int i = 0; i <= this.seat.length; i++) {
            if (null == seat[i]) {
                System.out.println("Seat " + i + " is FREE!");
                return i;
            }
        }
        System.out.println("The seats are full!");
        return -1;
    }

    public void setSeat(int i, Student1 s) {
        this.seat[i] = s;
    }

}
