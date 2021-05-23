import java.sql.*;
import java.util.ArrayList;

public class DBHandler {
    private static final String ADDRESS_CONNECTION = "jdbc:sqlite:" +
            "C:\\Users\\Alexandra Sidorova\\Documents\\java-network-apps\\lab_04\\alarms.db";

    private static DBHandler instance = null;

    public  static  synchronized DBHandler getInstance() {
        if (instance == null) {
            instance = new DBHandler();
        }
        return instance;
    }

    private Connection connection;

    private DBHandler() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(ADDRESS_CONNECTION);
            System.out.println("Opened db connection successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<AlarmAdvanced> getListFromDB() {
        ArrayList<AlarmAdvanced> resList = new ArrayList<AlarmAdvanced>();
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("select * from alarms");
            while (res.next()) {
                AlarmAdvanced alarm = new AlarmAdvanced();
                alarm.setHours(res.getInt("hours"));
                alarm.setMinutes(res.getInt("minutes"));
                alarm.setSeconds(res.getInt("seconds"));

                resList.add(alarm);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resList;
    }

    public void saveAlarmToDB(IAlarm alarm) {
        try {
            PreparedStatement pst =
                    connection.prepareStatement("insert into alarms('hours', 'minutes', 'seconds')" +
                            "values(?, ?, ?)");
            pst.setObject(1, alarm.getHours());
            pst.setObject(2, alarm.getMinutes());
            pst.setObject(3, alarm.getSeconds());

            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAlarmFromDB(IAlarm alarm) {
        try {
            PreparedStatement pst =
                    connection.prepareStatement("delete from alarms where hours = ? and minutes = ? and seconds = ?");
            pst.setObject(1, alarm.getHours());
            pst.setObject(2, alarm.getMinutes());
            pst.setObject(3, alarm.getSeconds());

            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}