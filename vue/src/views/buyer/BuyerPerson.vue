<template>
  <div>
    <el-card style="width: 40%;margin: 10px;">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.buyername" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: right">
        <el-button type="primary" @click="$router.push('/home')">返回首页</el-button>
        <el-button type="primary" @click="update">保存</el-button>
      </div>
    </el-card>
    <div class="sellInfo">
      <el-card style="width: 40%;margin: 10px">
        <br>
        <div class="wallet">
          <h3>我的钱包</h3>
          <h5>余额：</h5><span>{{ buyer.wallet }}</span>
        </div>
        <el-form :model="form" ref="form" :rules="rules" :inline="true">
          <el-form-item label="充值" prop="rechargeAmount">
            <el-input type="number" v-model="rechargeAmount" placeholder="输入充值金额"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="recharge">充值</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
import request from "../../utils/res";

export default {
  name: "BuyerPerson",
  data() {
    var checkRechargeAmount = (rule, value, callback) => {
      if (this.rechargeAmount == "") {
        return callback(new Error('充值金额不能为空'));
      } else {
        callback();
      }
    };
    return {
      form: {},
      wallet: "",
      buyer: {},
      rechargeAmount: "",
      rules: {
        rechargeAmount: [{ validator: checkRechargeAmount, trigger: 'blur' }]
      }
    }
  },
  created() {
    let str = sessionStorage.getItem("buyer") || "{}";
    this.form = JSON.parse(str);
    this.load()
  },
  methods: {
    load() {
      request.get("/api/buyer/" + this.form.id).then(res => {
        if (res.code === '0') {
          this.buyer = res.data;
        }
      })
    },
    update() {
      request.put("/api/buyer", this.form).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("buyer", JSON.stringify(this.form));
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    recharge() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.wallet = (Number)(this.form.wallet) + (Number)(this.rechargeAmount);
          request.put("/api/buyer", this.form).then(res => {
            console.log(res);
            if (res.code === '0') {
              this.rechargeAmount = "";
              this.$message({
                type: "success",
                message: "充值成功"
              })
              sessionStorage.setItem("buyer", JSON.stringify(this.form));
            } else {
              this.$message({
                type: "error",
                message: res.msg
              });
            }
            this.load()
          })

        } else {
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>
.sellInfo {
  text-align: left;
  height: 75vh;
  line-height: 30px;
}

.sellInfo .integral {
  margin-top: 15px;
}
</style>