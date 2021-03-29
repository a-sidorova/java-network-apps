package lab_01;

public interface IAlarm {
    void setHours(int hours);
    void setMinutes(int minutes);
    void setSeconds(int seconds);

    int getHours();
    int getMinutes();
    int getSeconds();

    void check(IClock clock);
    void printTime();
}
