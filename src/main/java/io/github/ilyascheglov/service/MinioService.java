package io.github.ilyascheglov.service;

import io.github.ilyascheglov.config.MinioProperties;
import io.github.ilyascheglov.exception.MinioException;
import io.minio.*;
import io.minio.http.Method;

import java.io.InputStream;
import java.util.UUID;

public class MinioService {

    private static final int OBJECT_SIZE = -1;
    private static final int PART_SIZE = 10485760;

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(MinioProperties minioProperties) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioProperties.getMinioUrl())
                .credentials(minioProperties.getLoginKey(), minioProperties.getPasswordKey())
                .build();
        this.bucketName = minioProperties.getBucketName();

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new MinioException("Error creating bucket because: " + e.getMessage());
        }
    }

    public String uploadFile(String objectName, String contentType, InputStream file) {
        try {
            String savedName = UUID.randomUUID() + objectName;
            minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(savedName)
                        .stream(file, OBJECT_SIZE, PART_SIZE)
                        .contentType(contentType)
                        .build());

            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new MinioException("Error putting file to minio because: " + e.getMessage());
        }
    }

    public void removeFile(String objectName) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            } else {
                throw new MinioException("Бакета с именем " + bucketName + " не существует");
            }
        } catch (Exception e) {
            throw new MinioException("Произошла ошибка: " + e.getMessage());
        }
    }
}