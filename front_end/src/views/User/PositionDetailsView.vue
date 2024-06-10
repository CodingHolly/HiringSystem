<template>
  <div class="main">
    <div class="job-banner">
      <div class="inner home-inner">
        <div class="job-primary detail-box">
          <div class="info-primary">
            <div class="job-status">
              <span>{{ positionData.jobStatus }}</span>
            </div>
            <div class="name">
              <h1 title="positionData.positionName">{{ positionData.positionName }}</h1>
              <span class="salary">{{ positionData.salary }}</span>
              <p style="margin-top: -5px">
                <span class="text-desc text-city">
                  <img src="@/assets/css/imgs/city.png" alt=""
                       style="width: 20px;height: 20px;margin-right: 6px;vertical-align: middle;">
                  {{ positionData.city }}</span>
                <span class="text-desc text-experience">
                  <img src="@/assets/css/imgs/experience.png" alt=""
                       style="width: 20px;height: 20px;margin-right: 6px; vertical-align: middle;">
                  {{ positionData.experience }}</span>
                <span class="text-desc text-degree">
                  <img src="@/assets/css/imgs/degree.png" alt=""
                       style="width: 20px;height: 20px;margin-right: 6px; vertical-align: middle;">
                  {{ positionData.degree }}</span>
              </p>
              <div class="job-op">
                <div class="btn-container">
                  <el-button
                      style="color: #00d1d0;background-color: #355476; border: 1px solid #00bedb; border-radius: 12px;width: 150px;font-size: 18px"
                      @click="handleInterest">感兴趣
                  </el-button>
                  <el-button
                      style="margin-left: 16px;color: white;background-color: #00bedb; border: 1px solid #00bedb; border-radius: 12px;width: 150px;font-size: 18px"
                      @click="handleSubmit">投递简历
                  </el-button>
                </div>
              </div>
            </div>
          </div>
          <div class="tag-container-new" style="padding-right: 43px;">
            <div class="job-tags" v-for="(item,index) in positionWelfareList" :key="index">
              <span>{{ item }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="job-box">
      <div class="inner home-inner">
        <div class="job-sider">
          <div class="sider-company">
            <p class="title">公司基本信息</p>
            <div class="company-info">
              <img :src=companyData.logo alt="">
              <p>{{ companyData.companyName }}</p>
            </div>
            <p>
              <i class="el-icon-user" style="margin-right: 5px"></i>
              <span>{{ companyData.companyScale }}</span>
            </p>
            <p>
              <i class="el-icon-office-building" style="margin-right: 5px"></i>
              <span>{{ companyData.companyCategory }}</span>
            </p>
            <p>
              <i class="el-icon-location-information"
                 style="margin-right: 5px;"></i>
              <span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                {{ companyData.registerAddress }}</span>
            </p>
          </div>
        </div>
        <div class="job-detail">
          <div class="job-detail-section">
            <div class="detail-content-header">
              <h3>职位描述</h3>
            </div>
            <ul class="job-keyword-list">
              <li v-for="(item, index) in positionKeywordList" :key="index">{{ item }}</li>
            </ul>
            <div class="job-sec-text">
              {{ positionData.profile }}
            </div>
            <div class="detail-content-header">
              <h3>岗位要求</h3>
            </div>
            <div class="job-sec-text">
              {{ positionData.requirement }}
            </div>
            <div class="job-boss-info">
              <div class="detail-figure">
                <img :src=principalInfo.avatar>
              </div>
              <h2 class="name">{{ principalInfo.username }}</h2>
              <div class="boss-info-attr">
                {{ principalInfo.companyName }}
                <em class="vdot">·</em>
                {{ principalInfo.positionType }}
              </div>
            </div>
          </div>
          <div class="job-detail-section job-detail-company">
            <div class="detail-section-item company-info-box">
              <h3>公司介绍</h3>
              <div class="job-sec-text text">
                {{ companyData.companyProfile }}
              </div>
            </div>
            <div class="detail-section-item business-info-box">
              <h3>工商信息</h3>
              <div class="level-list-box">
                <ul class="level-list">
                  <li class="company-name">
                    <span>公司名称</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.companyName }}</p>
                  </li>
                  <li class="company-user">
                    <span>法定代表人</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.legalRepresentative }}</p>
                  </li>
                  <li class="res-time">
                    <span>成立日期</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.foundingTime }}</p>
                  </li>
                  <li class="company-type">
                    <span>企业类型</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.companyCategory }}</p>
                  </li>
                  <li class="manage-state">
                    <span>经营状态</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.managementStatus }}</p>
                  </li>
                  <li class="company-fund">
                    <span>注册资金</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.registeredCapital }}</p>
                  </li>
                  <li class="register-addr">
                    <span>注册地址</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.registerAddress }}</p>
                  </li>
                  <li class="company-scale">
                    <span>企业规模</span>
                    <p style="font-size: 14px;color: #333333">{{ companyData.companyScale }}</p>
                  </li>
                </ul>
              </div>
            </div>
            <div class="detail-section-item company-address">
              <h3>工作地址</h3>
              <div class="job-location">
                <div class="location-address">
                  <img src="@/assets/css/imgs/location.png" alt=""
                       style="width: 20px;height: 20px;margin-right: 3px; vertical-align: middle;">
                  {{ positionData.workAddress }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "PositionDetailsView",
  data() {
    let positionId = this.$route.query.id
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      positionId: positionId,
      positionData: {},
      companyData: {},
      companyAddresses: [],
      positionWelfareList: [],
      positionKeywordList: [],
      principalInfo: {},
      interestInfo: {},
      resumeInfo:{},

    }
  },
  mounted() {
    this.loadPosition()
  },
  methods: {
    loadPosition() {
      request.get('/position_info/selectById?id=' + this.positionId).then(res => {
        if (res.code === '200') {
          this.positionData = res.data
          this.positionWelfareList = this.positionData.welfare.split(' ').filter((res) => {
            return res && res.trim();
          })
          this.positionKeywordList = this.positionData.keywordList.split(' ').filter((res) => {
            return res && res.trim();
          })
          this.loadCompany()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadCompany() {
      request.get('/company_info/' + this.positionData.companyName).then(res => {
        if (res.code === '200') {
          this.companyData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
      request.get('/company_info/address/' + this.positionData.companyName).then(res => {
        if (res.code === '200') {
          this.companyAddresses = res.data
          this.loadPrincipalInfo()
        }
      })
    },
    loadPrincipalInfo() {
      request.get('/company_admin/position_info/' + this.positionData.principal).then(res => {
        if (res.code === '200') {
          this.principalInfo = res.data
        }
      })
    },
    handleInterest() {
      this.interestInfo.phone = this.user.phone
      this.interestInfo.interestId = this.positionId
      request.post('/position_info/interest', this.interestInfo).then(res => {
        if (res.code === '200') {
          this.$message.success('添加成功！可在个人中心查看')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSubmit() {
      this.resumeInfo.username = this.user.username
      this.resumeInfo.userPhone = this.user.phone
      this.resumeInfo.hrPhone = this.principalInfo.phone
      this.resumeInfo.positionId = this.positionId
      this.resumeInfo.companyName = this.companyData.companyName
      this.resumeInfo.positionName = this.positionData.positionName
      request.post('/submit_resume/submit', this.resumeInfo).then(res => {
        if(res.code === '200') {
          this.$message.success('投递成功！')
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

.job-banner {
  background: linear-gradient(90deg, #3b526a 0, #345a6d 100%);
  padding: 5px 0 20px 0;
  height: auto;
  color: #fff;
}

.inner {
  margin: 0 auto;
  width: 1184px;
}

.home-inner {
  max-width: 1184px;
}

.inner:after {
  content: '';
  display: block;
  clear: both;
}

.job-primary {
  color: #4a4160;
  position: relative;
}

.job-primary.detail-box {
  border: none;
  height: auto;
  padding-top: 10px;
  margin: 0;
  box-sizing: border-box;
}

.info-primary {
  float: left;
  position: relative;
  padding-top: 15px;
  width: 680px;
}

.job-status {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1;
  color: #fff;
  height: 22px;
  line-height: 22px;
  font-size: 14px;
}

.name h1 {
  font-size: 28px;
  font-weight: 600;
  color: #fff;
  line-height: 40px;
  margin-right: 30px;
  margin-top: 1px;
  max-width: 360px;
  vertical-align: middle;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-block-start: 0.67em;
  margin-block-end: 0.67em;
  margin-inline-start: 0;
  margin-inline-end: 0;
  unicode-bidi: isolate;
}

.salary {
  font-size: 25px;
  font-family: kanzhun-Regular, kanzhun;
  color: #f26d49;
  line-height: 41px;
  height: auto;
  font-weight: 400;
  margin-left: 30px;
  position: relative;
  top: 1px;
  display: inline-block;
  vertical-align: middle;
}

.text-desc {
  font-size: 14px;
  color: #fff;
  line-height: 20px;
  margin-right: 20px;
}

.tag-container-new {
  line-height: 0;
  float: right;
  text-align: right;
  margin-top: 36px;
  box-sizing: border-box;
  width: 480px;
  position: relative;
}

.job-op {
  margin-top: 20px;
}

.job-tags {
  padding-top: 8px;
  overflow: hidden;
  zoom: 1;
  height: 32px;
  display: inline-block;
  color: #ffffff;
}

.job-tags span {
  height: 28px;
  line-height: 28px;
  background: rgba(255, 255, 255, .05);
  border-radius: 4px;
  font-size: 14px;
  color: #fff;
  white-space: nowrap;
  padding: 0 8px;
  border: none;
  margin-right: 10px;
  margin-bottom: 10px;
}

.job-box {
  margin-top: 12px;
  background: 0 0;
  height: auto;
}

.job-detail {
  border: none;
  padding-right: 0;
  margin-right: 300px;
  padding-bottom: 0;
  position: relative;
  min-height: 1000px;
}

.job-detail-section {
  position: relative;
  background: #fff;
  border-radius: 12px;
  padding: 5px 30px 20px 30px;
}

.detail-content-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}

.h3 {
  display: block;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0;
  margin-inline-end: 0;
  unicode-bidi: isolate;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: block;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0;
  margin-inline-end: 0;
  padding-inline-start: 0;
  unicode-bidi: isolate;
}

.job-keyword-list {
  overflow: hidden;
  margin-top: -8px;
}

li {
  list-style: none;
  margin: 0;
  display: list-item;
  text-align: -webkit-match-parent;
  unicode-bidi: isolate;
}

.job-keyword-list li {
  margin-top: 8px;
  margin-right: 8px;
  font-size: 14px;
  color: #666;
  line-height: 20px;
  float: left;
  padding: 4px 12px;
  white-space: nowrap;
  background: #f8f8f8;
  border-radius: 4px;
}

.job-sec-text {
  white-space: pre-wrap;
  word-break: break-all;
  margin-bottom: 20px;
  line-height: 28px;
  color: #333;
  font-size: 15px;
  text-align: justify;
  letter-spacing: 0;
}

.job-sider {
  margin-top: 0;
  padding-bottom: 0;
  float: right;
  width: 284px;
}

.sider-company {
  border-radius: 12px;
  padding-bottom: 20px;
  background: #fff;
  margin-bottom: 16px;
}

.sider-company .title {
  font-size: 16px;
  font-weight: 500;
  color: #222;
  line-height: 22px;
  padding: 12px 24px;
  background: linear-gradient(90deg, #f5fcfc 0, #fcfbfa 100%);
  margin-bottom: 0;
  border-radius: 12px 12px 0 0;
}

.company-info {
  display: flex;
  align-items: center;
  padding: 16px 24px;
  margin-bottom: 4px;
  font-size: 16px;
  font-weight: 500;
  color: #222;
  line-height: 22px;
  overflow: hidden;
}

.sider-company img {
  width: 48px;
  height: 48px;
  margin-right: 16px;
  border: 1px solid #f3f5fb;
  border-radius: 8px;
  float: left;
}

.sider-company p {
  padding: 0 24px;
  display: flex;
  align-items: center;
  font-size: 15px;
  color: #333;
  line-height: 21px;
  margin-bottom: 8px;
}

.job-boss-info {
  border-top: 1px solid #ededed;
  padding-top: 20px;
}

.detail-figure {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  border: 1px solid #f6fdfd;
  border-radius: 100%;
  float: left;
  position: static;
}

.detail-figure img {
  width: 56px;
  height: 56px;
  display: block;
  margin-right: 0;
  border-radius: 100%;
  vertical-align: top;
  border: none;
}

.job-boss-info .name {
  font-size: 20px;
  font-weight: 500;
  color: #222;
  line-height: 0;
  display: flex;
  align-items: center;
}

.boss-info-attr {
  font-size: 15px;
  color: #666;
  line-height: 21px;
  margin-top: 8px;
  display: flex;
  align-items: center;
}

.vdot {
  display: inline-block;
  padding: 0 10px 0 7px;
}

em {
  font-style: normal;
}

.job-detail-section + .job-detail-section {
  margin-top: 16px;
}

.job-detail-section h3 {
  font-size: 18px;
  font-weight: 600;
  color: #222;
  line-height: 25px;
  margin-bottom: 20px;
}

.job-sec-text {
  white-space: pre-wrap;
  word-break: break-all;
  margin-top: 20px;
  margin-bottom: 20px;
  line-height: 28px;
  color: #333;
  font-size: 15px;
  text-align: justify;
  letter-spacing: 0;
}

.text {
  max-height: none;
  overflow: hidden;
}

.detail-section-item {
  margin-bottom: 20px;
}

.level-list-box {
  background: #f8f8f8;
  border-radius: 12px;
  padding: 20px;
}

.level-list {
  height: auto;
  overflow: hidden;
  margin-top: 0;
}

.level-list li {
  float: left;
  padding-right: 5px;
  margin-top: 0;
  margin-bottom: 10px;
  box-sizing: border-box;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.level-list li span {
  display: block;
  font-size: 14px;
  color: #999;
  line-height: 20px;
  margin-bottom: 8px
}

.level-list span {
  display: inline-block;
  margin-right: 7px;
  color: #8d92a1;
}

.company-name {
  width: 400px;
}

.company-type {
  width: 400px;
}

.register-addr {
  width: 400px;
}

.company-user {
  width: 210px;
}

.company-scale {
  width: 210px;
}

.manage-state {
  width: 210px;
}

.company-fund {
  width: 150px;
}

.res-time {
  width: 150px;
}

.job-location {
  border-radius: 12px;
  border: 1px solid #ededed;
  box-shadow: none;
  overflow: hidden;
  margin-top: 20px;
  font-size: 13px;
  position: relative;
}

.location-address {
  font-size: 16px;
  color: #333333;
  line-height: 22px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: block;
  padding: 12px 20px 12px 30px;
  border-bottom: 1px #ecedef solid;
}
</style>