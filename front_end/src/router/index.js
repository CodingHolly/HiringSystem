import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from "@/views/Login/Login.vue";
import AdminLayout from "@/views/Admin/AdminLayout.vue";
import UserLayout from "@/views/User/UserLayout.vue";
import CompanyLayout from "@/views/Company/CompanyLayout.vue";
import Register from "@/views/Login/Register.vue";

Vue.use(VueRouter)

const routes = [
    //  ===== Login登录 =====
    {
        path: '/',
        name: 'Login',
        component: Login,
    },
    //  ===== Register注册 =====
    {
        path: '/register',
        name: 'Register',
        component: Register,
    },
    //  ===== Home后台管理主页 =====
    {
        path: '/admin/',
        name: 'AdminLayout',
        component: AdminLayout,
        children: [     //子路由，Layout.vue主体中的<router-view />
            //  ===== AdminHome后台管理主页 =====
            {
                path: 'home',
                name: 'Home',
                component: () => import('@/views/Admin/AdminHomeView.vue')
            },
            //  ===== Admin后台管理 =====
            {
                path: 'announcement',
                name: 'announcement',
                component: () => import('@/views/Admin/InformationManage/AnnouncementView.vue')
            }, {
                path: 'comment_management',
                // name: 'comment',
                component: () => import('@/views/Admin/InformationManage/CommentManageView.vue')
            }, {
                path: 'position_type',
                component: () => import('@/views/Admin/InformationManage/PositionTypeView.vue')
            }, {
                path: 'position_information',
                component: () => import('@/views/Admin/InformationManage/PositionInformationView.vue')
            }, {
                path: 'company_information',
                component: () => import('@/views/Admin/InformationManage/CompanyInformationView.vue')
            }, {
                path: 'admin_manage',
                component: () => import('@/views/Admin/PersonnelManage/AdminManageView.vue')
            }, {
                path: 'company_manage',
                component: () => import('@/views/Admin/PersonnelManage/CompanyManageView.vue')
            }, {
                path: 'user_manage',
                component: () => import('@/views/Admin/PersonnelManage/UserManageView.vue')
            },
        ]
    },
    //  ===== 404页面 =====
    {
        path: "*",
        component: () => import('@/views/404.vue')
    },
    //  ===== User用户前台页面 =====
    {
        path: '/user/',
        component: UserLayout,
        children: [{
            path: 'home',
            name: 'Home',
            component: () => import('@/views/User/UserHomeView.vue')
        }, {
            path: 'search_company',
            component: () => import('@/views/User/SearchCompanyView.vue')
        }, {
            path: 'search_position',
            component: () => import('@/views/User/SearchPositionView.vue')
        }, {
            path: 'my_resume',
            component: () => import('@/views/User/MyResumeView.vue')
        }, {
            path: 'my_message',
            component: () => import('@/views/User/MyMessageView.vue')
        }, {
            path: 'my_information',
            component: () => import('@/views/User/MyInformationView.vue')
        }, {
            path: 'position_details',
            component: () => import('@/views/User/PositionDetailsView.vue')
        }, {
            path: 'submit_message',
            component: () => import('@/views/User/SubmitMessageView.vue')
        }, {
            path: 'position_category',
            component: () => import('@/views/User/PositionCategoryView.vue')
        }]
    },
    //  ===== Company企业后台页面 =====
    {
        path: '/company/',
        component: CompanyLayout,
        children: [{
            path: 'home',
            component: () => import('@/views/Company/CompanyHomeView.vue')
        }, {
            path: 'company_information',
            component: () => import('@/views/Company/InformationManage/CompanyInformationView.vue')
        }, {
            path: 'position_information',
            component: () => import('@/views/Company/InformationManage/PositionInformationView.vue')
        }, {
            path: 'resume_management',
            component: () => import('@/views/Company/InformationManage/ResumeManagementView.vue')
        }, {
            path: 'company_manage',
            component: () => import('@/views/Company/PersonnelManage/CompanyManageView.vue')
        }]
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 路由守卫
// router.beforeEach((to, from, next) => {
//     // 若跳转路径是登录路径，则放行
//     if (to.path === '/login')
//         next()
//     //获取当前cookie数据
//     const user = Cookies.get("user")
//     //若没有cookie数据，并且跳转其他路径，则强制跳转到登录路径
//     if (!user && to.path !== '/login')
//         return next("/login")
//     //访问/home，并且cookie中存在数据，则放行
//     next()
// })
export default router
