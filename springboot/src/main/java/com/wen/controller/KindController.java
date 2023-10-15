package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.common.Result;
import com.wen.entity.Kind;
import com.wen.mapper.KindMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/kind")
public class KindController {
    @Resource
    KindMapper kindMapper;
    @PostMapping
    public Result<?> save(@RequestBody Kind kind){
        Kind res = kindMapper.selectOne(Wrappers.<Kind>lambdaQuery().eq(Kind::getName,kind.getName()));
        if (res!=null){
            return Result.error("-1","该种类名称已存在！");
        }
        kindMapper.insert(kind);
        return Result.success();
    }
//  更新
    @PutMapping
    public Result<?> update(@RequestBody Kind kind){
        kindMapper.updateById(kind);
        return Result.success();
    }
//  删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        kindMapper.deleteById(id);
        return Result.success();
    }
//  查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Kind> wrapper = Wrappers.<Kind>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Kind::getName,search).like(Kind::getId,search);
        }
        Page<Kind> GoodsPage = kindMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(GoodsPage);
    }
//  商品分类列表
    @GetMapping("/kind")
    public Result<?> selectAll(){
        LambdaQueryWrapper<Kind> wrapper = Wrappers.<Kind>lambdaQuery();
        return Result.success( kindMapper.selectList(wrapper));
    }
    @GetMapping ("/{id}")
    public Result<?> getById(@PathVariable Long id){
        Kind kind = kindMapper.selectById(id);
        return Result.success(kind);
    }
}
