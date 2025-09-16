package com.example.demo.strategy;

import com.example.demo.service.ExcelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExcelServiceStrategy {
    @Resource
    private Map<String, ExcelService> excelServiceMap;

    public ExcelService getExcelService(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }
        if (filename.endsWith(".xlsx")) {
            return excelServiceMap.get("xlsxExcelService");
        } else if (filename.endsWith(".xls")) {
            return excelServiceMap.get("xlsExcelService");
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }
    }
}
