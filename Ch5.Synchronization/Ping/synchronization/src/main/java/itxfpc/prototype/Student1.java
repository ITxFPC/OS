package itxfpc.prototype;

public class Student1 implements Runnable {
    private String name;
    private long waitTime;
    private TechingAssistant1 ta;
    private boolean isAtOffice;

    public Student1() {
    }

    public Student1(String name, long waitTime, TechingAssistant1 ta) {
        this.name = name;
        this.waitTime = waitTime;
        this.ta = ta;
        this.isAtOffice = false;
    }

    @Override
    public void run() {
        while (true) {
            this.isAtOffice = false;
            waiting();
            int i = getSeat();
            setSeat(i);
            if (this.ta.getStatus()) {
                System.out.println(this.name + " wake TA up !");
                this.ta.notify();
            }

            if (this.isAtOffice) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    private void waiting() {
        try {
            System.out.println(this.name + " is wating for " + this.waitTime / 1000 + "sec!");
            Thread.sleep(this.waitTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private int getSeat() {
        System.out.println(this.name + " go to find the TA !");
        return this.ta.getFreeSeat();
    }

    private void setSeat(int i) {
        if (i > 0) {
            this.ta.setSeat(i, this);
            System.out.println(this.name + "sit at " + i + " seat wait the TA!");
            this.isAtOffice = true;
        } else {
            System.out.println("The seat is full, " + this.name + " will back to wait TA!");
            this.isAtOffice = false;
        }
    }

    public String getName() {
        return this.name;
    }

}
