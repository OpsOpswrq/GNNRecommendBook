package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommend {
    int id;
    int userId;
    String sequence1;
    int bclass1;
    String sequence2;
    Date date;
}
