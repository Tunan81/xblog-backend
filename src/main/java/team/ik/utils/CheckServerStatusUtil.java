package team.ik.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class CheckServerStatusUtil {

    /**
     * 测试 IP 和 端口 状态
     *
     * @param host    主机地址
     * @param port    端口号
     * @param timeout 超时时间（ms）
     * @return boolean
     */
    public static boolean testIpAndPort(String host, int port, int timeout) {
        Socket s = new Socket();
        boolean status = false;
        try {
            s.connect(new InetSocketAddress(host, port), timeout);
            log.info("ip及端口访问正常");
            status = true;
        } catch (IOException e) {
            log.warn(host + ":" + port + "无法访问！");
        } finally {
            try {
                s.close();
            } catch (IOException ex) {
                log.warn("关闭socket异常" + ex);
            }
        }
        return status;
    }
}
