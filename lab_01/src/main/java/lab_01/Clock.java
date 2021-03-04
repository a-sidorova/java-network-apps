package lab_01;

import java.io.IOException;

public class Clock {
    protected String type;
    private String brand;
    private int cost;
    protected int hours, minutes;

    public Clock(String brand, int cost, int hours, int minutes) throws Exception {
        this.type = "Common";
        this.brand = brand;
        this.cost = cost;

        setHours(hours);
        setMinutes(minutes);
    }

    protected void checkValue(int value) throws Exception {
        if (value < 0)
            throw new Exception("Value must be not negative!");
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCost(int cost) throws Exception {
        checkValue(cost);
        this.cost = cost;
    }

    public void setHours(int hours) throws Exception {
        try {
            checkValue(hours);
            this.hours = hours % 24;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setMinutes(int minutes) throws Exception {
        try {
            checkValue(minutes);
            setHours(this.hours + minutes / 60);
            this.minutes = minutes % 60;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public String getBrand() {
        return this.brand;
    }

    public int getCost() {
        return this.cost;
    }

    public void increaseTime(int value) throws Exception {
        try {
            checkValue(value);
            setMinutes((this.minutes + value));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printInformation() {
        System.out.println("\tClock type: " + this.type + "\n\tBrand: " + this.brand +"\n\tCost: " + this.cost);
        printTime();
    }

    public void printTime() {
        System.out.println("\tTime: " + this.hours + "h. " + this.minutes + "m.");
    }


    public static class AdvancedClock extends Clock {
        private int seconds;

        public AdvancedClock(String brand, int cost, int hours, int minutes, int seconds) throws Exception {
            super(brand, cost, hours, minutes);

            this.type = "Advanced";
            setSeconds(seconds);
        }

        public void setSeconds(int seconds) throws Exception {
            try {
                checkValue(seconds);
                setMinutes(this.minutes + seconds / 60);
                this.seconds = seconds % 60;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public int getSeconds() {
            return this.seconds;
        }

        @Override
        public void increaseTime(int value) throws Exception {
            try {
                checkValue(value);
                setSeconds((this.seconds + value));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void printTime() {
            System.out.println("\tAdvanced Time: " + this.hours + "h. " + this.minutes + "m. " + this.seconds + "s. ");
        }
    }
}
