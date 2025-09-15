package com.example.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CellData {
    private String text;
    private List<String> imageUrls = new ArrayList<>();
}
