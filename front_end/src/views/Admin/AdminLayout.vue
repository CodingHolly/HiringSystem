<!--嵌套主页内容-->
<template>
  <div>
    <!--    头部区域-->
    <div style="height: 60px; line-height: 60px; background-color: #ecf5ff; margin-bottom: 5px; display: flex">
      <div style="width: 300px">
        <img src="@/assets/css/imgs/logo.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">
        <span style="margin-left: 35px; font-size: 22px">系统后台管理</span>
      </div>
<!--      下拉菜单-->
      <div style="flex: 1; text-align: right; padding-right: 40px">
        <el-dropdown size="large">
        <span class="el-dropdown-link">
          {{user.username}} ，你好！<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><div style="text-align: center" @click="goHome">系统首页</div></el-dropdown-item>
            <el-dropdown-item><div style="text-align: center" @click="logout">退出登录</div></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--    侧边栏和主体-->
    <div style="display: flex">
      <!--    侧边栏导航-->
      <div
          style="width: 200px; min-height: calc(100vh - 65px); overflow: hidden; margin-right: 15px;background-color: white">
        <el-menu :default-active="$route.path" :default-openeds="['2','3']" router class="el-menu-demo">
          <el-menu-item index="/admin/home">
            <i class="el-icon-s-home"></i>
            <span>系统首页</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/admin/announcement">公告信息</el-menu-item>
            <el-menu-item index="/admin/company_information">企业信息</el-menu-item>
            <el-menu-item index="/admin/position_type">职位分类</el-menu-item>
            <el-menu-item index="/admin/position_information">职位信息</el-menu-item>
            <el-menu-item index="/admin/comment_management">留言管理</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>人员管理</span>
            </template>
            <el-menu-item index="/admin/admin_manage">管理员管理</el-menu-item>
            <el-menu-item index="/admin/company_manage">企业人员管理</el-menu-item>
            <el-menu-item index="/admin/user_manage">个人用户管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!--    主体-->
      <div style="flex: 1;background-color: white; padding: 30px">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Layout",
  created() {
    request.get('/admin/' + this.user.id).then(res => {
      this.user = res.data
    })
  },
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
    }
  },
  methods: {
    logout() {
      //清除浏览器用户数据，再返回
      Cookies.remove('user')
      this.$router.push('/')
    },
    goHome() {
      this.$router.push('/admin/home')
    }
  },

}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #6495ED;
}
.el-icon-arrow-down {
  font-size: 20px;
}
</style>