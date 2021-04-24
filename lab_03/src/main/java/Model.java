import javafx.application.Platform;

import java.util.ArrayList;

public class Model {
    ArrayList<IClock> allClocks = new ArrayList<IClock>();
    ArrayList<IObserver> allObservers = new ArrayList<IObserver>();
    Thread t;
    boolean threadFlag = true;
    boolean isPause = false;
    boolean alarm = false;
    boolean start = false;
    boolean pause = false;
    boolean stop = false;

    public void addObserver(IObserver o) {
        allObservers.add(o);
    }

    public void add() {
        for (IClock clock : allClocks) {
            clock.increaseTime(1);
        }

        update();
    }

    public void addClock(IClock clock) {
        allClocks.add(clock);
        update();
    }

    public void addAlarm(IAlarm alarm) {
        allClocks.get(0).addAlarm(alarm);
        update();
    }

    public ArrayList<IClock> getAllClocks() {
        return allClocks;
    }

    void update() {
        for (IObserver o : allObservers)
            o.update(this);
    }

    public void start() {
        this.start = true;
        this.pause = false;
        this.stop = false;

        IClock clock = allClocks.get(0);
        if (t == null) {
            int step = clock.getStep();
            t = new Thread(new Runnable() {
                int step = clock.getStep();
                @Override
                public void run() {
                    threadFlag = true;
                    System.out.println("Clock is starting!");

                    while (threadFlag) {
                        if (isPause) {
                            synchronized (t) {
                                try {
                                    t.wait();
                                } catch (InterruptedException ee) {
                                    ee.printStackTrace();
                                }
                            }
                        }

                        try {
                            alarm = clock.checkAlarms();
                            Thread.sleep(step);
                            clock.increaseTime(1);
                            update();
                        } catch (InterruptedException e) {
                            threadFlag = false;
                        }
                    }
                }
            });
        }

        t.start();
    }

    public void pause() {
        this.start = false;
        this.pause = true;
        this.stop = false;

        if (isPause) {
            isPause = false;

            synchronized (t) {
                t.notify();
            }
        } else {
            isPause = true;
        }

        update();
    }

    public void stop() {
        this.start = false;
        this.pause = false;
        this.stop = true;

        if (t != null) {
            t.interrupt();
            t = null;
            System.out.println("Clock is ending!");
        }

        update();
    }
}
