package com.wen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("goods")
@Data
public class Goods {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("goodsName")
    private String goodsName;
    private String photo;
    private Double price;
    private String instruction;
    private Integer num;
    private Integer status;
    @TableField("userId")
    private Integer userId;
    @TableField("userName")
    private String userName;
    @TableField("kindName")
    private String kindName;
}
