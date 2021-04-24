public class FAlarm {
    public static IAlarm build(ClockType type) {
        if (type == ClockType.Common)
            return new Alarm();
        else if (type == ClockType.Advanced)
            return new AlarmAdvanced();

        return null;
    }
}
