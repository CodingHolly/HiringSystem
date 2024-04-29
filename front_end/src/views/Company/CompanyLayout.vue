<template>
  <div>
    <!--    头部区域-->
    <div style="height: 60px; line-height: 60px; background-color: #ecf5ff; margin-bottom: 5px; display: flex">
      <div style="width: 300px">
        <img src="@/assets/css/imgs/logo.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">
        <span style="margin-left: 35px; font-size: 22px">企业后台管理</span>
      </div>
      <!--      下拉菜单-->
      <div style="flex: 1; text-align: right; padding-right: 40px">
        <el-dropdown size="large">
        <span class="el-dropdown-link">
          {{user.username}} ，你好！<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><div style="text-align: center" @click="goCompanyHome">企业系统首页</div></el-dropdown-item>
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
          <el-menu-item index="/company/home">
            <i class="el-icon-s-home"></i>
            <span>企业系统首页</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/company/company_information">企业信息</el-menu-item>
            <el-menu-item index="/company/position_information">岗位信息</el-menu-item>
            <el-menu-item index="/company/resume_management">简历管理</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>人员管理</span>
            </template>
            <el-menu-item index="/company/company_manage">企业人员管理</el-menu-item>
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

export default {
  name: "CompanyLayout",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
    }
  },
  methods: {
    goCompanyHome() {
      this.$router.push('/company/home')
    },
    logout() {
      //清除浏览器用户数据，再返回
      Cookies.remove('user')
      this.$router.push('/')
    },
  }
}
</script>

<style scoped>

</style>