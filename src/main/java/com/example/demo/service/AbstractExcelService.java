package com.example.demo.service;

import com.example.demo.entity.CellData;
import com.example.demo.entity.PictureInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractExcelService implements ExcelService {
    @Override
    public List<CellData> parseExcel(MultipartFile file) {
        List<CellData> result = new ArrayList<>();
        if (file == null || file.isEmpty()) {
            log.warn("File is null or empty");
            return result;
        }
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 只处理第一页
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, List<PictureInfo>> pictureMap = this.buildFloatPictureMap(workbook);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    log.warn("row is null");
                    continue;
                }
                try {
                    CellData data = this.processRow(row, i, pictureMap);
                    log.info("处理行数据: {}", data);
                    result.add(data);
                } catch(Exception e) {
                    log.warn("处理行失败: {}", i, e);
                }
            }
        } catch (Exception e) {
            log.error("Error parsing Excel file", e);
            return result;
        }
        return result;
    }

    /**
     * 构建浮动图片映射
     * @param workbook 工作簿
     * @return 浮动图片映射
     */
    protected abstract Map<String, List<PictureInfo>> buildFloatPictureMap(Workbook workbook);

//    protected abstract Map<String, List<PictureInfo>> buildEmbeddedPictureMap(Workbook workbook);

    private CellData processRow(Row row, int rowIndex, Map<String, List<PictureInfo>> pictureMap) {
        CellData.CellDataBuilder builder = CellData.builder();
        builder.rowIndex(rowIndex);
        try {
            builder.name(row.getCell(0).getStringCellValue());
            builder.age((int) row.getCell(1).getNumericCellValue());
            builder.imageUrls(savePictures(rowIndex, pictureMap));
        } catch(Exception e) {
            log.warn("处理行失败: {}", rowIndex, e);
        }
        return builder.build();
    }

    private List<String> savePictures(int rowIndex, Map<String, List<PictureInfo>> pictureMap) {
        List<String> urls = new ArrayList<>();
        List<PictureInfo> pictures = pictureMap.getOrDefault(rowIndex + "_2", Collections.emptyList());
        for (PictureInfo pic : pictures) {
            // 模拟保存图片并生成URL
            String url = "http://example.com/images/" + System.currentTimeMillis() + "." + pic.getExtension();
            urls.add(url);
        }
        return urls;
    }
}
