package team.ik.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tunan
 * @since 2023-10-10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    /**
     * minio服务地址
     */
    private String endpoint;

    /**
     * minio服务账号
     */
    private String accessKey;

    /**
     * minio服务密码
     */
    private String secretKey;

    /**
     * minio服务桶名称
     */
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
