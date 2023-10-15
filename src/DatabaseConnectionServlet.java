import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

/**
 * 存储该context对应的数据库信息，
 * 使得只连接一次数据库，就能让所有的Servlet都获取到数据库信息
 */
@WebServlet(
        name = "DatabaseConnectionServlet",
        urlPatterns = { "/DatabaseConnectionServlet" },
        loadOnStartup = 1 // 设置初始化顺序
)
public class DatabaseConnectionServlet extends HttpServlet {
    private Connection connection;
    @Override
    public void init() throws ServletException {
        super.init();
        // 获取context
        ServletContext context = getServletContext();
        this.connection = DBUtil.getLocalConnection();
        // 设置context对应的数据库
        context.setAttribute("dbConnection", connection);
        // 用于readerCount的互斥访问
        context.setAttribute("mutex", new Semaphore(1));
        // 用于统计读者数量
        context.setAttribute("readerCount", 0);
        // 用于读者和写者互斥
        context.setAttribute("rw", new Semaphore(1));
        // TODO 在这里有时间可以试试如果给context加入变量后原始Servlet销毁，那context中的变量还能不能继续访问
    }

    @Override
    public void destroy() {
        super.destroy();
        ServletContext context = getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
