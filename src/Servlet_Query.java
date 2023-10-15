import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Query")
public class Servlet_Query extends HttpServlet {
    private Connection c;

    // 在Servlet初始化的时候获取数据库连接，用来在本类中操作数据库
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        c = (Connection) context.getAttribute("dbConnection");
        System.out.println("数据库连接：" + c);
    }

    private String executeQuery(String query){
        String res;
        Statement statement;
        ServletContext context = getServletContext();
        Semaphore rw = (Semaphore) context.getAttribute("rw");
        Semaphore mutex = (Semaphore) context.getAttribute("mutex");
        int readerCount = (int) context.getAttribute("readerCount");

        try {
            statement = c.createStatement();
            // 第一个读进程加锁
            mutex.acquire();
            if (readerCount == 0){
                rw.acquire();
            }
            readerCount++;
            mutex.release();
            // 读
            ResultSet resultSet = statement.executeQuery(query);
            // 最后一个读进程解锁
            mutex.acquire();
            readerCount--;
            if (readerCount == 0){
                rw.release();
            }
            mutex.release();

            List<List> result = DBUtil.getResult(resultSet);
            res = DBUtil.listToString(result);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String sql = request.getParameter("sql");
        System.out.println("SQL语句：" + sql);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>查询结果</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + executeQuery(sql) + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("DB的doPost方法被调用！");
        processRequest(request, response);
    }
}