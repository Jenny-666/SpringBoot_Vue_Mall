package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.common.Result;
import com.wen.mapper.GoodsMapper;
import com.wen.mapper.UserMapper;
import com.wen.entity.Goods;
import com.wen.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    @Autowired
    @Mapper
    GoodsMapper goodsMapper;
    @Resource
    @Autowired
    UserMapper userMapper;
//  新增
    @PostMapping
    public Result<?> save(@RequestBody Goods goods){
        User user = userMapper.selectById(goods.getUserId());
        goods.setUserName(user.getUsername());
        this.goodsMapper.insert(goods);
        return Result.success();
    }
//  更新
    @PutMapping
    public Result<?> update(@RequestBody Goods goods){
        goodsMapper.updateById(goods);
        return Result.success();
    }
//  删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        goodsMapper.deleteById(id);
        return Result.success();
    }
//  商家查询自己对应商品
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "userId") Integer userId
                              ){
        LambdaQueryWrapper<Goods> wrapper = Wrappers.<Goods>lambdaQuery();
        if(userId!=null)wrapper.eq(Goods::getUserId,userId);
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Goods::getGoodsName,search);
        }
        Page<Goods> GoodsPage = goodsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(GoodsPage);
    }
//  管理员查询 根据状态审核  买家商品首页商品
    @GetMapping("/audit")
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                               @RequestParam(name = "status", required = false) Serializable status
    ){
        LambdaQueryWrapper<Goods> wrapper = Wrappers.<Goods>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Goods::getGoodsName,search);
        }
        if (status != null) wrapper.eq(Goods::getStatus, status);
        Page<Goods> GoodsPage = goodsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(GoodsPage);
    }
    @GetMapping ("/{id}")
    public Result<?> getById(@PathVariable Long id){
        Goods goods = goodsMapper.selectById(id);
        return Result.success(goods);
    }

}
