package com.example.demo.service.impl;

import com.example.demo.service.OssService;
import org.springframework.stereotype.Service;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String upload(byte[] data, String fileFullName) {
        // TODO： 实现上传逻辑，这里只是一个示例
        return "http://" + fileFullName;
    }
}
