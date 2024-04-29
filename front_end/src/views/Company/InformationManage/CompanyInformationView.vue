<template>
  <div>
    <el-card style="margin: 50px 100px 0 100px">
      <div slot="header" style="font-size: 23px">
        <span>企业信息</span>
      </div>
      <div>
        <el-form ref="form" :model="companyInfo" :inline="true">
          <el-form-item label="企业名称" prop="companyName" style="margin-left: 20px">
            <el-input v-model="companyInfo.companyName"></el-input>
          </el-form-item>
          <el-form-item label="法定代表人" prop="legalRepresentative" style="margin-left: 20px">
            <el-input v-model="companyInfo.legalRepresentative"></el-input>
          </el-form-item>
          <el-form-item label="成立时间" prop="foundingTime" style="margin-left: 20px">
            <el-date-picker
                v-model="companyInfo.foundingTime"
                type="date"
                placeholder="选择日期"
                style="width: 210px">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="注册地点" prop="registerAddress" style="margin-left: 20px">
            <el-input v-model="companyInfo.registerAddress" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="经营状态" prop="managementStatus" style="margin-left: 20px">
            <el-select v-model="companyInfo.managementStatus" style="width: 100px">
              <el-option
                  v-for="status in status"
                  :key="status.value"
                  :label="status.label"
                  :value="status.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="注册资本" prop="registeredCapital" style="margin-left: 20px">
            <el-input v-model="companyInfo.registeredCapital" style="width: 233px"></el-input>
          </el-form-item>
          <el-form-item label="公司简介" prop="companyProfile" style="margin-left: 20px">
            <el-input v-model="companyInfo.companyProfile" type="textarea" :rows="3" style="width: 830px"></el-input>
          </el-form-item>
          <el-form-item label="人数规模" prop="companyProfile" style="margin-left: 20px">
            <el-input v-model="companyInfo.companyScale" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="公司logo">
            <el-upload
                action="http://localhost:9090/api/file/upload"
                :headers="{token: user.token}"
                :file-list="fileList"
                list-type="picture"
                :on-success="(companyname,res,file,fileList) => handleLogoUpload(this.companyInfo.companyName,res,file,fileList)">
              <el-button size="small" type="primary" plain>上传Logo</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center">
          <el-button type="primary" plain @click="save">保 存</el-button>
        </div>
      </div>
    </el-card>

    <el-card style="margin: 20px 100px 40px 100px">
      <div slot="header" style="font-size: 23px">
        <span>地址信息</span>
      </div>
      <div>
        <el-form>
          <el-form-item
              style="margin-left: 40px"
              v-for="(address, index) in companyAddresses"
              :label="'地址' + index"
              :key="address.key"
              :prop="'companyAddresses.' + index + '.companyAddress'">
            <el-input style="width: 400px" v-model="address.companyAddress">{{ address.companyAddress }}</el-input>
            <el-button style="margin-left: 10px" type="danger" plain size="small"
                       @click.prevent="removeAddress(address)">删除
            </el-button>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center;margin-left: 20px;margin-bottom: 20px">
          <el-button type="primary" plain @click="addAddress">新增地址
          </el-button>
          <el-button style="margin-left: 20px" type="primary" plain @click="saveAddress">保存地址
          </el-button>
        </div>
      </div>

    </el-card>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "CompanyInformationView",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      companyInfo: {},
      companyAddresses: [{
        id: '',
        companyName: '',
        companyAddress: ''
      }],
      status: [{
        value: '存续',
        label: '存续',
      }, {
        value: '在业',
        label: '在业',
      }, {
        value: '吊销',
        label: '吊销'
      }, {
        value: '注销',
        label: '注销'
      }],
      fileList:[]
    }
  },
  created() {
    request.get('/company/' + this.user.id).then(res => {
      this.user = res.data
    })
    request.get('/company_info/' + this.user.companyName).then(res => {
      this.companyInfo = res.data
    })
    request.get('/company_info/address/' + this.user.companyName).then(res => {
      this.companyAddresses = res.data
    })
  },
  methods: {
    removeAddress(item) {
      const index = this.companyAddresses.indexOf(item);
      if (index !== -1) {
        this.companyAddresses.splice(index, 1)
      }
    },
    addAddress() {
      this.companyAddresses.push({
        companyAddress: '',
        companyName: this.companyInfo.companyName,
      });
    },
    handleLogoUpload(response, file, fileList) {
      this.companyInfo.logo = response.data
      this.fileList = fileList
    }
  }
}
</script>

<style scoped>

</style>