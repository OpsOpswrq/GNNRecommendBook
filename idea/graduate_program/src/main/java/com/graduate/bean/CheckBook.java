package com.graduate.bean;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBook {
    int id;
    List<JSONObject> books;
    String description;
    Date date;
}
