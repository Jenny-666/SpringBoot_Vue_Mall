<template>
    <div style="padding: 10px 0">
        <el-button @click="home" style=" margin-left: 200px;">返回首页</el-button>
        <el-card style="width: 1100px; margin-left: 200px;margin-top: 2%;">
            <el-table :data="orders" stripe style="width: 100%">
                <el-table-column width="30"></el-table-column>
                <el-table-column prop="orderId" label="订单ID" sortable></el-table-column>
                <!-- <el-table-column prop="goodsId" label="商品ID"></el-table-column> -->
                <el-table-column prop="goodsName" label="商品名称"></el-table-column>
                <el-table-column prop="num" label="数量"></el-table-column>
                <el-table-column prop="totalPrice" label="总价"></el-table-column>
                <el-table-column prop="creatTime" label="订单开始时间"></el-table-column>
                <el-table-column prop="endTime" label="订单结束时间" width="200"></el-table-column>
                <el-table-column prop="status" label="订单状态">
                    <template #default="scope">
                        <span v-if="scope.row.status === 2">交易成功</span>
                        <span v-if="scope.row.status === 1">待收货</span>
                        <span v-if="scope.row.status === 0">待发货</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200">
                    <template v-slot="scope">
                        <el-button type="success" @click="handleConfirm(scope.row)"
                            :disabled="scope.row.status !== 1 && scope.row.status !== 0">确认收货</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>
<script>

import request from "@/utils/res";
export default {
    name: 'Goods',
    components: {
    },
    data() {
        return {
            currentPage4: 1,
            pageSize: 100,
            buyer: {},
            orders: [],
            total: 0,
        }
    },
    created() {
        let userStr = sessionStorage.getItem("buyer") || '{}';
        this.buyer = JSON.parse(userStr);
        request.get("/api/buyer/" + this.buyer.id).then(res => {
            if (res.code === '0') {
                this.buyer = res.data;
            }
        });
        this.load()
    },
    methods: {
        load() {
            request.get("/api/order/buyer/" + this.buyer.id, {
                params: {
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize,
                    buyerId: this.buyer.id
                }
            }).then(res => {
                if (res.code === '0') {
                    console.log(this.buyer.id)
                    console.log(res);
                    this.orders = res.data.records;
                    this.total = res.data.total;
                }
            });
        },
        home() {
            this.$router.push("/home")
        },
        handleConfirm(row) {
            this.$confirm("确认订单已完成？").then(() => {
                let old = row.status;
                row.status = 2
                let ordEndTime = row.endTime
                row.endTime = this.formatDate(new Date(), 'YY-MM-DD hh:mm:ss')
                request.put("/api/order", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "确认成功，该订单已完成！" })
                    } else {
                        this.$message(response.data.msg)
                        row.status = old
                        row.endTime = ordEndTime
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },
        formatDate(time, format = 'YY-MM-DD hh:mm:ss') {
            var date = new Date(time);
            var year = date.getFullYear(),
                month = date.getMonth() + 1,
                day = date.getDate(),
                hour = date.getHours(),
                min = date.getMinutes(),
                sec = date.getSeconds();
            var preArr = Array.apply(null, Array(10)).map(function (elem, index) {
                return '0' + index;
            });       
            var newTime = format.replace(/YY/g, year)
                .replace(/MM/g, preArr[month] || month)
                .replace(/DD/g, preArr[day] || day)
                .replace(/hh/g, preArr[hour] || hour)
                .replace(/mm/g, preArr[min] || min)
                .replace(/ss/g, preArr[sec] || sec);
            return newTime;
        }
    }

}
</script>

<style scoped>
.order {
    width: 1030px;
    margin: 0 auto;
    margin-top: 50px;
}
</style>