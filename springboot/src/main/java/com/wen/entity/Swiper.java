package com.wen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("swiper")
@Data
public class Swiper {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String photo;
}
