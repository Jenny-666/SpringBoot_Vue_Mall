import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/newsDetail",
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import("@/views/admin/User"),
      },
      {
        path: 'buyer',
        name: 'Buyer',
        component: () => import("@/views/admin/Buyer"),
      },
      {
        path: '/goods',
        name: 'Goods',
        component: ()=>import("@/views/sell/Goods")
      },
      {
        path: '/sellOrder',
        name: 'SellOrder',
        component: ()=>import("@/views/sell/SellOrder")
      },
      {
        path: '/goodsAudit',
        name: 'GoodsAudit',
        component: ()=>import("@/views/admin/GoodsAudit")
      },
      {
        path: '/kind',
        name: 'Kind',
        component: () => import("@/views/admin/Kind"),
      },
      {
        path: '/recharge',
        name: 'Recharge',
        component: () => import("@/views/sell/Recharge"),
      },
      {
        path: '/person',
        name: 'Person',
        component: ()=>import("@/views/Person")
      },
      {
        path: '/news',
        name: 'News',
        component: ()=>import("@/views/admin/News")
      },
      {
        path: '/sellLevel',
        name: 'SellLevel',
        component: ()=>import("@/views/admin/SellLevel")
      },
      {
        path: '/swiper',
        name: 'Swiper',
        component: ()=>import("@/views/admin/Swiper")
      },
      {
        path: '/newsDetail',
        name: 'NewsDetail',
        component: ()=>import("@/views/NewsDetail")
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: ()=>import("@/views/Register")
  },
  {
    path: '/buyerLogin',
    name: 'BuyerLogin',
    component: ()=>import("@/views/buyer/BuyerLogin")
  },
  {
    path: '/buyerRegister',
    name: 'BuyerRegister',
    component: ()=>import("@/views/buyer/BuyerRegister")
  },
  {
    path: '/home',
    name: 'Home',
    component: ()=>import("@/views/buyer/Home")
  },
  {
    path: '/buyerPerson',
    name: 'BuyerPerson',
    component: ()=>import("@/views/buyer/BuyerPerson")
  },
  {
    path: '/detail',
    name: 'Detail',
    component: ()=>import("@/views/buyer/Detail")
  },
  {
    path: '/order',
    name: 'Order',
    component: ()=>import("@/views/buyer/Order")
  },
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
