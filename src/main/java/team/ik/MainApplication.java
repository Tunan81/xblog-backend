package team.ik;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */

@EnableScheduling
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
@MapperScan("team.ik.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("接口地址:" + "<a href='http://localhost:8101/api/doc.html'>接口地址</a>");
    }
}
