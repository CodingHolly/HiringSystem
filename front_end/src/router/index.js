import Vue from 'vue'
import VueRouter from 'vue-router'

import Layout from "@/views/Layout.vue";
import Login from "@/views/Login/Login.vue";

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
        redirect: '/home',
        children: [     //子路由，Layout.vue主体中的<router-view />
                        //  ===== Home后台管理主页 =====
            {
                path: 'home',
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
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
