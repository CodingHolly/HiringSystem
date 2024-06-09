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
                       style="width: 20px;height: 20px;margin-right: 6px;vertical-align: middle;">{{
                    positionData.city
                  }}</span>
                <span class="text-desc text-experience">
                  <img src="@/assets/css/imgs/experience.png" alt=""
                       style="width: 20px;height: 20px;margin-right: 6px; vertical-align: middle;">{{
                    positionData.experience
                  }}</span>
                <span class="text-desc text-degree">
                  <img src="@/assets/css/imgs/degree.png" alt=""
                       style="width: 20px;height: 20px;margin-right: 6px; vertical-align: middle;">{{
                    positionData.degree
                  }}</span>
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
              <span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis">{{ companyData.registerAddress }}</span>
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
        this.companyAddresses = res.data
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
  padding: 0 30px 20px 30px;
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
  padding-inline-start: 0px;
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
  margin-top: 20px;
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

.side-img {
  border-radius: 12px;
  margin-bottom: 16px !important;
}
</style>