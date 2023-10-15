<template>
    <div class="base">
        <div style="background-color: #545c64; width: 1510px">
            <el-menu background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" class="el-menu-demo"
                mode="horizontal">
                <el-menu-item index="/home" @click="$router.push('/home')">有闲商城首页</el-menu-item>
                <el-menu-item index="/order" @click="$router.push('/order')">我的订单<i class="el-icon-truck" /></el-menu-item>
                <div style="float: right; display: flex">
                    <el-menu-item index="/buyerPerson" @click="$router.push('/buyerPerson')">个人信息</el-menu-item>
                    <el-menu-item index="/buyerLogin" @click="$router.push('/buyerLogin')">退出</el-menu-item>
                </div>
            </el-menu>
        </div>
        <el-card style="height: 80px; background-color: white">
            <el-input style="width: 600px; margin-left: 400px" placeholder="请输入你要查询的商品" clearable v-model="search"
                size="big"></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="load" size="big"><i
                    class="el-icon-search" />搜索</el-button>
        </el-card>

        <div style="width: 1500px; height: 410px; display: flex">
            <div
                style="width: 300px; text-align: right; padding-right: 10px; background-color: whitesmoke; margin: 10px 0 0">
                <ul style="margin: 10px 0; font-weight: inherit; font-size: 18px; color: #545c64" v-for="item in kindList"
                    :key="item.id" :label="item.name" :value="item.name">
                    <li style="margin-top: 10px; cursor: pointer">{{ item.name }}</li>
                </ul>
            </div>
            <!--    轮播图-->
            <div style="flex: 1; margin: 10px 0 0 ">
                <span class="demonstration"></span>
                <el-carousel height="400px">
                    <el-carousel-item v-for="item in swiperList" :key="item.id">
                        <img :src="item.photo">
                    </el-carousel-item>
                </el-carousel>
            </div>
        </div>


        <div style="width: 100px; height: 50px; text-align: center; margin:25px 0 0 710px">
            <a style="font-size: 18px; font-weight: bold; padding-top: 100px">综合商品</a>
        </div>
        <div style="margin: 10px 60px; background-color: whitesmoke; height: 640px; width: 1400px; ">
            <el-row :gutter="12">
                <el-col :span="4" v-for="item in tableData" :key="item.id">
                    <div class="phone">
                        <img style="width: 140px; height: 140px; margin-top: 5px; margin-left: 2px; cursor: pointer"
                            :src="item.photo" @click="$router.push('/detail?id=' + item.id)">
                        <div class="refer" @click="$router.push('/detail?id=' + item.id)"><b>{{ item.instruction }}</b>
                        </div>
                        <div class="goodsName" @click="$router.push('/detail?id=' + item.id)">{{ item.goodsName }}</div>
                        <div class="price" @click="$router.push('/detail?id=' + item.id)">￥{{ item.price }}</div>
                    </div>
                </el-col>
            </el-row>
        </div>

        <div style="padding: 10px 0 0 50px">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage4" :page-sizes="[6, 12]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>

        <div style="width: 100%; height: 250px; margin-top: 80px">
            <div style="height: 250px;width: 1510px; background-color: #545c64">
                <h3 style="margin-left: 705px; font-size: 28px; color: white">有闲商城</h3>
                <div style="margin-top: 15px">
                    <a style="margin-left: 280px; font-size: 16px; color: white">商城 | 游戏 | 政企服务 | 集团隐私政策 | 公司儿童信息保护规则 |
                        商城隐私政策 | 商城用户协议 | 问题反馈 | Select Location</a>
                </div>
                <div style="margin-top: 10px">
                    <a style="margin-left: 315px;font-size: 16px; color: white"> 互联网ICP备案：沪ICP备13002172号-3 沪-非经营性-2023-0143
                        营业性演出许可证 沪市文演（经）00-2253 | </a>
                </div>
            </div>

        </div>
    </div>
</template>
   
<script>
import res from '../../utils/res'
export default {
    name: "Home",
    components: {},
    data() {
        return {
            tableData: [],
            item: {},
            total: 0,
            pageNum: 1,
            pageSize: 12,
            search: "",
            name: "",
            user: {},
            buyer: {},
            kindList: [],
            swiperList: [],
        }
    },
    created() {
        this.load();
        this.getKindList();
        this.getSwiperList();
        let userStr = sessionStorage.getItem("buyer") || '{}';
        this.buyer = JSON.parse(userStr);
    },
    methods: {
        load() {
            res.get("/api/goods/audit", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    search: this.search,
                    status: 1
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        home() {
            this.$router.push("/home")
        },
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        getKindList() {
            res.get("/api/kind").then(res => {
                this.kindList = res.data.records
            })
        },
        getSwiperList() {
            res.get("/api/swiper").then(res => {
                this.swiperList = res.data.records
            })
        }
    }
}
</script>
   
<style scoped>
li:hover {
    color: orangered;
}

.goodsName {
    font-size: 14px;
    text-align: center;
    cursor: pointer
}

.price {
    font-size: 16px;
    font-weight: bold;
    color: orangered;
    cursor: pointer;
}

.refer {
    padding: 2px;
    cursor: pointer;
    /*margin-top: 5px;*/
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}

.base {
    margin: 0px;
    padding: 0px;
}

img {
    width: 100%;
    height: 100%;
}

.phone {
    background-color: white;
    padding-bottom: 10px;
    font-size: 16px;
    text-align: center;
    width: 160px;
    height: 150px;
    transition: all 0.8s;
    margin: 60px 16px;
}

.box~.phone {
    margin-left: 30px;
}

.box0 img {
    width: 170px;
    height: 170px;
}

.phone:hover {
    transform: scale(1.08);
}
</style>