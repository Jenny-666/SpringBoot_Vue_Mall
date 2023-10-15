package com.wen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("orders")
@Data
public class Order {
    @TableId(value="orderId",type= IdType.AUTO)
    private Integer orderId;
    @TableField("goodsId")
    private Integer goodsId;
    @TableField("goodsName")
    private String goodsName;
    @TableField("userId")
    private Integer userId;
    @TableField("buyerId")
    private Integer buyerId;
    private Integer num;
    @TableField("totalPrice")
    private Double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("creatTime")
    private Date creatTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("endTime")
    private Date endTime;
    private Integer status;

}
