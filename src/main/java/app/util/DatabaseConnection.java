package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Romeo on 5/31/2017.
 */
public class DatabaseConnection {
    private static Connection con;


//    public class HideThisFile {
//
//        public static final String DRIVERNAME = "com.mysql.jdbc.Driver";
//        public static final String URL = "jdbc:mysql://{DBHOST}:{DBNAME}";
//        public static final String DBUSER = "{Username}";
//        public static final String DBPASS = "{Password}";
//
//    }

    /**
     * Creates a database connection to the server
     */
    public static Connection getConnection() {
        try {
            Class.forName(HideThisFile.DRIVERNAME);
            try {
                con = DriverManager.getConnection(HideThisFile.URL,
                        HideThisFile.DBUSER, HideThisFile.DBPASS);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }
}
