import javafx.application.Platform;

import java.util.ArrayList;

public class Model {
    ClockAdvanced clock = null;
    ArrayList<IObserver> allObservers = new ArrayList<IObserver>();
    IAlarm alarmRing = null;
    Thread t;
    boolean threadFlag = true;
    boolean isPause = false;
    boolean alarm = false;
    boolean start = false;
    boolean pause = false;
    boolean stop = false;

    DBHandler dbhandler = DBHandler.getInstance();

    public void addObserver(IObserver o) {
        allObservers.add(o);
    }

    public void add() {
        clock.increaseTime(1);
        update();
    }

    public void addClock(ClockAdvanced clock) {
        this.clock = clock;
        ArrayList<AlarmAdvanced> alarms = getAlarms();
        for (AlarmAdvanced alarm : alarms)
            this.clock.addAlarm(alarm);

        update();
    }

    public void addAlarm(AlarmAdvanced alarm) {
        clock.addAlarm(alarm);
        dbhandler.saveAlarmToDB(alarm);
        update();
    }

    public void deleteAlarm(IAlarm alarm) {
        if (alarm != null) {
            dbhandler.deleteAlarmFromDB(alarm);
            this.clock.alarms.clear();
            ArrayList<AlarmAdvanced> alarms = getAlarms();
            for (AlarmAdvanced a : alarms)
                this.clock.addAlarm(a);
            update();
        }
    }

    public ArrayList<AlarmAdvanced> getAlarms() {
        return dbhandler.getListFromDB();
    }

    public IClock getClock() {
        return clock;
    }

    void update() {
        for (IObserver o : allObservers)
            o.update(this);
    }

    public void start() {
        this.start = true;
        this.pause = false;
        this.stop = false;

        if (t == null) {
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
                            Thread.sleep(step);
                            clock.increaseTime(1);
                            alarmRing = clock.checkAlarms();

                            if (alarmRing != null) {
                                deleteAlarm(alarmRing);
                                alarm = true;
                            } else {
                                alarm = false;
                            }

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
