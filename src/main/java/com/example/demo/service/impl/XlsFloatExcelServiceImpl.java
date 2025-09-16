package com.example.demo.service.impl;

import com.example.demo.entity.PictureInfo;
import com.example.demo.service.AbstractFloatExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("xlsFloatExcelService")
public class XlsFloatExcelServiceImpl extends AbstractFloatExcelService {
    @Override
    protected Map<String, List<PictureInfo>> buildFloatPictureMap(Workbook workbook) {
        Map<String, List<PictureInfo>> pictureMap = new HashMap<>();
        if (!(workbook instanceof HSSFWorkbook book)) {
            return pictureMap;
        }
        HSSFSheet sheet = book.getSheetAt(0);
        HSSFPatriarch drawingPatriarch = sheet.getDrawingPatriarch();
        if (drawingPatriarch != null) {
            //获取所有图像形状
            List<HSSFShape> shapes = drawingPatriarch.getChildren();
            if (shapes != null) {
                //遍历所有形状
                for (HSSFShape shape : shapes) {
                    //获取形状在工作表中的顶点位置信息（anchor锚点）
                    HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                    if (shape instanceof HSSFPicture pic) {
                        HSSFPictureData picData = pic.getPictureData();
                        //图片形状在工作表中的位置, 所在行列起点和终点位置
                        short c1 = anchor.getCol1();
                        int r1 = anchor.getRow1();
                        String key = r1 + "_" + c1;
                        byte[] data = picData.getData();
                        //文件扩展名
                        String suffix = picData.suggestFileExtension();
                        log.info("图片位置：{}，扩展名：{}，大小：{}", key, suffix, data.length);
                        pictureMap.computeIfAbsent(key, k -> new ArrayList<>()).add(new PictureInfo(data, suffix, r1, c1,pictureMap.get(key).size() + 1));
                    }
                }
            }
        }
        return pictureMap;
    }
}
