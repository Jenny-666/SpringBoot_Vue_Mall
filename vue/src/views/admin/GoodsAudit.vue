<template>
    <div style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">

        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
            <el-button style="margin-left: 5px; color: white" @click="load" type="primary">查询</el-button>
        </div>
        <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column prop="id" label="商品ID" sortable>
            </el-table-column>
            <el-table-column prop="userName" label="商家昵称">
            </el-table-column>
            <el-table-column prop="goodsName" label="商品名称">
            </el-table-column>
            <el-table-column prop="kindName" label="商品类别">
            </el-table-column>
            <el-table-column prop="instruction" label="商品详情">
            </el-table-column>
            <el-table-column prop="num" label="商品库存">
            </el-table-column>
            <el-table-column prop="price" label="商品价格">
            </el-table-column>
            <el-table-column label="商品图片">
                <template #default="scope">
                    <div class="demo-image__preview">
                        <el-image style="width: 100px; height: 100px" :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]">
                        </el-image>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="商品状态">
                <template #default="scope">
                    <span v-if="scope.row.status === 1">审核通过</span>
                    <span v-if="scope.row.status === 2">审核失败</span>
                    <span v-if="scope.row.status === 0">待审核</span>
                </template>
            </el-table-column>

            <el-table-column label="审核" width="240">
                <template v-slot="scope">
                    <el-button type="success" @click="handlePass(scope.row, 1)"
                        :disabled="scope.row.status !== 0">审核通过</el-button>
                    <el-button type="danger" @click="handleFail(scope.row, 2)"
                        :disabled="scope.row.status !== 0">审核不通过</el-button>
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
    created() {
        this.load();
        let userStr = sessionStorage.getItem("user") || '{}';
        this.user = JSON.parse(userStr);
        //   请求后端服务器，确认当前登录用户的合法性
        request.get("/api/user/" + this.user.id).then(res => {
            if (res.code === '0') {
                this.user = res.data;
            }
        });
    },
    data() {
        return {
            kinds: [],
            form: {},
            search: '',
            currentPage4: 1,
            pageSize: 10,
            total: 0,
            dialogVisible: false,
            tableData: [],
            user: {},
        }
    },

    methods: {
        load() {
            request.get("/api/goods/audit", {
                params: {
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize,
                    search: this.search,
                    status: 0
                }
            }).then(res => {
                console.log(res);
                this.tableData = res.data.records;
                this.total = res.data.total;
            })
        },

        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.currentPage4 = pageNum;
            this.load();
        },

        handlePass(row, status) {
            row.status = 1;
            this.form = JSON.parse((JSON.stringify(row)));
            request.put("/api/goods", this.form).then(res => {
                console.log(res);
                if (res.code === '0') {
                    this.$message({
                        type: "success",
                        message: "审核通过"
                    })
                } else {
                    this.$message({
                        type: "error",
                        message: res.msg
                    })
                }
                this.load();
            });
        },
        handleFail(row, status) {
            row.status = 2;
            this.form = JSON.parse((JSON.stringify(row)));
            request.put("/api/goods", this.form).then(res => {
                console.log(res);
                if (res.code === '0') {
                    this.$message({
                        type: "success",
                        message: "审核未通过"
                    })
                } else {
                    this.$message({
                        type: "error",
                        message: res.msg
                    })
                }
                this.load();
            });
        }
    }
}
</script>
  