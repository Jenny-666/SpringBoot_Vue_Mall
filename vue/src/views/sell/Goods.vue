<template>
    <div style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="add">新增</el-button>
        </div>

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

            <el-table-column fixed="right" label="操作" width="200">
                <template #default="scope">
                    <div style="display: flex; align-content: space-between">
                        <el-button @click="handleEdit(scope.row)" type="primary">编辑</el-button>
                        <el-popconfirm title="确定下架商品吗？" @confirm="handleDelete(scope.row.id)">
                            <template #reference>
                                <el-button type="danger">下架</el-button>
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

            <el-dialog title="添加商品" v-model="dialogVisible" width="30%">
                <el-form :model="form" label-width="120px" :rules="rules" ref="form">
                    <el-form-item label="商品名称" prop="goodsName">
                        <el-input v-model="form.goodsName"></el-input>
                    </el-form-item>
                    <el-form-item label="商品库存" prop="num">
                        <el-input v-model="form.num"></el-input>
                    </el-form-item>
                    <el-form-item label="商品价格" prop="price">
                        <el-input v-model="form.price"></el-input>
                    </el-form-item>
                    <el-form-item label="商品详情" prop="instruction">
                        <el-input v-model="form.instruction"></el-input>
                    </el-form-item>
                    <el-form-item label="商品类别">
                        <el-select v-model="form.kindName" placeholder="请选择商品类别">
                            <el-option v-for="item in kindList" :key="item.id" :label="item.name" :value="item.name" />
                        </el-select>
                    </el-form-item>

                    <el-form-item label="商品图片">
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
    name: 'Goods',
    components: {

    },
    created() {
        this.getKindList();
        let userStr = sessionStorage.getItem("user") || '{}';
        this.user = JSON.parse(userStr);
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
            kindList: [],
            rules: {
                goodsName: [
                    { required: true, message: '请输入商品名称', trigger: 'blur' },
                ],
                num: [
                    { required: true, message: '请输入商品库存', trigger: 'blur' },
                ],
                price: [
                    { required: true, message: '请输入商品价格', trigger: 'blur' },
                ],
            }
        }
    },

    methods: {
        load() {
            request.get("/api/goods", {
                params: {
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize,
                    search: this.search,
                    userId: this.user.id
                }
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
                this.form.userId = this.user.id
                this.form.userName = this.user.username
                request.put("/api/goods", this.form).then(res => {
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
                        })
                    }
                    this.load();
                    this.dialogVisible = false;
                });
            } else {//新增
                this.form.userId = this.user.id
                this.form.userName = this.user.username
                request.post("/api/goods", this.form).then(res => {
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
            this.form.status = 0;
            this.$nextTick(() => {
                if (this.$refs['upload']) {
                    this.$refs['upload'].clearFiles();
                }
            });
        },
        handleDelete(id) {
            request.delete("/api/goods/" + id).then(res => {
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
        getKindList() {
            request.get("/api/kind").then(res => {
                this.kindList = res.data.records
            })
        }
    }
}
</script>
  