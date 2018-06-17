import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveTest {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";


    public static void main(String... args) throws Exception{

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "hiveuser", "hivepass");
        Statement stmt = con.createStatement();

        String tableName = "empdata";
        stmt.executeQuery("drop table " + tableName);
        ResultSet res = stmt.executeQuery("create table " + tableName  + " (id int, name string, dept string)");

        String sql = "show tables '" + tableName + "'";
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }
        res.close();


        sql = "describe " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(2));
        }
        res.close();
        con.close();
    }
}
