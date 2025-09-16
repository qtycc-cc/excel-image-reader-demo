package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CellData {
    private Integer rowIndex;
    private String name;
    private Integer age;
    private List<String> imageUrls = new ArrayList<>();
}
