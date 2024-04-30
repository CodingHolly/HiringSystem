<template>
  <div>
    <div style="display: flex">
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>企业名称</span>
          </div>
          <div class="text">
            {{ companyInfo.companyName }}
          </div>
        </el-card>
      </div>
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>法定代表人</span>
          </div>
          <div class="text">
            {{ companyInfo.legalRepresentative }}
          </div>
        </el-card>
      </div>
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>成立时间</span>
          </div>
          <div class="text">
            {{ companyInfo.foundingTime }}
          </div>
        </el-card>
      </div>
      <div class="company-logo">
        <el-card class="logo" :body-style="{display:'flex',padding:'5px', justifyContent:'center'}">
          <div slot="header">
            <span>企业Logo</span>
          </div>
          <div>
            <el-image v-if="companyInfo.logo" :src="companyInfo.logo" style="width: 70px; height: 70px;align-items: center"></el-image>
          </div>
        </el-card>
      </div>
    </div>

    <div style="display: flex">
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>注册地址</span>
          </div>
          <div class="text">
            {{ companyInfo.registerAddress }}
          </div>
        </el-card>
      </div>
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>公司地址</span>
          </div>
          <div v-for="(item, index) in companyAddresses" :key="index">
            <div class="address-text">{{ item.companyAddress }}</div>
          </div>
        </el-card>
      </div>
    </div>
    <div style="display: flex">
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>企业类别</span>
          </div>
          <div class="text">
            {{ companyInfo.companyCategory }}
          </div>
        </el-card>
      </div>
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>注册资本</span>
          </div>
          <div class="text">
            {{ companyInfo.registeredCapital }}
          </div>
        </el-card>
      </div>
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>经营状态</span>
          </div>
          <div class="text">
            {{ companyInfo.managementStatus }}
          </div>
        </el-card>
      </div>
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>人数规模</span>
          </div>
          <div class="text">
            {{ companyInfo.companyScale }}
          </div>
        </el-card>
      </div>
    </div>

    <div style="display: flex">
      <div class="company-info">
        <el-card>
          <div slot="header">
            <span>公司简介</span>
          </div>
          <div class="profile-text">
            {{ companyInfo.companyProfile }}
          </div>
        </el-card>
      </div>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "CompanyHomeView",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      companyInfo: {},
      companyAddresses: [],

    }
  },
  created() {
    request.get('/company_admin/' + this.user.id).then(res => {
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
    handleLogoDisplay() {

    }
  }
}
</script>

<style scoped>
.text {
  font-size: 20px;
  text-align: center;
}

.address-text {
  font-size: 18px;
  text-align: left;
  margin-left: 10px;
  margin-bottom: 10px;
}

.profile-text {
  font-size: 15px;
  text-align: left;
}


.company-info {
  margin-top: 20px;
  margin-right: 30px;
  flex: 2.5;
}
.company-logo{
  margin-top: 20px;
  margin-right: 30px;
  flex: 2.5;
}


/*.company-name {*/
/*  margin-right: 30px;*/
/*  margin-top: 20px;*/
/*}*/
/*.company-legal-representative {*/
/*  margin-right: 30px;*/
/*}*/
/*.company-founding-time {*/
/*  margin-right: 30px;*/
/*}*/
/*.company-category {*/
/*  margin-right: 30px;*/
/*}*/
/*.company-name {*/
/*  margin-right: 30px;*/
/*}*/
/*.company-name {*/
/*  margin-right: 30px;*/
/*}*/
/*.company-name {*/
/*  margin-right: 30px;*/
/*}*/
/*.company-register-address {*/
/*  margin-right: 30px;*/
/*  margin-top: 40px;*/
/*}*/

</style>