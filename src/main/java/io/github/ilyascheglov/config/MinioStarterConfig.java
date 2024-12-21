package io.github.ilyascheglov.config;

import io.github.ilyascheglov.service.MinioService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioStarterConfig {

    @Bean
    public MinioService minioService(MinioProperties minioProperties) {
        return new MinioService(minioProperties);
    }
}
