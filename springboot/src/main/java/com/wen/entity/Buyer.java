package com.wen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("buyer")
@Data
public class Buyer {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String buyername;
    private String password;
    private String nickName;
    private Integer age;
    private String sex;
    private String address;
    private Integer role;
    private String phone;
    private Integer status;
    private Double wallet;
}
