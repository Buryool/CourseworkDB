package Week3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            Connection c = DBUtil.getLocalConnection();
            String query = "Select * from people;";
            Statement statement = c.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<List> result = DBUtil.getResult(resultSet);
            System.out.println(DBUtil.listToString(result));
        } catch (SQLException ex) {
            System.err.println("Error in retrieving data.");
            ex.printStackTrace();
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
