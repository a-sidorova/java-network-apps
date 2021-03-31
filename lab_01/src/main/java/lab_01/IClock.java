package lab_01;

public interface IClock {
    int getHours();
    int getMinutes();
    int getSeconds();
    int getCost();
    String getBrand();

    void setHours(int hours);
    void setMinutes(int minutes);
    void setSeconds(int seconds);
    void setCost(int cost);
    void setBrand(String brand);

    void start() throws InterruptedException;
    void stop();
    void addAlarm(IAlarm alarm);
    void printInformation();
}
