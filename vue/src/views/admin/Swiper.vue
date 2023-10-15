<template>
    <div style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="add">新增</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
            <el-button style="margin-left: 5px; color: white" @click="load" type="primary">查询</el-button>
        </div>

        <el-table :data="tableData" border stripe style="width:100%">
            <el-table-column prop="id" label="ID" sortable>
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

            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <div style="display: flex; align-content: space-between">
                        <el-button @click="handleEdit(scope.row)" type="primary">更换</el-button>
                        <el-popconfirm title="确定删除图片吗？" @confirm="handleDelete(scope.row.id)">
                            <template #reference>
                                <el-button type="danger">删除</el-button>
                            </template>
                        </el-popconfirm>
                    </div>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage4" :page-sizes="[5, 10, 20]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>

            <el-dialog title="添加轮播图" v-model="dialogVisible" width="30%">
                <el-form :model="form" label-width="120px">

                    <el-form-item label="轮播图">
                        <el-upload ref="upload" action="http://localhost:9090/files/upload"
                            :on-success="filesUploadSuccess">
                            <el-button type="primary">点击上传</el-button>
                        </el-upload>
                    </el-form-item>

                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="save">确 定</el-button>
                    </span>
                </template>
            </el-dialog>

        </div>
    </div>
</template>
  
<script>

import request from "@/utils/request";
export default {
    name: 'Swiper',
    components: {

    },
    created() {
        let userStr = sessionStorage.getItem("user") || '{}';
        this.user = JSON.parse(userStr);
        //   请求后端服务器，确认当前登录用户的合法性
        request.get("/api/user/" + this.user.id).then(res => {
            if (res.code === '0') {
                this.user = res.data;
            }
        });
        this.load();
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
            user: {},
        }
    },

    methods: {
        load() {
            request.get("/api/swiper", {
                params: {
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize,
                    search: this.search,
                },

            }).then(res => {
                console.log(res);
                this.tableData = res.data.records
                this.total = res.data.total;
            })
        },
        add() {
            this.dialogVisible = true;
            this.form = {};
            this.$nextTick(() => {
                if (this.$refs['upload']) {
                    this.$refs['upload'].clearFiles();
                }
            });
        },
        save() {
            if (this.form.id) {//更新
                request.put("/api/swiper", this.form).then(res => {
                    console.log(res);
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "更换成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                    this.load();
                    this.dialogVisible = false;
                });
            } else {//新增
                request.post("/api/swiper", this.form).then(res => {
                    console.log(res);
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "新增成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                    this.load();
                    this.dialogVisible = false;
                });
            }
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.currentPage4 = pageNum;
            this.load();
        },
        handleEdit(row) {
            this.form = JSON.parse((JSON.stringify(row)));
            this.dialogVisible = true;
            this.$nextTick(() => {
                if (this.$refs['upload']) {
                    this.$refs['upload'].clearFiles();//清楚历史图片
                }
            });
        },
        handleDelete(id) {
            request.delete("/api/swiper/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                        type: "success",
                        message: "删除成功"
                    })
                } else {
                    this.$message({
                        type: "error",
                        message: res.msg
                    })
                }
                this.load()
            });
        },
        filesUploadSuccess(res) {
            console.log(res);
            this.form.photo = res.data;
            this.load();
        },
    }
}
</script>
  