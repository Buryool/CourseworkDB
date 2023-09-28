//import java.io.IOException;
//import java.io.PrintWriter;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//// 示例Servlet服务器代码用于处理来自客户端的传入请求。
//@WebServlet(name = "SenoraSrvR", urlPatterns = {"/sent-sentim-comment"})
//public class SenoraSrvR extends HttpServlet {
//
//    private SentimentDetector senoraSrv = new SentimentDetector();
//    private AccessControl control = new AccessControl();
//    private int textSizeLimit = 5000;
//
//    /**
//     * 处理两个HTTP的请求 <code>GET</code> and <code>POST</code> methods.
//     * @param request servlet请求
//     * @param response servlet响应
//     * @throws ServletException 如果发生特定于servlet的错误
//     * @throws IOException 如果发生I/O错误
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String clientAddress = request.getRemoteHost();
//        if (!control.allow(clientAddress)) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "[UNAUTHORIZED ACCESS ERROR]");
//            return;
//        }
//
//        request.setCharacterEncoding("UTF-8");
//        String text = request.getParameter("text");
//        String result = "";
//
//        if (text == null || text.trim().length() == 0) {
//            result = "[NO_INPUT_ERROR]";
//        } else if (text.length() > textSizeLimit) {
//            result = "[TEXT_SIZE_EXCEEDS_" + textSizeLimit + "_CHARS_ERROR]";
//        } else {
//
//            Double score = senoraSrv.getSentimScore(text);
//
//            if (score == null) {
//                result = "-100";
//            } else {
//                result = "" + (senoraSrv.getSentimScore(text)).doubleValue();
//            }
//        }
//
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//
//        try {
//            out.println(result);
//        } finally {
//            out.close();
//        }
//
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     *  处理HTTP<code>GET</code> method.
//     * @param request servlet请求
//     * @param response servlet响应
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * 处理HTTP <code>POST</code> method.
//     * @param request servlet请求
//     * @param response servlet响应
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * 返回servlet的简短描述。
//     * @return 包含servlet描述的字符串
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }
//}
