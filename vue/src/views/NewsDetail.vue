<template>
  <div style="padding-left:10px; padding-right: 10px; width: calc(100vw - 200px)">
    <div style="margin: 20px;color: rgb(79, 125, 242);text-align: center;font-size:2ex;">
      <h1>欢迎进入有闲后台！</h1>
    </div>
    <!-- 轮播图 -->
    <div class="block">
      <el-carousel height="460px">
        <el-carousel-item v-for="item in swiperList " :key="item.id">
          <img style="height:460px;width: 100%;" :src="item.photo" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <!-- 轮播图END -->
    <br>
    <div style="margin: 20px;color: rgb(79, 125, 242);text-align: center;font-size:1ex;">
      <h1>有闲公告</h1>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable>
      </el-table-column>
      <el-table-column prop="title" label="标题">
      </el-table-column>
      <el-table-column prop="time" label="发布时间">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <div style="display: flex; align-content: space-between">
            <el-button @click="details(scope.row)" type="primary">详情</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="currentPage4" :page-sizes="[5, 10, 20]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
      <el-dialog title="详情" v-model="vis" width="50%">
        <el-card>
          <div v-html="detail.content" style="min-height: 150px;">
          </div>
        </el-card>
      </el-dialog>
    </div>
  </div>
</template>
  
<script>

import E from 'wangeditor';
import request from "@/utils/request";
let editor;
export default {
  name: 'News',
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
      detail: {},
      vis: false,
      carousel: "",
      swiperList: []
    }
  },
  created() {
    this.getSwiperList();
    this.load();
  },
  mounted() {

  },
  methods: {
    load() {
      request.get("/api/news", {
        params: {
          pageNum: this.currentPage4,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    creatDom() {
      editor = new E('#div1');//富文本编辑器创建，获取节点
      // 配置 server 接口地址
      editor.config.uploadImgServer = 'http://localhost:9090/files/editor/upload';
      editor.config.uploadFileName = 'file';//设置文件上传的名字
      editor.create();//创建。
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.currentPage4 = pageNum;
      this.load();
    },
    filesUploadSuccess(res) {
      console.log(res);
      this.form.cover = res.data;
      this.load();
    },
    details(row) {
      this.detail = row;
      this.vis = true;
    },
    getSwiperList() {
      request.get("/api/swiper").then(res => {
        this.swiperList = res.data.records
      })
    },

  }
}
</script>
  