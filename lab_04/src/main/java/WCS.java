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
        update(m);
    }

    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);

            while(true) {
                String s = dis.readUTF();
                System.out.println("Server got: " + s);

                Request request = gson.fromJson(s, Request.class);

                if (request.state == Action.CREATE) {
                    System.out.println("WCS CREATE");
                    m.addAlarm(request.alarm);
                } else if (request.state == Action.DELETE) {
                    System.out.println("WCS DELETE");
                    m.deleteAlarm(request.alarm);
                } else if (request.state == Action.START) {
                    System.out.println("WCS START");
                    m.start();
                } else if (request.state == Action.STOP) {
                    System.out.println("WCS STOP");
                    m.stop();
                } else if (request.state == Action.PAUSE) {
                    System.out.println("WCS PAUSE");
                    m.pause();
                } else if (request.state == Action.SET) {
                    System.out.println("WCS SET");
                    m.addClock(request.clock);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Response r) {
        try {
            dos.writeUTF(gson.toJson(r));
        } catch (IOException e) {}
    }

    @Override
    public void update(Model model) {
        Response response = new Response();

        if (model.getClock() != null) {
            IClock clock = model.getClock();

            String time = clock.getHours() + ":" + clock.getMinutes() + ":" + clock.getSeconds();
            response.setNewTime(time);
        }

        ArrayList<AlarmAdvanced> alarms = model.getAlarms();
        for (IAlarm alarm : alarms) {
            String alarmStr = alarm.getHours() + ":" + alarm.getMinutes() + ":" + alarm.getSeconds();
            response.addAlarm(alarmStr);
        }

        response.setAlarmFlag(m.alarm);

        if (m.start)
            response.setStart(true);
        else if (m.pause)
            response.setPause(true);
        else if (m.stop)
            response.setStop(true);

       send(response);
    }
}
