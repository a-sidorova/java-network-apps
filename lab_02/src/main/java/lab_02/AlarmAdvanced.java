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
    public boolean check(IClock clock) {
        return (this.hours == clock.getHours() && this.minutes == clock.getMinutes() && this.seconds == clock.getSeconds());
    }

    @Override
    public String printTime() {
        return new String(this.hours + "h. " + this.minutes + "m." + this.seconds + "s.");
    }
}
