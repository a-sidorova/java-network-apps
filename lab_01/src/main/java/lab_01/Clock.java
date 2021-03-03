package lab_01;

import java.io.IOException;

public class Clock {
    protected String type;
    protected String brand;
    protected Integer cost;
    protected Integer hours, minutes;

    public Clock(String brand, Integer cost, Integer hours, Integer minutes) throws Exception {
        this.type = "Common";
        this.brand = brand;
        this.cost = cost;

        setHours(hours);
        setMinutes(minutes);
    }

    protected void checkValue(Integer value) throws Exception {
        if (value < 0)
            throw new Exception("Value must be not negative!");
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setHours(Integer hours) throws Exception {
        try {
            checkValue(hours);
            this.hours = hours % 24;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setMinutes(Integer minutes) throws Exception {
        try {
            checkValue(minutes);
            setHours(this.hours + minutes / 60);
            this.minutes = minutes % 60;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getHours() {
        return this.hours;
    }

    public Integer getMinutes() {
        return this.minutes;
    }

    public String getBrand() {
        return this.brand;
    }

    public Integer getCost() {
        return this.cost;
    }

    public void increaseTime(Integer value) throws Exception {
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
        private Integer seconds;

        public AdvancedClock(String brand, Integer cost, Integer hours, Integer minutes, Integer seconds) throws Exception {
            super(brand, cost, hours, minutes);

            this.type = "Advanced";
            setSeconds(seconds);
        }

        public void setSeconds(Integer seconds) throws Exception {
            try {
                checkValue(seconds);
                setMinutes(this.minutes + seconds / 60);
                this.seconds = seconds % 60;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public Integer getSeconds() {
            return this.seconds;
        }

        @Override
        public void increaseTime(Integer value) throws Exception {
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
