<template>
    <div style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
            <el-button style="margin-left: 5px; color: white" @click="load" type="primary">查询</el-button>
        </div>
        <el-table :data="orders" border stripe style="width: 100%">
            <el-table-column prop="orderId" label="订单ID" sortable></el-table-column>
            <!-- <el-table-column prop="goodsId" label="商品ID"></el-table-column> -->
            <el-table-column prop="goodsName" label="商品名称"></el-table-column>
            <el-table-column prop="num" label="数量"></el-table-column>
            <el-table-column prop="totalPrice" label="总价"></el-table-column>
            <el-table-column prop="creatTime" label="订单开始时间"></el-table-column>
            <el-table-column prop="endTime" label="订单结束时间" width="200"></el-table-column>
            <el-table-column prop="status" label="订单状态">
                <template #default="scope">
                    <span v-if="scope.row.status === 2">交易完成</span>
                    <span v-if="scope.row.status === 1">已发货</span>
                    <span v-if="scope.row.status === 0">待发货</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
                <template v-slot="scope">
                    <el-button type="success" @click="handleSend(scope.row)"
                        :disabled="scope.row.status !== 0">发货</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage4" :page-sizes="[5, 10, 20]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
    </div>
</template>
<script>

import request from "@/utils/request";
export default {
    name: 'Goods',
    components: {
    },
    data() {
        return {
            currentPage4: 1,
            pageSize: 10,
            buyer: {},
            orders: [],
            total: 0,
            search: ""
        }
    },
    created() {
        let userStr = sessionStorage.getItem("user") || '{}';
        this.user = JSON.parse(userStr);
        request.get("/api/user/" + this.user.id).then(res => {
            if (res.code === '0') {
                this.user = res.data;
            }
        });
        this.load()
    },
    methods: {
        load() {
            request.get("/api/order/user/" + this.user.id, {
                params: {
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize,
                    search: this.search,
                    userId: this.user.id
                }
            }).then(res => {
                if (res.code === '0') {
                    console.log(this.user.id)
                    console.log(res);
                    this.orders = res.data.records;
                    this.total = res.data.total;
                }
            });
        },
        handleSend(row) {
            this.$confirm("确认发货?").then(() => {
                row.status = 1
                request.put("/api/order", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "发货成功！" })
                    } else {
                        this.$message(response.data.msg)
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.currentPage4 = pageNum;
            this.load();
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