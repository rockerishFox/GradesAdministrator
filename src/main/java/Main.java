import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Hello");
        String connectionUrl = "jdbc:sqlserver://localhost;database=GradesAdministrator;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM vw_students";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(
                                rs.getInt("id") + " " +
                                rs.getString("nume") + " " +
                                rs.getString("prenume") + " " +
                                rs.getString("profesor") + " " +
                                rs.getString("grupa")
                        );
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
