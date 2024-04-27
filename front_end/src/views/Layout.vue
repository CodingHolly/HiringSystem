<!--嵌套主页内容-->
<template>
  <div>
    <!--    头部区域-->
    <div style="height: 60px; line-height: 60px; background-color: #ecf5ff; margin-bottom: 5px; display: flex">
      <div style="width: 300px">
        <img src="@/assets/css/imgs/logo.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">
        <span style="margin-left: 35px; font-size: 22px">后台管理</span>
      </div>
<!--      下拉菜单-->
      <div style="flex: 1; text-align: right; padding-right: 40px">
        <el-dropdown size="large">
        <span class="el-dropdown-link">
          {{user.username}} ，你好！<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><div style="text-align: center" @click="goHome">系统首页</div></el-dropdown-item>
            <el-dropdown-item><div style="text-align: center" @click="logout">退出登入</div></el-dropdown-item>
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
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span>系统首页</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/announcement">公告信息</el-menu-item>
            <el-menu-item index="/position_classification">职位分类</el-menu-item>
            <el-menu-item index="/position_information">职位信息</el-menu-item>
            <el-menu-item index="/comment_management">评论管理</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>人员管理</span>
            </template>
            <el-menu-item index="/admin_information">管理员信息</el-menu-item>
            <el-menu-item index="/company_information">企业信息</el-menu-item>
            <el-menu-item index="/user_information">用户信息</el-menu-item>
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
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Layout",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
    }
  },
  methods: {
    logout() {
      //清除浏览器用户数据，再返回
      Cookies.remove('user')
      this.$router.push('/login')
    },
    goHome() {
      this.$router.push('/admin_home')
    }
  }
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