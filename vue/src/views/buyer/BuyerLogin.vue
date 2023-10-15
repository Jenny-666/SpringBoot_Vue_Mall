<template>
  <div style="width: 100%;height: 100vh; background: white;display: flex;justify-content: center;align-items: center">
    <div
      style="width: 300px; height: 380px;box-shadow: 0 5px 15px rgba(0, 0, 0, .8); display: flex;justify-content: center;align-items: center;">
      <el-form :model="form" size="normal" style="height: 100% ;width: 220px;" ref="form" :rules="rules">
        <div style="width: 100%;height: 40px;margin-top:20px;line-height: 30px;text-align: center">
          <span style="color: rgb(79, 125, 242);font-weight: 900;font-size: 30px;">欢迎登录 <br> 有闲商城</span>
        </div>
        <el-form-item style="margin-top: 50px" prop="buyername">
          <el-input v-model="form.buyername" prefix-icon="el-icon-user-solid" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            style="color: white; border-radius: 30px;width: 100%;height: 50px;background: linear-gradient(90deg,#3cadeb,#9c88ff);"
            @click="login">登录</el-button>
        </el-form-item>
        <el-form-item style="text-align: center; color: rgb(79, 125, 242);">
          <a @click="toRegister" style="font-size: 16px;font-weight: 500;opacity: 0.8;text-decoration: underline">注册</a>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../utils/res";

export default {
  name: "BuyerLogin",
  data() {
    return {
      form: {},
      rules: {
        buyername: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    sessionStorage.removeItem("buyer");
  },
  methods: {
    toRegister() {
      this.$router.push("/buyerRegister")
    },
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/api/buyer/login", this.form).then(res => {
            console.log(res);
            if (res.code === '0') {

              console.log("res.data:" + res.data)
              sessionStorage.setItem("buyer", JSON.stringify(res.data));//缓存用户信息

              this.$message({
                type: "success",
                message: "登录成功"
              })
              // 如果存在查询参数
              if (this.$route.query.login) {
                let redirect = this.$route.query.login;
                //跳转至进入登录页前的路由
                this.$router.replace(redirect);   //跳转到指定url路径，但是history栈中不会有记录，点击返回会跳转到上上个页面
              } else {
                // 否则跳转至首页
                this.$router.replace('/home');//登录成功后的页面跳转
              }
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });

    }
  }
}
</script>

<style scoped></style>
