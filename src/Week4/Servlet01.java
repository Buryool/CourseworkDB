package Week4;

import Week3.DBUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet01")
public class Servlet01 extends HttpServlet {
    private String selectAll_test(){
        String res = null;

        try {
            Connection c = DBUtil.getLocalConnection();
            String query = "Select * from people;";
            Statement statement = c.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<List> result = DBUtil.getResult(resultSet);
            res = DBUtil.listToString(result);
        } catch (SQLException ex) {
            System.err.println("Error in retrieving data.");
            ex.printStackTrace();
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + selectAll_test() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("doGet方法被调用！");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("doPost方法被调用！");
        processRequest(request, response);
    }

    // 获取对Servlet的简要描述
    @Override
    public String getServletInfo() {
        return "Servlet的简要描述~";
    }

}