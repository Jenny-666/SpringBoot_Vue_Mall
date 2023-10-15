package com.wen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("kind")
@Data
public class Kind {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("name")
    private String name;
}
