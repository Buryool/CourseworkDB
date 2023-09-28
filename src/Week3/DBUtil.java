package Week3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类提供了用于访问和检索数据库内容的静态函数
 */
public class DBUtil {

    // 连接数据库
    public static Connection getConnection(String dbDriver, String dbURL, String username, String password) {

        Connection connection = null;

        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbURL, username, password);
            return connection;
        } catch (Exception e) {
            System.out.println("Database cannot be connected!");
            e.printStackTrace();
        }
        return null;
    }

    /**连接到本地数据库并返回conenction。
     * @retutn 数据库conenction;如果无法连接数据库，则返回null。
     */
    public static Connection getLocalConnection() {
        String databaseAddress = "jdbc:mysql://localhost:3306/distibutysytem_db1";
        String databaseUsername = "root";
        String databasePassword = "1313";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            Connection connection = DriverManager.getConnection(databaseAddress, databaseUsername, databasePassword);
            System.out.println("数据库连接成功！");
            return connection;
        } catch (Exception e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
            return null;
        }

    }



    /**关闭数据库连接。
     *@param c 数据库连接对象。
     */
    public static void close(Connection c) {
        if (c != null) {
            try {
                c.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                /* ignore close errors */
                System.err.println("Database cannot be closed!");
                e.printStackTrace();
            }
        }
    }

    /**
     * @param rsmd JDBC ResultSetMetaData对象。
     * @return 包含列名的字符串列表。
     */
    public static List<String> getColumnNames(ResultSetMetaData rsmd) {

        try {
            int colNum = rsmd.getColumnCount();
            List<String> colNames = new ArrayList<String>();

            for (int i = 1; i <= colNum; i++) {
                colNames.add(rsmd.getColumnName(i));
            }

            return colNames;
        } catch (SQLException e) {
            System.err.println("Error in retrieving column names.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param rset JDBC结果集
     * @return 列表的列表包含一个表的元素
     */
    public static List<List> getResult(ResultSet rset) {

        List<List> result = new ArrayList<List>();
        List<String> row;

        try {
            int colNum = rset.getMetaData().getColumnCount();
            while (rset.next()) {
                row = new ArrayList<String>();

                for (int i = 1; i <= colNum; i++) {
                    row.add(rset.getString(i));
                }

                result.add(row);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("Error in retrieving data.");
            e.printStackTrace();
        }

        return null;
    }

    public static String listToString(List<List> list) {
        String res = "";
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(i).size(); j++){
                res += list.get(i).get(j);
                res += " ";
            }
            res += "\n";
        }
        return res;
    }

    /**
     * @param rset JDBC结果集
     * @return 列表的列表包含一个表的元素
     */
    public static List<List> getFilteredResult(ResultSet rset, int col, String filterValue) {

        List<List> result = new ArrayList<List>();
        List<String> row;

        try {
            int colNum = rset.getMetaData().getColumnCount();
            while (rset.next()) {

                //If the value of this colums equals to the filter string, ignore this row.
                if (rset.getString(col).equals(filterValue)) {
                    continue;
                }

                row = new ArrayList<String>();
                for (int i = 1; i <= colNum; i++) {
                    row.add(rset.getString(i));
                }

                result.add(row);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("Error in retrieving data.");
            e.printStackTrace();
        }

        return null;
    }
}
