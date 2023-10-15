<template>
    <div style="padding: 10px 0">
        <el-card style="width: 1100px; margin-left: 200px;margin-top: 5%;">
            <div style="display: flex">
                <div style="width: 350px">
                    <el-image :src="goods.photo" :preview-src-list="[goods.photo]"
                        style="width: 80%; position: center; margin-top: 30px"></el-image>
                </div>
                <div style="flex: 1; padding-left: 50px">
                    <div class="goodsName">{{ goods.goodsName }}</div>
                    <div class="refer">{{ goods.instruction }}</div>
                    <div class="price">￥{{ goods.price }}</div>
                    <div class="refer" style="margin-top: 10px;">库存{{ goods.num }}台</div>
                    <div class="goodsName">
                        <el-input-number size="big" v-model="form.num" :min="1" :max="100" label="数量"></el-input-number>
                    </div>
                    <div class="goodsName">
                        <el-button class="addCar" size="big" @click="buy"><i class="el-icon-bank-card" /> 直接购买</el-button>
                        <el-button class="goHome" size="big" @click="home"><i class="el-icon-bank-card" /> 返回首页</el-button>
                    </div>
                    <a size="big" @click="chat"
                        style="float: left; color: #DF3033; cursor: pointer; padding-left: 10px">联系商家</a>
                </div>
            </div>
        </el-card>
    </div>
</template>
   
<script>
import request from "../../utils/res"

export default {
    name: "Detail",
    components: {
    },
    data() {
        return {
            id: this.$route.query.id,
            goods: {},
            form: { num: 1 },
            user: {},
            buyer: {},
        }
    },
    created() {
        let userStr = sessionStorage.getItem("buyer") || '{}';
        this.buyer = JSON.parse(userStr);
        this.load()
    },
    methods: {
        load() {
            request.get("/api/goods/" + this.id).then(res => {
                this.goods = res.data
            })
        },
        home() {
            this.$router.push("/home")
        },
        buy() {
            this.form.goodsId = this.goods.id
            this.form.buyerId = this.buyer.id
            this.form.userId = this.goods.userId
            this.form.goodsName = this.goods.goodsName
            this.$confirm("确认购买？").then(() => {
                request.post("/api/order/checkOut", this.form).then(res => {
                    console.log(res);
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "下单成功"
                        })                       
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        });
                    }
                    this.load()
                })

            }).catch(() => { })
        }
    }
}
</script>
   
<style scoped>
.addCar {
    padding: 10px;
    width: 140px;
    height: 45px;
    background-color: #DF3033;
    color: white;
    font-weight: bold;
    font-size: 18px
}

.goHome {
    padding: 10px;
    width: 140px;
    height: 45px;
    background-color: #428cf4;
    color: white;
    font-weight: bold;
    font-size: 18px
}

.goodsName {
    padding: 10px;
    font-size: 22px;
    font-weight: bold;
}

.refer {
    padding: 10px;
    font-size: 16px;
    font-weight: bold
}

.price {
    height: 60px;
    padding: 15px 10px 10px 20px;
    background-color: #FCE5E5;
    color: orangered;
    font-weight: bold;
    margin-left: 10px;
    margin-top: 10px;
    font-size: 24px;
}
</style>