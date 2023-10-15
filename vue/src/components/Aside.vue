<template>
  <el-menu
      style="width: 200px;min-height: calc(100vh - 50px);background:rgb(27, 38, 65)"
      :uniqueOpened="true"
      default-active="/newDetail"
      class="el-menu-vertical-demo"
      router
      background-color="inherit"
      text-color="#fff"
      active-text-color="#ffd04b">
    <el-submenu index="/user" v-if="user.role===1">
      <template #title>用户管理</template>
      <el-menu-item index="/buyer">用户审核</el-menu-item>
      <el-menu-item index="/user">商家审核</el-menu-item>
    </el-submenu>
    <el-submenu index="/goodsAudit" v-if="user.role===1">
      <template #title>商品管理</template>
      <el-menu-item index="/goodsAudit">待审核</el-menu-item>
    </el-submenu>
    <el-menu-item index="/kind" v-if="user.role===1">类别管理</el-menu-item>
    <el-menu-item index="/news" v-if="user.role===1">公告管理</el-menu-item>
    <el-menu-item index="/sellLevel" v-if="user.role===1">等级管理</el-menu-item>
    <el-menu-item index="/swiper" v-if="user.role===1">轮播图管理</el-menu-item>
    <el-menu-item index="/newsDetail" v-if="user.role===2">首页</el-menu-item>
    <el-menu-item :index="'/goods'" v-if="user.role===2">商品管理</el-menu-item>
    <el-menu-item :index="'/sellOrder'" v-if="user.role===2">订单管理</el-menu-item>
    <el-menu-item index="/recharge" v-if="user.role===2">商家中心</el-menu-item>
  </el-menu>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Aside",
  data(){
    return{
      user:{},
    }
  },
  created() {
    let userStr =sessionStorage.getItem("user") || '{}';
    this.user = JSON.parse(userStr);
//  请求后端服务器，确认当前登录用户的合法性
    request.get("/api/user/"+this.user.id).then(res=>{
      if (res.code==='0'){
        this.user = res.data;
      }
    });
  },
  methods:{

  }
}
</script>

<style scoped>

</style>