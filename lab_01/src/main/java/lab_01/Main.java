package lab_01;


import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws Exception {
        System.out.println("======== CLOCKS ========");

        try {
            System.out.println("\n###TEST_1 <Set start time>");
            test_start_time();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("\n###TEST_2 <Change time>");
            test_change_time();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("\n###TEST_3 <Increase time>");
            test_increase_time();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void test_start_time() throws Exception {
        Clock clock = new Clock("common", 100, 0, 0);
        Clock.AdvancedClock advancedClock = new Clock.AdvancedClock("casio", 2000, 0, 0, 0);
        System.out.println("1. Information:");
        clock.printInformation();
        System.out.println("2. Information:");
        advancedClock.printInformation();
    }

    public static void test_change_time() throws Exception {
        Clock.AdvancedClock advancedClock = new Clock.AdvancedClock("casio", 2000, 0, 0, 0);
        System.out.println("Information:");
        advancedClock.printInformation();
        System.out.println("Set 2 112 528 seconds:");
        advancedClock.setSeconds(2112528);
        advancedClock.printInformation();
    }

    public static void test_increase_time() throws Exception {
        Clock.AdvancedClock advancedClock = new Clock.AdvancedClock("overwatch", 15000, 22, 58, 58);
        System.out.println("Information:");
        advancedClock.printInformation();
        System.out.println("Add 1 second:");
        advancedClock.increaseTime(1);
        advancedClock.printInformation();
        System.out.println("Add 120 seconds:");
        advancedClock.increaseTime(120);
        advancedClock.printInformation();
        System.out.println("Add 120 seconds:");
        advancedClock.increaseTime(120);
        advancedClock.printInformation();
    }
}

