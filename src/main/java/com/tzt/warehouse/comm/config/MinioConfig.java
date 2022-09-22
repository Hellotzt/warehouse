package com.tzt.warehouse.comm.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MinioConfig {
    /**
     * API调用地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * 连接账号
     */
    @Value("${minio.accessKey}")
    private String accessKey;
    /**
     * 连接秘钥
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient getMinioClient(){
        try {
            MinioClient  minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey)
                                .build();
            log.info("minio客户端创建成功");
            return minioClient;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("-----创建Minio客户端失败-----");
            return null;
        }
    }
}