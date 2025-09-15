package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {
    @Value("${aliyun.oss.accessKeyId:}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret:}")
    private String accessKeySecret;
    @Value("${aliyun.oss.endpoint:}")
    private String endpoint;
    @Value("${aliyun.oss.bucketName:}")
    private String bucketName;
}
