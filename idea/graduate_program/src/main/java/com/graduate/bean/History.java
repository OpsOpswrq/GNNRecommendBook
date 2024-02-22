package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    int Id;
    int userId;
    Date date;
    int bookId;
    int bclass1;
    int bclass2;
}
