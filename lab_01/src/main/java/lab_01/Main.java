package lab_01;


import java.io.IOException;
import java.util.Scanner;

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

        try {
            System.out.println("\n###TEST_4 <Getters and Setters>");
            test_getters_and_setters();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void test_start_time() throws Exception {
        Clock clock = new Clock("common", 100, 0, 0);
        System.out.println("1. Information:");
        clock.printInformation();

        Clock.AdvancedClock advancedClock = new Clock.AdvancedClock("casio", 2000, 0, 0, 0);
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

    public static void test_getters_and_setters() throws Exception {
        Clock.AdvancedClock advancedClock = new Clock.AdvancedClock("", 0, 0, 0, 0);
        System.out.println("Information:");
        advancedClock.printInformation();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Set brand: ");
        String brand = scanner.nextLine();
        advancedClock.setBrand(brand);
        System.out.println("Get brand: " + advancedClock.getBrand() + "\n");

        System.out.println("Set cost: ");
        int cost = scanner.nextInt();
        advancedClock.setCost(cost);
        System.out.println("Get cost: " + advancedClock.getCost() + "\n");

        System.out.println("Set hours: ");
        int hours = scanner.nextInt();
        advancedClock.setHours(hours);
        System.out.println("Get hours: " + advancedClock.getHours() + "\n");

        System.out.println("Set minutes: ");
        int minutes = scanner.nextInt();
        advancedClock.setMinutes(minutes);
        System.out.println("Get minutes: " + advancedClock.getMinutes() + "\n");

        System.out.println("Set seconds: ");
        int seconds = scanner.nextInt();
        advancedClock.setSeconds(seconds);
        System.out.println("Get seconds: " + advancedClock.getSeconds() + "\n");

        System.out.println("New information:");
        advancedClock.printInformation();
    }
}
