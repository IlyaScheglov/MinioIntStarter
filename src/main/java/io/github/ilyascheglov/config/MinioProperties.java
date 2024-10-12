package io.github.ilyascheglov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.minio")
public class MinioProperties {

    private String minioUrl;

    private String loginKey;

    private String passwordKey;

    private String bucketName;

    public String getMinioUrl() {
        return minioUrl;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setMinioUrl(String minioUrl) {
        this.minioUrl = minioUrl;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
