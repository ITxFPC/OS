package itxfpc.semaphore;


import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class SemaphoreChair implements Runnable {
    private static final  int MAX_CHAIRS_NUMBER = 3;
    private Semaphore semaphore = new Semaphore(MAX_CHAIRS_NUMBER, true);
    // private boolean[] isUsed = new boolean[MAX_CHAIRS_NUMBER];
    private LinkedList<SemaphoreStudent> seats = new LinkedList<>();


    @Override
    public void run() {
        while (true) {
            try {
                // TODO: 如果沒有 sleep 而 Busy waiting 會導致其他 thread 無法執行而不會改變狀態
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (checkFirstSeat()) {
                this.releaseSeatSource(this.seats.getFirst());
            }
        }

    }

    private boolean checkFirstSeat() {

        if (this.seats.size() > 0) {
            System.out.println(seatStatus()+ "First seat is " + this.seats.getFirst().getName());
            if (this.seats.getFirst().getStatus() == 2) {
                return true;
            }
        }
        return false;
    }

    public int getSeatIndex(SemaphoreStudent student) {

        return this.seats.indexOf(student);

    }

    public int getEmptySeatsNumber() {
        return MAX_CHAIRS_NUMBER - this.seats.size();
    }

    public boolean isEmpty() {
        return this.seats.size() == 0;
    }

    // P Operation
    public void getSeat(SemaphoreStudent student) throws InterruptedException {
        System.out.println(seatStatus() + student.getName() + " ask for a seat.");
        this.semaphore.acquire();
        this.addStudent(student);
    }

    // V Operation
    public void releaseSeatSource(SemaphoreStudent student) {
        this.removeStudent(student);
        this.semaphore.release();
    }


    synchronized private void addStudent(SemaphoreStudent student) {
        this.seats.add(student);
        System.out.println(seatStatus() + student.getName() + " is sitting on the number "
                + seats.indexOf(student) + "th seat.");
    }

    synchronized private void removeStudent(SemaphoreStudent student) {
        System.out.println(seatStatus() + student.getName() + " is leaving out the number "
                + seats.indexOf(student) + "th seat.");
        this.seats.remove(student);

    }

    private String seatStatus() {
        return "[" + this.seats.size() + "/3]: ";
    }



}
