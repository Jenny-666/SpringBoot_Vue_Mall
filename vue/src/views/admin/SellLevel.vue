<template>
    <div class="sellLevel" style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">
        <h3>修改商家等级</h3>

        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
            <el-button style="margin-left: 5px;color: white" @click="load" type="primary">查询</el-button>
        </div>

        <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID" sortable>
            </el-table-column>
            <el-table-column prop="username" label="商家名称">
            </el-table-column>
            <el-table-column prop="level" label="商家等级">
            </el-table-column>

            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <div style="display: flex; align-content: space-between">
                        <el-button @click="handleChangeLevel(scope.row)" type="primary">修改商家等级</el-button>

                    </div>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage4" :page-sizes="[5, 10, 20]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
            
            <el-dialog title="商家等级修改" v-model="dialogVisible" width="30%">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="等级">
                        <el-select v-model="form.level" placeholder="请选择商品类别">
                            <el-option label="1" value="1"></el-option>
                            <el-option label="2" value="2"></el-option>
                            <el-option label="3" value="3"></el-option>
                            <el-option label="4" value="4"></el-option>
                            <el-option label="5" value="5"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="changeLevel">确 定</el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>
  
<script>
import request from "@/utils/request";

export default {
    name: 'SellLevel',
    components: {

    },
    data() {
        return {
            form: {},
            search: '',
            currentPage4: 1,
            pageSize: 10,
            total: 0,
            dialogVisible: false,
            tableData: [],
            user: {}
        }
    },
    created() {
        this.load();
        let userStr = sessionStorage.getItem("user") || '{}';
        this.user = JSON.parse(userStr);
    },
    methods: {
        load() {
            request.get("/api/user/selectByStatus", {
                params: {
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize,
                    search: this.search,
                    status: 1,
                    role: 2
                }
            }).then(res => {
                console.log(res);
                this.tableData = res.data.records;
                this.total = res.data.total;
            })
        },
        changeLevel() {
            this.$confirm("确认修改？").then(() => {
                request.put("/api/user", this.form).then(res => {
                    console.log(res);
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "更新成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        });
                    }
                    this.load();
                    this.dialogVisible = false;
                });
            })
        },
        handleChangeLevel(row) {
            this.form = JSON.parse((JSON.stringify(row)));
            this.dialogVisible = true;
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.currentPage4 = pageNum;
            this.load();
        },

    }
}
</script>
  
<style>
.sellLevel {
    width: 750px;
    margin: 0 auto;
    margin-top: 30px;
}

.sellLevel h3 {
    margin-bottom: 10px;
    color: rgb(79, 125, 242);
    text-align: center;
}
</style>