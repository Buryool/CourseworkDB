package Final;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AllInfo")
public class Servlet01 extends HttpServlet {
    private Connection c;

    // 在Servlet初始化的时候获取数据库连接，用来在本类中操作数据库
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        c = (Connection) context.getAttribute("dbConnection");
        System.out.println("数据库连接：" + c);
    }

    private String exeQuery(String query){
        String res;
        Statement statement;

        try {
            statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<List> result = DBUtil.getResult(resultSet);
            res = DBUtil.listToString(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String sql = request.getParameter("sql");
        System.out.println("SQL信息：" + sql);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>数据库全部信息</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + exeQuery(sql) + "</h1>");
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
}