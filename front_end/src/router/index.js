import Vue from 'vue'
import VueRouter from 'vue-router'

import Layout from "@/views/Layout.vue";
import Login from "@/views/Login/Login.vue";
import Cookies from "js-cookie";

Vue.use(VueRouter)

const routes = [
    //  ===== Login登录 =====
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    //  ===== Home后台管理主页 =====
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect: '/admin_home',
        children: [     //子路由，Layout.vue主体中的<router-view />
            //  ===== Home后台管理主页 =====
            {
                path: 'admin_home',
                name: 'Home',
                component: () => import('@/views/Admin/HomeView.vue')
            },
            //  ===== Admin后台管理 =====
            {
                path: 'announcement',
                name: 'announcement',
                component: () => import('@/views/Admin/InformationManage/AnnouncementView.vue')
            }, {
                path: 'comment_management',
                // name: 'comment',
                component: () => import('@/views/Admin/InformationManage/CommentManage.vue')
            }, {
                path: 'position_classification',
                component: () => import('@/views/Admin/InformationManage/PositionClassificationView.vue')
            }, {
                path: 'position_information',
                component: () => import('@/views/Admin/InformationManage/PositionInformationView.vue')
            }, {
                path: 'admin_information',
                component: () => import('@/views/Admin/PersonnelManage/AdminManageView.vue')
            }, {
                path: 'company_information',
                component: () => import('@/views/Admin/PersonnelManage/CompanyManageView.vue')
            }, {
                path: 'user_information',
                component: () => import('@/views/Admin/PersonnelManage/UserManageView.vue')
            },
        ]
    },
    //  ===== 404页面 =====
    {
        path: "*",
        component: () => import('@/views/404.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    // 若跳转路径是登录路径，则放行
    if (to.path === '/login')
        next()
    //获取当前cookie数据
    const user = Cookies.get("user")
    //若没有cookie数据，并且跳转其他路径，则强制跳转到登录路径
    if (!user && to.path !== '/login')
        return next("/login")
    //访问/home，并且cookie中存在数据，则放行
    next()
})
export default router
