package com.example.demo.service.impl;

import com.example.demo.entity.PictureInfo;
import com.example.demo.service.AbstractFloatExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("xlsxFloatExcelService")
public class XlsxFloatExcelServiceImpl extends AbstractFloatExcelService {

    @Override
    protected Map<String, List<PictureInfo>> buildFloatPictureMap(Workbook workbook) {
        Map<String, List<PictureInfo>> pictureMap = new HashMap<>();
        if (!(workbook instanceof XSSFWorkbook book)) {
            return pictureMap;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        //获取工作表中绘图包
        XSSFDrawing drawing = sheet.getDrawingPatriarch();
        if (drawing != null) {
            //获取所有图像形状
            List<XSSFShape> shapes = drawing.getShapes();
            //遍历所有形状
            for (XSSFShape shape : shapes) {
                //获取形状在工作表中的顶点位置信息（anchor锚点）
                XSSFClientAnchor anchor = (XSSFClientAnchor) shape.getAnchor();
                if (shape instanceof XSSFPicture pic) {
                    //形状获取对应的图片数据
                    XSSFPictureData picData = pic.getPictureData();
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
        return pictureMap;
    }
}
