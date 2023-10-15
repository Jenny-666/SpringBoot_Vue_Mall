package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.common.Result;
import com.wen.mapper.UserMapper;
import com.wen.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
//商家，管理员
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @PostMapping
    public Result<?> save(@RequestBody User user){
        if (user.getPassword()==null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
//  更新
    @PutMapping
    public Result<?> update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }
//  删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }
//  根据id查询
    @GetMapping ("/{id}")
    public Result<?> getById(@PathVariable Long id){
        User user = userMapper.selectById(id);
        return Result.success(user);
    }
//  管理员查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(name = "role", required = false) Serializable role
                              ){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickName,search);
        }
        if (role != null) wrapper.eq(User::getRole, role);
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }
//  登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
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
//  注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if (res!=null){
            return Result.error("-1","该用户名已被注册");
        }
        if (user.getPassword()==null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
//  管理员修改商家等级查询
    @GetMapping("selectByStatus")
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(name = "status", required = false) Serializable status,
                               @RequestParam(name = "role", required = false) Serializable role){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (status != null) wrapper.eq(User::getStatus, status);
        if (role != null) wrapper.eq(User::getRole, role);
        if (StrUtil.isNotBlank(search)){
            wrapper.like(User::getUsername,search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

}
