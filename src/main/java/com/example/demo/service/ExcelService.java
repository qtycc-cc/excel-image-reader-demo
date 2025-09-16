package com.example.demo.service;

import com.example.demo.entity.CellData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelService {
    List<CellData> parseExcel(MultipartFile file);
}
