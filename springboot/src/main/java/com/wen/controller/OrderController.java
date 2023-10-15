package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.common.Result;
import com.wen.entity.Buyer;
import com.wen.entity.Goods;
import com.wen.entity.Order;
import com.wen.entity.User;
import com.wen.mapper.BuyerMapper;
import com.wen.mapper.GoodsMapper;
import com.wen.mapper.OrderMapper;
import com.wen.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController

@RequestMapping("/order")
public class OrderController {
    @Resource
    @Autowired
    @Mapper
    OrderMapper orderMapper;
    @Resource
    @Autowired
    UserMapper userMapper;
    @Resource
    @Autowired
    GoodsMapper goodsMapper;
    @Resource
    @Autowired
    BuyerMapper buyerMapper;
//  购买结算，生成订单
    @PostMapping("/checkOut")
    public Result<?> checkOut(@RequestBody Order orders) {
        Buyer buyer = buyerMapper.selectById(orders.getBuyerId());
        Goods goods = goodsMapper.selectById(orders.getGoodsId());
        User user = userMapper.selectById(orders.getUserId());
        Integer num = goods.getNum() - orders.getNum();
        if (num < 0) return Result.error("-1","库存不足");
        goods.setNum(num);
        orders.setNum(orders.getNum());
        orders.setBuyerId(orders.getBuyerId());
        orders.setGoodsId(goods.getId());
        orders.setUserId(goods.getUserId());
        orders.setGoodsName(goods.getGoodsName());
        orders.setTotalPrice(goods.getPrice() * orders.getNum());
        Double wallet = buyer.getWallet() - orders.getTotalPrice();
        if (wallet < 0) return Result.error("-1","余额不足，请充值！");
        buyer.setWallet(wallet);
        Double wallet2 = user.getWallet() + orders.getTotalPrice();
        user.setWallet(wallet2);
        orderMapper.insert(orders);
        goodsMapper.updateById(goods);
        buyerMapper.updateById(buyer);
        userMapper.updateById(user);
        return Result.success();
    }
//  修改订单状态
    @PutMapping
    public Result<?> update(@RequestBody Order order){
        orderMapper.updateById(order);
        return Result.success();
    }
//  买家查看订单
    @GetMapping("/buyer/{buyerId}")
    public Result<?> buyerSelect(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "buyerId") Integer buyerId
    ){
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        if(buyerId != null)wrapper.eq(Order::getBuyerId,buyerId);
        Page<Order> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(orderPage);
    }
//  商家查看订单
    @GetMapping("/user/{userId}")
    public Result<?> userSelect(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "userId") Integer userId
    ){
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        if(userId!=null)wrapper.eq(Order::getUserId,userId);
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Order::getGoodsName,search);
        }
        Page<Order> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(orderPage);
    }
}
