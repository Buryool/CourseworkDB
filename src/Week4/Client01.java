package Week4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Client01 {
    public String connectService(String webURL) {

        String myMessage = "测试信息";

        //假设服务使用可变的“message”来接收消息。
        String myRequest = "message=" + myMessage;

        try {

            //创建带有Web服务地址的URL实例
            URL url = new URL(webURL);

            //连接到Web服务
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置Post服务请求模式。
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //发送请求
            conn.getOutputStream().write(myRequest.getBytes("UTF-8"));

            //从服务部门得到答复
            conn.getInputStream();
            InputStream content = conn.getInputStream();

            //将输入字符串转换为字节数组
            byte[] buf = new byte[1024];
            ByteArrayOutputStream sb = new ByteArrayOutputStream();
            int i = 0;
            while ((i = content.read(buf)) != -1) {
                sb.write(buf, 0, i);
            }

            //关闭Web服务连接
            content.close();

            //将字节数据转换为字符串
            String responseFromServer = sb.toString();

            return responseFromServer;
        } catch (IOException ex) {
            return null;
        }
    }

    public static void main(String[] args) {

        Client01 client01 = new Client01();

        //服务的本地主机地址
        String myURL1 = "http://localhost:8080/Distributy_System_Study_war_exploded/Servlet01";
        String response1 = client01.connectService(myURL1);
        System.out.println(myURL1 + "\n");
        System.out.println(response1);
    }
}