package aws.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private static Connection con;

    public static Connection getRemoteConnection(String dbName, String userName, String password,
                                                 String hostname, String port) {
        try {
            String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            System.out.println("Getting remote connection...");
            con = DriverManager.getConnection(jdbcUrl);
            System.out.println("Remote connection successful.");
            return con;
        }
        catch (SQLException e) { System.out.println(e.toString());}
        return null;
    }

    public void insertNewPerson() {
        try {
            String insertTableSQL = "INSERT INTO Person VALUES (1, 'Tom', 'Sawyer');";
            Statement statement = con.createStatement();
            statement.executeUpdate(insertTableSQL);
            System.out.println("Record inserted successfully.");
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred while inserting new record.");
        }
    }

    public void closeConnection() {
        try {
            con.close();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred while closing connection.");
        }
    }

}
