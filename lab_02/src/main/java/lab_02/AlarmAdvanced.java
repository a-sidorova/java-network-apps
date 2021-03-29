package lab_02;

public class AlarmAdvanced extends Alarm {
    int seconds;

    @Override
    public void setSeconds(int seconds) {
        try {
            checkValue(seconds);
            setMinutes(this.minutes + seconds / 60);
            this.seconds = seconds % 60;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getSeconds() {
        return this.seconds;
    }

    @Override
    public void check(IClock clock) {
       // System.out.println("this: " + this.hours + " " + this.minutes + " " + this.seconds + "\n" + "clock: " + clock.getHours() + " " + clock.getMinutes() + " " + clock.getSeconds());

        if (this.hours == clock.getHours() && this.minutes == clock.getMinutes() && this.seconds == clock.getSeconds()) {
            System.out.println("!!!ALARM!!!");
            clock.printInformation();
        }
    }

    @Override
    public void printTime() {
        System.out.println("\tAlarm: " + this.hours + "h. " + this.minutes + "m. " + this.seconds + "s.");
    }
}
