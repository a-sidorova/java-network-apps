import java.util.ArrayList;

public class Response {
    ArrayList<String> listAlarms = new ArrayList<String>();
    String time = null;
    boolean alarmFlag;
    boolean start = false;
    boolean stop = false;
    boolean pause = false;

    public void addAlarm(String alarm) {
        listAlarms.add(alarm);
    }

    public ArrayList<String> getListAlarms() {
        return listAlarms;
    }

    public void setNewTime(String time) {
        this.time = time;
    }

    public void setAlarmFlag(boolean flag) {
        alarmFlag = flag;
    }

    public void setStart(boolean status) { start = status; }

    public void setStop(boolean status) { stop = status; }

    public void setPause(boolean status) { pause = status; }
}