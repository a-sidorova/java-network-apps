public class Request {
    ClockAdvanced clock;
    AlarmAdvanced alarm;
    Action state;
    int removingIdx;

    public void setClock(ClockAdvanced clock) {
        this.clock = clock;
    }

    public void setAlarm(AlarmAdvanced alarm) {
        this.alarm = alarm;
    }

    public void setStatus(Action state) {
        this.state = state;
    }

    public void setRemovingIdx(int removingIdx) {
        this.removingIdx = removingIdx;
    }
}
