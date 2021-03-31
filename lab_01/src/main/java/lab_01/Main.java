package lab_01;


import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] argv) {
        System.out.println("======== CLOCKS ========");

        try {
            IClock clock = FClock.build(ClockType.Advanced);
            clock.setSeconds(58);
            clock.setMinutes(10);
            clock.setHours(5);
            clock.printInformation();

            IAlarm alarm1 = FAlarm.build(ClockType.Advanced);
            alarm1.setSeconds(0);
            alarm1.setMinutes(11);
            alarm1.setHours(5);
            alarm1.printTime();
            clock.addAlarm(alarm1);

            IAlarm alarm2 = FAlarm.build(ClockType.Advanced);
            alarm2.setSeconds(10);
            alarm2.setMinutes(11);
            alarm2.setHours(5);
            alarm2.printTime();
            clock.addAlarm(alarm2);

            clock.start();
            Thread.sleep(30000);
            clock.stop();
            clock.printInformation();
        } catch (IllegalArgumentException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
