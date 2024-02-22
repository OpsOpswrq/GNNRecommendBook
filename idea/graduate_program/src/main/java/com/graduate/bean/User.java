package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int Id;
    String userId;
    String name;
    int age;
    int sex;
    String picture;
    String email;
    String password;
    String address;
}
