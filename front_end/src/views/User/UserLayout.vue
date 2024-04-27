<!--嵌套主页内容-->
<template>
  <div>
    <!--    头部区域-->
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/css/imgs/logo.png" alt="">
        <div class="title">缺一人才招聘网站</div>
      </div>

      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
            <el-menu-item index="/user/home">前台</el-menu-item>
            <el-menu-item index="/user/person">个人中心</el-menu-item>
          </el-menu>
        </div>
      </div>

      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown">
              <div style="">
                <span>{{user.username}}</span>
              </div>
            </div>
          </el-dropdown>
        </div>
      </div>
    </div>
    <div>
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
  name: "UserHomeLayout",
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
      this.$router.push('/admin/home')
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