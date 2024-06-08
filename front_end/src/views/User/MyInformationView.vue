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
                <el-form-item label="姓名" prop="username" style="margin-left: 100px">
                  <el-input v-model="user.username" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phone" style="margin-left: 35px">
                  <el-input v-model="user.phone" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" style="margin-left: 100px">
                  <el-input v-model="user.email" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" style="margin-left: 35px">
                  <el-input v-model="user.password" style="width: 200px" show-password disabled></el-input>
                  <el-button type="text" style="margin-left: 10px; color: gray;font-size: small" @click="passwordFormVisible = true">
                    修改密码
                  </el-button>
                </el-form-item>
                <el-form-item label="证件照" prop="logo" style="margin-left: 100px">
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

        <!--        密码修改弹窗-->
        <el-dialog title="密码修改" :visible.sync="passwordFormVisible" width="30%" :close-on-click-modal="false"
                   destroy-on-clos>
          <el-form label-width="100px" :model="passwordForm" :rules="passwordRules" ref="passwordForm">
            <el-form-item label="新密码：" prop="newPassword" label-width="100px">
              <el-input v-model="passwordForm.newPassword" type="password" autocomplete="off" style="width: 200px"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="passwordFormVisible = false">取 消</el-button>
            <el-button type="primary" @click.native="savePassword">确 定</el-button>
          </div>
        </el-dialog>
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
      resume: {},
      passwordForm: {},
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
      fileList: [],
      passwordFormVisible: false,
      passwordRules: {
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3-10个字符', trigger: 'blur'}
        ]
      },
    }
  }
  ,
  created() {
    request.get('/user/' + this.user.id).then(res => {
      this.user = res.data
    })
    request.get('/resume/' + this.user.phone).then(res => {
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
    },
    savePassword() {
      this.$refs['passwordForm'].validate((valid) => {
        if (valid) {
          this.passwordForm.phone = this.user.phone
          request.post('/user/password', this.passwordForm).then(res => {
            if (res.code === '200') {
              this.$notify.success("修改成功, 请重新登录")
              this.$router.push('/')
            } else {
              this.$notify.error("修改失败")
            }
          })
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