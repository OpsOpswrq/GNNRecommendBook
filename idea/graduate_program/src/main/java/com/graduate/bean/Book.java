package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    int Id;
    String name;
    String price;
    String detail;
    String author;
    int bclass1;
    int bclass2;
    int rate;
    String picture_URL;
    int left;
}
