import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Label;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class WCS extends Thread implements IObserver {

    Socket cs;
    Model m;

    OutputStream os;
    InputStream is;
    DataInputStream dis;
    DataOutputStream dos;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public WCS(Socket cs, Model m) {
        this.cs = cs;
        this.m = m;

        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException e) { }

        m.addObserver(this);
        this.start();
    }

    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);

            while(true) {
                String s = dis.readUTF();
                System.out.println("Server got: " + s);

                if (s.contains(new String("Clock"))) {
                    String[] time = s.replaceAll("Clock", "").split(":");
                    int h = Integer.parseInt(time[0]);
                    int m = Integer.parseInt(time[1]);

                    ClockType type = ClockType.Common;
                    if (time.length == 3)
                        type = ClockType.Advanced;

                    IClock clock = FClock.build(type);
                    clock.setHours(h);
                    clock.setMinutes(m);

                    if (time.length == 3) {
                        int sec = Integer.parseInt(time[2]);
                        clock.setSeconds(sec);
                    }

                    this.m.addClock(clock);
                } else if (s.contains(new String("Alarm"))) {
                    String[] time = s.replaceAll("Alarm", "").split(":");
                    int h = Integer.parseInt(time[0]);
                    int m = Integer.parseInt(time[1]);

                    ClockType type = ClockType.Common;
                    if (time.length == 3)
                        type = ClockType.Advanced;

                    IAlarm alarm = FAlarm.build(type);
                    alarm.setHours(h);
                    alarm.setMinutes(m);

                    if (time.length == 3) {
                        int sec = Integer.parseInt(time[2]);
                        alarm.setSeconds(sec);
                    }

                    this.m.addAlarm(alarm);
                } else if (s.contains(new String("Start"))) {
                    m.start();
                } else if (s.contains(new String("Stop"))) {
                    m.stop();
                } else if (s.contains(new String("Pause"))) {
                    m.pause();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String s) {
        try {
            dos.writeUTF(s);
        } catch (IOException e) {}
    }

    @Override
    public void update(Model model) {
       IClock clock = model.allClocks.get(0);

       String s = new String("Clock");
       s += clock.getHours() + ":" + clock.getMinutes();

       String type = clock.getType();

       if (type == "Advanced")
           s += ":" + clock.getSeconds();

       s += "\n";

       ArrayList<IAlarm> alarms = clock.getAlarms();
       for (IAlarm alarm : alarms) {
           s += "Alarm" + alarm.getHours() + ":" + alarm.getMinutes();
           if (type == "Advanced")
               s += ":" + alarm.getSeconds();

           s += "\n";
       }

       if (m.alarm) {
           s += "Attention\n";
       }

       if (m.start)
           s += "Start\n";
       else if (m.pause)
           s += "Pause\n";
       else if (m.stop)
           s += "Stop\n";

       send(s);
    }
}
