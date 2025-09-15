package com.example.demo.service.impl;

import com.example.demo.entity.CellData;
import com.example.demo.service.ExcelService;
import com.example.demo.service.OssService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
public class ExcelServiceImpl implements ExcelService {
    @Resource
    private OssService ossService;

    @Override
    public List<List<CellData>> parseExcel(MultipartFile file) {
        List<List<CellData>> list = new ArrayList<>();
        if (file.isEmpty()) {
            log.warn("文件为空");
            return list;
        }
        String fileName = Optional.of(file).map(MultipartFile::getOriginalFilename).orElse("");
        Workbook workbook = null;
        try {
            if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            if (Objects.isNull(workbook)) {
                log.warn("无法处理的文件类型: {}", fileName);
                return list;
            }
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, List<byte[]>> cellImagesMap = extractCellImages(sheet);

            // 第二步：读取单元格数据并关联图片
            list = readCellDataWithImages(sheet, cellImagesMap);
            workbook.close();
        } catch (Exception e) {
            log.warn("出现异常", e);
        }
        return list;
    }

    private Map<String, List<byte[]>> extractCellImages(Sheet sheet) {
        Map<String, List<byte[]>> cellImagesMap = new HashMap<>();
        if (Objects.isNull(sheet)) {
            return cellImagesMap;
        }
        if (sheet instanceof XSSFSheet xssfSheet) {
            XSSFDrawing drawing = xssfSheet.getDrawingPatriarch();

            if (drawing != null) {
                for (XSSFShape shape : drawing.getShapes()) {
                    if (shape instanceof XSSFPicture picture) {
                        XSSFClientAnchor anchor = picture.getPreferredSize();

                        int row = anchor.getRow1();
                        int col = anchor.getCol1();
                        String cellKey = row + "_" + col;

                        // 获取图片数据
                        XSSFPictureData pictureData = picture.getPictureData();
                        byte[] imageData = pictureData.getData();

                        cellImagesMap.computeIfAbsent(cellKey, k -> new ArrayList<>()).add(imageData);
                    }
                }
            }
        } else if (sheet instanceof HSSFSheet hssfSheet) {
            HSSFPatriarch patriarch = hssfSheet.getDrawingPatriarch();
            if (patriarch != null) {
                for (HSSFShape shape : patriarch.getChildren()) {
                    if (shape instanceof HSSFPicture hssfPicture) {
                        HSSFClientAnchor anchor = (HSSFClientAnchor) hssfPicture.getAnchor();

                        int row = anchor.getRow1();
                        int col = anchor.getCol1();
                        String cellKey = row + "_" + col;

                        // 获取图片数据
                        HSSFPictureData pictureData = hssfPicture.getPictureData();
                        byte[] imageData = pictureData.getData();

                        cellImagesMap.computeIfAbsent(cellKey, k -> new ArrayList<>()).add(imageData);
                    }
                }
            }
        }
        return cellImagesMap;
    }

    private List<List<CellData>> readCellDataWithImages(Sheet sheet, Map<String, List<byte[]>> cellImagesMap) {
        List<List<CellData>> list = new ArrayList<>();
        if (Objects.isNull(sheet) || Objects.isNull(cellImagesMap)) {
            return list;
        }
        return list;
    }
}
