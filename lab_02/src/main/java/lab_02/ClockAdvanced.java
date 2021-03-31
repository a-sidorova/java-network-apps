package lab_02;

public class ClockAdvanced extends Clock {
    int seconds = 0;

    public ClockAdvanced() {
        super();

        this.type = "Advanced";
        this.step = 1000;
    }

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
    public void increaseTime(int value) {
        try {
            checkValue(value);
            setSeconds((this.seconds + value));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String printTime() {
        return new String(this.hours + "h. " + this.minutes + "m. " + this.seconds + "s. ");
    }
}
