<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C6E2FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width:70%;background-color: white;height: 2000px">
        <div style="display: flex">
          <el-card style="margin: 50px 100px">
            <div slot="header" style="font-size: 20px; color: #355476; font-weight: bold"><span>账号信息</span></div>
            <div>
              <el-form ref="form" :model="user" :inline="true">
                <el-form-item label="姓名" prop="username" style="margin-left: 35px">
                  <el-input v-model="user.username" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phone" style="margin-left: 35px">
                  <el-input v-model="user.phone" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" style="margin-left: 35px">
                  <el-input v-model="user.email" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" style="margin-left: 35px">
                  <el-input v-model="user.password" style="width: 300px" show-password></el-input>
                </el-form-item>
                <el-form-item label="证件照" prop="logo" style="margin-left: 35px">
                  <div>
                    <el-image v-if="user.img" :src="user.img" style="width: 50px; height: 70px"></el-image>
                    <el-upload
                        :show-file-list="false"
                        action="http://localhost:9090/api/file/upload"
                        :headers="{token: user.token}"
                        :file-list="fileList"
                        list-type="picture"
                        :on-success="(res,file,fileList) => handleImgUpload(res,file,fileList)">
                      <el-button plain>上传证件照</el-button>
                    </el-upload>
                  </div>
                </el-form-item>

              </el-form>
              <div slot="footer" style="text-align: center">
                <el-button type="primary" plain @click="saveInfo">保 存</el-button>
              </div>
            </div>
          </el-card>
        </div>
        <div style="display: flex">
          <el-card style="margin: 5px 100px">
            <div slot="header" style="font-size: 20px; color: #355476; font-weight: bold"><span>会员中心</span></div>
            <div>
              <div slot="footer" style="text-align: center">
                <el-button type="primary" plain @click="saveResume">保 存</el-button>
              </div>
            </div>
          </el-card>
        </div>
      </div>
      <div class="right"></div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "MyInformationView",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      resume:{},
      statuses: [{
        value: '离校-随时到岗',
        label: '离校-随时到岗'
      }, {
        value: '在校-考虑机会',
        label: '在校-考虑机会'
      }, {
        value: '在校-月内到岗',
        label: '在校-月内到岗'
      }, {
        value: '在校-暂不考虑',
        label: '在校-暂不考虑'
      }],
      identities: [{
        value: '学生',
        label: '学生'
      }, {
        value: '职场人',
        label: '职场人'
      }],
      fileList: []
    }
  }
  ,
  created() {
    request.get('/user/' + this.user.id).then(res => {
      this.user = res.data
    })
    request.get('/resume/'+ this.user.phone).then(res => {
      this.resume = res.data
    })
  }
  ,
  methods: {
    changeHandler(value) {
      this.user.sex = value
    },
    handleImgUpload(response, file, fileList) {
      this.user.img = response.data
      this.fileList = fileList
    },
    saveInfo() {
      request.post('/user/save_info', this.user).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    saveResume() {
      request.post('/resume/save_resume', this.resume).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.main-content {
  min-height: 100vh;
  width: 100%;
  margin: 0 auto;
  background-size: 100%;
}

.left {
  width: 15%;
  background-repeat: no-repeat;
  background-color: #C6E2FF;
}

.right {
  width: 15%;
  background-repeat: no-repeat;
  background-color: #C6E2FF
}

#myResume >>> .el-form-item__label {
  font-weight: bold;
  font-size: 18px;
}
</style>