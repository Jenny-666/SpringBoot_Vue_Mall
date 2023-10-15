package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.common.Result;
import com.wen.entity.Buyer;
import com.wen.mapper.BuyerMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
//买家
@RestController
@RequestMapping("/buyer")
public class BuyerController {
    @Resource
    BuyerMapper buyerMapper;
    @PostMapping
    public Result<?> save(@RequestBody Buyer buyer){
        if (buyer.getPassword()==null){
            buyer.setPassword("123456");
        }
        buyerMapper.insert(buyer);
        return Result.success();
    }
//  修改
    @PutMapping
    public Result<?> update(@RequestBody Buyer buyer){
        buyerMapper.updateById(buyer);
        return Result.success();
    }
//  删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        buyerMapper.deleteById(id);
        return Result.success();
    }
//  根据id查询 买家个人信息
    @GetMapping ("/{id}")
    public Result<?> getById(@PathVariable Long id){
        Buyer buyer = buyerMapper.selectById(id);
        return Result.success(buyer);
    }
//  管理员查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(name = "role", required = false) Serializable role
    ){
        LambdaQueryWrapper<Buyer> wrapper = Wrappers.<Buyer>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Buyer::getNickName,search);
        }
        if (role != null) wrapper.eq(Buyer::getRole, role);
        Page<Buyer> buyerPage = buyerMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(buyerPage);
    }
//  买家登录
    @PostMapping("/adminLogin")
    public Result<?> login(@RequestBody Buyer buyer){
        Buyer res = buyerMapper.selectOne(Wrappers.<Buyer>lambdaQuery().eq(Buyer::getBuyername, buyer.getBuyername()).eq(Buyer::getPassword, buyer.getPassword()));
        if (res==null){
            return Result.error("-1","用户名或密码错误");
        }
        if(res.getStatus() == 1)
            return Result.success(res);
        else if(res.getStatus() == 0)
            return Result.error("-1","审核中,请耐心等待！");
        else
            return Result.error("-1","该账号审核失败，请注册新账号！");
    }
//  买家注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody Buyer buyer){
        Buyer res = buyerMapper.selectOne(Wrappers.<Buyer>lambdaQuery().eq(Buyer::getBuyername,buyer.getBuyername()));
        if (res!=null){
            return Result.error("-1","该用户名已被注册");
        }
        if (buyer.getPassword()==null){
            buyer.setPassword("123456");
        }
        buyerMapper.insert(buyer);
        return Result.success();
    }
}
