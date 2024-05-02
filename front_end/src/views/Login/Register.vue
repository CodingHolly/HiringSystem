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

      <!--      登录-->
      <div style="height: calc(100vh - 50px); display: flex;align-items: center; justify-content: center">
        <div style="width: 400px; padding: 40px; background-color: white; border-radius: 5px">
          <div style="display: flex;margin-bottom: 50px; font-size: 20px">
            <a style="color: #333;text-decoration:none" href="/">
              <div>登录</div>
            </a>
            <div style="border-bottom: 2px solid #8cc5ff; padding-bottom: 10px; margin-left: 10px">注册</div>
          </div>
          <el-form :model="form" :rules="rules" ref="formRef">
            <el-form-item prop="role">
              <el-select size="large" v-model="form.role" style="width: 100%">
                <el-option label="个人用户" value="USER"></el-option>
                <el-option label="企业公司" value="COMPANY"></el-option>
                <el-option label="客服管理" value="ADMIN"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="username">
              <el-input size="large" prefix-icon="el-icon-user" placeholder="请输入用户名"
                        v-model="form.username"></el-input>
            </el-form-item>
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
                         @click="register">注 册
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
  name: "Register",
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
        role: [
          {required: true, role: '请选择角色', trigger: 'blur'}
        ]
      },
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
    }
  },
  methods: {
    register() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              // localStorage.setItem("xm-user", JSON.stringfy(res.data))
              Cookies.set('user', JSON.stringify(this.user)) // 设置Cookie
              this.$router.push('/')
              this.$notify.success('注册成功，请重新登录')
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>

</style>