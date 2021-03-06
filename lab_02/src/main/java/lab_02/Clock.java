package lab_02;

import java.util.ArrayList;
import java.util.EventObject;

public class Clock implements IClock {
    int hours = 0;
    int minutes = 0;
    int cost = 0;
    String type = "Common";
    String brand = "Classic";
    Thread t;
    int step = 60000;
    boolean flag = false;
    ArrayList<IAlarm> alarms;

    public Clock() {
        alarms = new ArrayList<IAlarm>();
    }

    protected void update() {

    }

    protected void checkValue(int value) {
        if (value < 0)
            throw new IllegalArgumentException("Value must be not negative!");
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void setCost(int cost) {
        checkValue(cost);
        this.cost = cost;
    }

    @Override
    public void setHours(int hours) {
        try {
            checkValue(hours);
            this.hours = hours % 24;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setMinutes(int minutes) {
        try {
            checkValue(minutes);
            setHours(this.hours + minutes / 60);
            this.minutes = minutes % 60;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setSeconds(int seconds) {
        throw new IllegalArgumentException("Seconds are not supported!");
    }

    @Override
    public int getHours() {
        return this.hours;
    }

    @Override
    public int getMinutes() {
        return this.minutes;
    }

    @Override
    public int getSeconds() {
        throw new IllegalArgumentException("Seconds are not supported!");
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getStep() {
        return this.step;
    }

    @Override
    public void increaseTime(int value) {
        try {
            checkValue(value);
            setMinutes(this.minutes + value);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start() throws InterruptedException {
        if (t == null) {
            t = new Thread() {
                @Override
                public void run() {
                    flag = true;
                    System.out.println("Clock is starting!");

                    while (flag) {
                        try {
                            increaseTime(1);
                            Thread.sleep(step);
                            checkAlarms();
                        } catch (InterruptedException e) {
                            flag = false;
                        }
                    }
                }
            };

        }

        t.start();
    }

    @Override
    public void stop() {
        if (t != null) {
            t.interrupt();
            t = null;
            System.out.println("Clock is ending!");
        }
    }

    @Override
    public boolean checkAlarms() {
        boolean check = false;
        for (IAlarm alarm : this.alarms) {
            check |= alarm.check(this);
        }

        return check;
    }

    @Override
    public void addAlarm(IAlarm alarm) {
        this.alarms.add(alarm);
    }

    public String printTime() {
        return new String(this.hours + "h. " + this.minutes + "m.");
    }
}
