package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureInfo {
    /**
     * 图片
     */
    private byte[] data;
    /**
     * 图片扩展名
     */
    private String extension;
    /**
     * 行
     */
    private int row;
    /**
     * 列
     */
    private int col;
    /**
     * 第几张图片
     */
    private int index;
}
