import java.util.ArrayList;

public interface IClock {
    int getHours();
    int getMinutes();
    int getSeconds();
    int getCost();
    String getBrand();
    String getType();
    ArrayList<IAlarm> getAlarms();
    int getStep();

    void setHours(int hours);
    void setMinutes(int minutes);
    void setSeconds(int seconds);
    void setCost(int cost);
    void setBrand(String brand);

    void addAlarm(IAlarm alarm);
    void deleteAlarm(IAlarm alarm);
    void increaseTime(int value);
    void printInformation();
    IAlarm checkAlarms();
}
