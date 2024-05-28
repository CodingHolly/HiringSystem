<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C6E2FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width:70%;background-color: white;height: 2000px">
        <div style="display: flex">
          <el-card style="margin: 50px 100px">
            <div slot="header" style="font-size: 20px; color: #355476; font-weight: bold"><span>个人信息</span></div>
            <div>
              <el-form ref="form" :model="user" :inline="true">
                <el-form-item label="姓名" prop="username" style="margin-left: 35px">
                  <el-input v-model="user.username" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phone" style="margin-left: 35px">
                  <el-input v-model="user.phone" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex" style="margin-left: 35px">
                  <el-radio-group v-model="user.sex" @change="changeHandler">
                    <el-radio-button label="男"></el-radio-button>
                    <el-radio-button label="女"></el-radio-button>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="身份" prop="identity" style="margin-left: 15px">
                  <el-select v-model="user.identity" style="width: 125px;">
                    <el-option
                        v-for="option in identities"
                        :key="option.value"
                        :label="option.label"
                        :value="option.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="当前求职状态" prop="jobSearchStatus" style="margin-left: 35px">
                  <el-select v-model="user.jobSearchStatus" style="width: 200px;">
                    <el-option
                        v-for="status in statuses"
                        :key="status.value"
                        :label="status.label"
                        :value="status.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="出生年月日" prop="birthday" style="margin-left: 35px">
                  <el-date-picker
                      v-model="user.birthday"
                      type="date"
                      placeholder="选择日期"
                      style="width: 210px"
                      value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" style="margin-left: 85px">
                  <el-input v-model="user.email" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="出生地" prop="birthplace" style="margin-left: 35px">
                  <el-input v-model="user.birthplace" style="width: 285px" placeholder="国家/省/市"></el-input>
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
            <div slot="header" style="font-size: 20px; color: #355476; font-weight: bold"><span>我的简历</span></div>
            <div>
              <el-form id="myResume">
                <el-form-item label="个人优势" prop="personalAdvantage" style="margin-left: 20px; margin-right: 20px">
                  <el-input v-model="resume.personalAdvantage" type="textarea" :rows="3" style="width: 780px"></el-input>
                </el-form-item>
                <el-form-item label="期望职位" prop="expectedPosition" style="margin-left: 20px; margin-right: 20px">
                  <el-input v-model="resume.expectedPosition" type="textarea" :rows="3" style="width: 780px"></el-input>
                </el-form-item>
                <el-form-item label="工作经历" prop="workExperience" style="margin-left: 20px; margin-right: 20px">
                  <el-input v-model="resume.workExperience" type="textarea" :rows="3" style="width: 780px"></el-input>
                </el-form-item>
                <el-form-item label="项目经历" prop="projectExperience" style="margin-left: 20px; margin-right: 20px">
                  <el-input v-model="resume.projectExperience" type="textarea" :rows="3" style="width: 780px"></el-input>
                </el-form-item>
                <el-form-item label="教育经历" prop="educationExperience" style="margin-left: 20px; margin-right: 20px">
                  <el-input v-model="resume.educationExperience" type="textarea" :rows="3" style="width: 780px"></el-input>
                </el-form-item>
              </el-form>
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