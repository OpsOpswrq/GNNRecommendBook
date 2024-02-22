package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    int id;
    int bookId;
    int userId; // 头像,评分和评论  连表查询  链接 user  ID 属性
    String content;
    int rate; // 可以计算本站的平均评分
    Date date;
}
