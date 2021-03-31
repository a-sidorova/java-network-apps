package lab_02;

public interface IClock {
    int getHours();
    int getMinutes();
    int getSeconds();
    int getCost();
    int getStep();
    String getBrand();

    void setHours(int hours);
    void setMinutes(int minutes);
    void setSeconds(int seconds);
    void setCost(int cost);
    void setBrand(String brand);

    void start() throws InterruptedException;
    void stop();

    void increaseTime(int value);
    void addAlarm(IAlarm alarm);
    boolean checkAlarms();
    String printTime();
}
