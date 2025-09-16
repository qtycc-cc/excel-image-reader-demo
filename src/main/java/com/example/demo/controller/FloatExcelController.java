package com.example.demo.controller;

import com.example.demo.entity.CellData;
import com.example.demo.service.FloatExcelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel/float")
public class FloatExcelController {
    @Resource
    private Map<String, FloatExcelService> excelServiceMap;

    @PostMapping("/xlsx/upload")
    public List<CellData> upload(@RequestParam("file")MultipartFile file) {
        return excelServiceMap.get("xlsxFloatExcelService").parseExcel(file);
    }

    @PostMapping("/xls/upload")
    public List<CellData> uploadXls(@RequestParam("file")MultipartFile file) {
        return excelServiceMap.get("xlsFloatExcelService").parseExcel(file);
    }
}
