package com.example.demo.service;

import com.example.demo.entity.CellData;
import com.example.demo.service.impl.ExcelServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ExcelService {
    List<List<CellData>> parseExcel(MultipartFile file);
}
