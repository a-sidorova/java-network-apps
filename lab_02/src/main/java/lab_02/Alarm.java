package lab_02;

public class Alarm implements IAlarm {
    int hours;
    int minutes;

    protected void checkValue(int value) {
        if (value < 0)
            throw new IllegalArgumentException("Value must be not negative!");
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
    public void check(IClock clock) {
        if (this.hours == clock.getHours() && this.minutes == clock.getMinutes()) {
            System.out.println("!!!ALARM!!!");
            clock.printInformation();
        }
    }

    @Override
    public void printTime() {
        System.out.println("\tAlarm: " + this.hours + "h. " + this.minutes + "m.");
    }
}
