package team.ik.config;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

/**
 * Undertow配置类
 * 解决 Undertow 启动警告日志的问题
 * 参考文档 ：<a href="https://springdoc.cn/spring-boot-undertow/">...</a>
 */
@Configuration
public class UndertowConfiguration implements WebServerFactoryCustomizer<UndertowServletWebServerFactory>{

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {

            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();

            // 设置合理的参数
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(true, 2048));

            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
    }
}