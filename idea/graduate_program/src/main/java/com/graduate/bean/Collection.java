package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 藏书对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    int id;
    int userId;
    int bookId;
    Date date;
}
