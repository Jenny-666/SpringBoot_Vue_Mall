package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.common.Result;
import com.wen.entity.Swiper;
import com.wen.mapper.SwiperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/swiper")
public class SwiperController {
    @Resource
    @Autowired
    @Mapper
    SwiperMapper swiperMapper;
//  添加轮播图
    @PostMapping
    public Result<?> add(@RequestBody Swiper swiper){
        swiperMapper.insert(swiper);
        return Result.success();
    }
//  更新
    @PutMapping
    public Result<?> update(@RequestBody Swiper swiper){
        swiperMapper.updateById(swiper);
        return Result.success();
    }
//  删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        swiperMapper.deleteById(id);
        return Result.success();
    }
//  查询，页面显示
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search
                              ){
        LambdaQueryWrapper<Swiper> wrapper = Wrappers.<Swiper>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Swiper::getId,search);
        }
        Page<Swiper> swiperPage = swiperMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(swiperPage);
    }

}
