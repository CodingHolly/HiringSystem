<!--嵌套主页内容-->
<template>
  <div>
    <!--    头部区域-->
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/css/imgs/logo.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">
        <span class="title">缺一人才招聘网站</span>
      </div>

      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
            <el-menu-item index="/user/home">首页</el-menu-item>
            <el-menu-item index="/user/search_company">搜索 | 公司</el-menu-item>
            <el-menu-item index="/user/search_position">搜索 | 职位</el-menu-item>
            <el-menu-item index="/user/my_resume">我的简历</el-menu-item>
            <el-menu-item index="/user/my_information">个人中心</el-menu-item>
          </el-menu>
        </div>
      </div>

      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button type="text" @click="$router.push('/')">登录</el-button>
          <el-button type="text" @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown">
              <span>{{ user.username }}</span><i class="el-icon-arrow-down" style="margin-left: 10px;"></i>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div style="text-align: center" @click="myMessage">消息通知</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-align: center" @click="safetyAndMember">安全与会员</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-align: center" @click="logout">退出登录</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <div>
      <!--    主体-->
      <div class="main-body">
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
      this.$router.push('/')
    },
    safetyAndMember() {
      this.$router.push('/user/my_information')
    },
    myMessage() {
      this.$router.push('/user/my_message')
    },
  },
  created() {
    request.get('/user/' + this.user.id).then(res => {
      this.user = res.data
    })
  }
}
</script>

<style scoped>
.front-header {
  display: flex;
  height: 60px;
  line-height: 60px;
  margin-bottom: 5px;
  border-bottom-color: #eeeeee;
  background-color: rgb(255, 255, 255);
}

.front-header-left {
  width: 350px;

}

.front-header-left .title {
  flex: 1;
  color: #355476;
  cursor: pointer;
  margin-left: 35px;
  font-size: 22px;
  font-weight: 700;
}

.front-header-center {
  flex: 1;
}

.front-header-right {
  width: 250px;
  padding-right: 20px;
  text-align: right;
}

.front-header-dropdown {
  display: flex;
  align-items: center;
  justify-content: right;
}

.el-icon-arrow-down {
  margin-left: 5px;
}
</style>