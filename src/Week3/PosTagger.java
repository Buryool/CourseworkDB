package Week3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PosTagger {

    public String callPosTaggerPost(String myMessage) {

        String serviceUrl = "{Add Service URL, e.g. http://www.abc.cn/}";

        //假设服务使用变量“message”来接收消息
        String myRequest = "message=" + myMessage;

        try {

            URL url = new URL(serviceUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置服务请求方式为Post。
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //发送我的请求
            conn.getOutputStream().write(myRequest.getBytes(StandardCharsets.UTF_8));

            //从服务获得应答
            conn.getInputStream();
            InputStream content = conn.getInputStream();

            //
            byte[] buf = new byte[1024];
            ByteArrayOutputStream sb = new ByteArrayOutputStream();
            int i;
            while ((i = content.read(buf)) != -1) {
                sb.write(buf, 0, i);
            }
            content.close();

            //将字节数据转换为字符串
            String responseFromServer = sb.toString();

            return responseFromServer;

        } catch (IOException ex) {
            return null;
        }

    }
}
