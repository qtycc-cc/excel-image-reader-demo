package com.example.demo.controller;

import com.example.demo.entity.CellData;
import com.example.demo.strategy.ExcelServiceStrategy;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    @Resource
    private ExcelServiceStrategy excelServiceStrategy;

    @PostMapping("/upload")
    public List<CellData> upload(@RequestParam("file")MultipartFile file) {
        return excelServiceStrategy.getExcelService(file.getOriginalFilename()).parseExcel(file);
    }
}
