<template>
  <div class="el-container">
    <div style="width: 500px">
      <img src="@/assets/css/imgs/login_background1.jpg" alt="" style="width: 100%">
    </div>

    <div style="flex: 1;background-color: whitesmoke">
      <div style="height: 60px; display: flex; align-items: center; background-color: white; justify-content: center">

        <img src="@/assets/css/imgs/login_header.jpg" alt="" style="width: 40px; margin-left: 10px">
        <div style="font-size: 35px; margin-left: 20px">缺 一 人 才 招 聘 网 站</div>
      </div>

      <!--      滑块认证-->
      <div>
        <el-card class="cover" v-if="loginUser.id">
          <slide-verify :l="42"
                        :r="10"
                        :w="310"
                        :h="155"
                        slider-text="向右滑动"
                        @success="onSuccess"
                        @fail="onFail"
                        @refresh="onRefresh"></slide-verify>
        </el-card>
      </div>

      <!--      登录-->
      <div style="height: calc(100vh - 50px); display: flex;align-items: center; justify-content: center">
        <div style="width: 400px; padding: 40px; background-color: white; border-radius: 5px">
          <div style="display: flex;margin-bottom: 50px; font-size: 20px">
            <div style="border-bottom: 2px solid #8cc5ff; padding-bottom: 10px">登录</div>
            <a style="color: #333;text-decoration:none" href="/api/register">
              <div style="margin-left: 10px;">注册</div>
            </a>
          </div>
          <el-form :model="form" :rules="rules" ref="formRef">
            <!--            <el-form-item prop="role">-->
            <!--              <el-select size="large" v-model="form.role" style="width: 100%">-->
            <!--                <el-option label="个人用户" value="USER"></el-option>-->
            <!--                <el-option label="企业公司" value="COMPANY"></el-option>-->
            <!--                <el-option label="客服管理" value="ADMIN"></el-option>-->
            <!--              </el-select>-->
            <!--            </el-form-item>-->
            <el-form-item prop="phone">
              <el-input size="large" prefix-icon="el-icon-user" placeholder="请输入手机号"
                        v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input size="large" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password
                        v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="large"
                         style="width: 100%; background-color: #8cc5ff; border-color: #8cc5ff;color: white"
                         @click="login">登 录
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>

    <div style="width: 500px">
      <img src="@/assets/css/imgs/login_background3.jpg" alt="" style="width: 100%">
    </div>

  </div>

</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {

  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      form: {},
      rules: {
        phone: [
          {required: true, phone: '请输入手机号', trigger: 'blur'},
        ],
        password: [
          {required: true, password: '请输入密码', trigger: 'blur'},
        ],
      },
      loginUser: {},
      created() {
      },
    }
  },
  methods: {
    login() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          request.post('/admin/login', this.form).then(res => {
            if (res.code === '200') {
              this.loginUser = res.data   //跳出滑块组件，认证
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    onSuccess() {
      Cookies.set('user', JSON.stringify(this.loginUser)) //设置Cookie
      this.$router.push('/admin_home')  //跳转主页
      this.$notify.success('登录成功')
    },
    onFail() {

    },
    onRefresh() {
      console.log('refresh')
    }
  }
}
</script>

<style scoped>
.el-container {
  height: 100vh;
  overflow: hidden;
  display: flex;
}

a {
  color: #2a60c9;
}

.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>