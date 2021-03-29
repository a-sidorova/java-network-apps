package lab_02;

public class FClock {
    public static IClock build(ClockType type) {
        if (type == ClockType.Common)
            return new Clock();
        else if (type == ClockType.Advanced)
            return new ClockAdvanced();

        return null;
    }
}
