<template>
  <div style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button style="margin-left: 5px; color: white" @click="load" type="primary">查询</el-button>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable>
      </el-table-column>
      <el-table-column prop="username" label="用户名">
      </el-table-column>
      <el-table-column prop="nickName" label="昵称">
      </el-table-column>
      <el-table-column prop="age" label="年龄">
      </el-table-column>
      <el-table-column prop="sex" label="性别">
      </el-table-column>
      <el-table-column prop="address" label="地址">
      </el-table-column>
      <el-table-column prop="phone" label="电话">
      </el-table-column>

      <el-table-column label="角色">
        <template #default="scope">
          <span v-if="scope.row.role === 1">管理员</span>
          <span v-if="scope.row.role === 2">商家</span>
        </template>
      </el-table-column>

      <el-table-column label="状态">
        <template #default="scope">
          <span v-if="scope.row.status === 1">审核通过</span>
          <span v-if="scope.row.status === 2">审核失败</span>
          <span v-if="scope.row.status === 0">待审核</span>
        </template>
      </el-table-column>

      <el-table-column label="审核" width="240" fixed="right">
        <template v-slot="scope">
          <el-button type="success" @click="handleEdit(scope.row, 1)" :disabled="scope.row.status !== 0">审核通过</el-button>
          <el-button type="danger" @click="handleDelete(scope.row, 2)"
            :disabled="scope.row.status !== 0">审核不通过</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Home',
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
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/api/user", {
        params: {
          pageNum: this.currentPage4,
          pageSize: this.pageSize,
          search: this.search,
          role: 2
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
    handleEdit(row, status) {
      this.$confirm("确认审核通过？").then(() => {
        row.status = 1;
        this.form = JSON.parse((JSON.stringify(row)));
        this.dialogVisible = true;

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
            })
          }
          this.load();
        });
      }).catch(() => { });
    },
    handleDelete(row, status) {
      this.$confirm("确认审核失败？").then(() => {
        row.status = 2;
        this.form = JSON.parse((JSON.stringify(row)));
        this.dialogVisible = true;
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
            })
          }
          this.load();
        });

      }).catch(() => { });
    }
  }
}
</script>
