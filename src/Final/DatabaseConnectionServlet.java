package Final;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.SQLException;

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
