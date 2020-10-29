package com.aortiz.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Layout
{
    private Integer id;
    private List<Field> fields;
}
