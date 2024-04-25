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

      <div style="height: calc(100vh - 50px); display: flex;align-items: center; justify-content: center">
        <div style="width: 400px; padding: 40px; background-color: white; border-radius: 5px">
          <div style="display: flex;margin-bottom: 50px; font-size: 20px">
            <div style="border-bottom: 2px solid #8cc5ff; padding-bottom: 10px">登录</div>
<!--            <a style="color: #333;text-decoration:none" href="/register">-->
<!--              <div style="margin-left: 10px;">注册</div>-->
<!--            </a>-->

          </div>
          <el-form :model="form" :rules="rules" :ref="formRef">
            <el-form-item prop="role">
              <el-select size="large" v-model="form.role" style="width: 100%">
                <el-option label="个人用户" value="USER"></el-option>
                <el-option label="企业公司" value="COMPANY"></el-option>
                <el-option label="客服管理" value="SERVICE"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="username">
              <el-input size="large" prefix-icon="el-icon-user" placeholder="请输入账号"
                        v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input size="large" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password
                        v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="large"
                         style="width: 100%; background-color: #8cc5ff; border-color: #8cc5ff;color: white">登 录
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
export default {

  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login.vue",
  data() {
    return {
      form: {
        role: 'USER'
      },
      rules: {
        // username: [
        //     required: true,
        //     message: '请输入账号',
        //     trigger: 'blur'
        // ]
      },
      created() {
      },
      methods: {
        login() {
          this.$refs['formRef'].validate((valid) => {
            if (valid) {
              //验证通过
              request.post('/login', this.form).then(res => {
                if (res.code === '200') {
                  localStorage.setItem("hiring-system", JSON.stringify(res.data())) //存储用户信息
                  if (res.data.role === 'USER') {  //跳转用户个人页面
                    this.$router.push('/user');
                  } else if (res.data.role === 'COMPANY') {  //跳转企业公司页面
                    this.$router.push('/');
                  } else {  //跳转客服服务页面
                    this.$router.push('/')
                  }
                  this.$message.success('登录成功');
                } else {
                  this.$message.error(res.msg);
                }
              })
            }
          })
        }
      }
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
</style>