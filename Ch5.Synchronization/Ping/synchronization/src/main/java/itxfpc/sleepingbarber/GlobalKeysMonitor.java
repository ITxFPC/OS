package itxfpc.sleepingbarber;

import java.util.concurrent.Semaphore;

public class GlobalKeysMonitor {
    public static Semaphore taReady = new Semaphore(0, true);
    public static Semaphore accessWRSeats= new Semaphore(1, true);
    public static Semaphore studentReady= new Semaphore(1, true);
    public static int numberOfFreeSeat;
}