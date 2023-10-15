<template>
  <div class="sellInfo">
    <el-card style="width: 40%;margin: 10px">
      <div class="integral">
        <h3>我的等级</h3>
        <h5>等级：</h5><span>{{ user.level }}</span>
      </div>
      <br>
      <div class="wallet">
        <h3>我的钱包</h3>
        <h5>余额：</h5><span>{{ user.wallet }}</span>
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
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Recharge",
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
      level: "",
      count: 0,
      user: {},
      rechargeAmount: "",
      rules: {
        rechargeAmount: [{ validator: checkRechargeAmount, trigger: 'blur' }]
      }
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}";
    this.form = JSON.parse(str);
    this.load()
  },
  methods: {
    load() {
      request.get("/api/user/" + this.form.id).then(res => {
        if (res.code === '0') {
          console.log(res)
          this.user = res.data;
        }
      });
    },
    recharge() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.wallet = (Number)(this.form.wallet) + (Number)(this.rechargeAmount);
          request.put("/api/user", this.form).then(res => {
            console.log(res);
            if (res.code === '0') {
              this.rechargeAmount = "";
              this.$message({
                type: "success",
                message: "充值成功"
              })
              sessionStorage.setItem("user", JSON.stringify(this.form));
              this.load()
            } else {
              this.$message({
                type: "error",
                message: res.msg
              });
            }
          });
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
  width: 1200px;
  margin: 0 auto;
  text-align: left;
  height: 75vh;
  line-height: 30px;
}

.sellInfo .integral {
  margin-top: 15px;
}
</style>