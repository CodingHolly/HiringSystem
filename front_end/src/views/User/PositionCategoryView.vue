<template>
  <div class="page-job-wrapper" style="padding-top: 0">
    <div class="job-search-wrapper">
      <el-input class="search-input-box" v-model="searchCategory" placeholder="搜 索 职 位"></el-input>
      <el-button class="search-btn">搜索</el-button>
    </div>
    <div class="page-job-inner">
      <div class="page-job-content clearfix">
        <div class="job-list-wrapper">
          <div class="subscribe-weixin-wrapper">
            <div class="subscribe-weixin-inner">
              <img src="@/assets/css/imgs/login_header.jpg">
              <h3>新职位发布时通知我</h3>
              <p>订阅缺一人才公众号，新岗位上线实时通知，求职快人一步</p>
            </div>
          </div>
          <div class="search-job-result">
            <ul class="job-list-box">
              <li v-for="(item,index) in positionInfo" :key="index" class="job-card-wrapper">
                <div class="job-card-body clearfix">
                  <div class="job-card-left">
                    <div class="job-title clearfix">
                      <span class="job-name">{{ item.positionName }}</span>
                      <span class="job-area-wrapper">
                        <span class="job-area">{{ item.city }}</span>
                      </span>
                    </div>
                    <div class="job-info clearfix">
                      <span class="salary">{{ item.salary }}</span>
                      <span class="experience">{{ item.experience }}</span>
                      <span class="degree">{{ item.degree }}</span>
                    </div>
                  </div>
                  <div class="job-card-right">
                    <div class="company-logo">
                      <img :src=item.companyLogo alt="">
                    </div>
                    <div class="company-info">
                      <h3 class="company-name">{{ item.companyName }}</h3>
                    </div>
                  </div>

                </div>
                <div class="job-card-footer clearfix">
                  <span class="tag-list">{{ item.keywordList }}</span>
                  <div class="welfare">{{item.welfare}}</div>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div class="job-side-wrapper">
          <div class="user-info-wrapper">
            <div class="title">个人基本信息</div>
            <div class="info-card-body">
              <div class="user-img">
                <img :src=user.img alt="">
              </div>
              <p>
                <i class="el-icon-user" style="margin-right: 5px"></i>
                <span>{{ user.username }}</span>
              </p>
              <p>
                <i class="el-icon-office-building" style="margin-right: 5px"></i>
                <span>{{ user.birthplace }}</span>
              </p>
              <p>
                <i class="el-icon-location-information"
                   style="margin-right: 5px;"></i>
                <span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                {{ user.registerAddress }}</span>
              </p>
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
  name: "PositionCategoryView",
  data() {
    let categoryId = this.$route.query.id
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      categoryId: categoryId,
      searchCategory: '',
      positionInfo: [],
    }
  },
  created() {
    this.loadPosition()
  },
  methods: {
    loadPosition() {
      request.get('/position_info/selectByCategoryId?id=' + this.categoryId).then(res => {
        if (res.code === '200') {
          this.positionInfo = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

.job-search-wrapper {
  background: #fff;
  width: 1184px;
  height: 95px;
  margin: 20px auto 0;
  border-radius: 12px;
  padding: 24px 16px 24px 16px;
}

.search-input-box {
  position: relative;
  float: left;
  width: 800px;
  margin-left: 50px;
  margin-right: 30px;
  border-radius: 10px;
}

>>> .el-input__inner {
  height: 51.6px;
  font-size: 18px;
  text-align: left;
}

.search-btn {
  float: left;
  display: block;
  font-weight: 500;
  background-color: #C6E2FF;
  color: #666666;
  line-height: 28px;
  width: 116px;
  font-size: 20px;
  text-align: center;
  padding: 11px 0;
}

.page-job-inner {
  margin-top: 20px;
}

.page-job-content {
  width: 1184px;
  margin: 0 auto;
}

.job-list-wrapper {
  float: left;
  width: 884px;
}

.subscribe-weixin-wrapper {
  position: relative;
  width: 884px;
  padding: 30px 30px;
  background: #fff;
  border-radius: 12px;
  margin-bottom: 16px;
  overflow: hidden;
}

.subscribe-weixin-inner {
  position: relative;
  padding-left: 68px;
}

.subscribe-weixin-inner img {
  position: absolute;
  width: 56px;
  top: 0;
  left: 0;
  z-index: 1;
}

.subscribe-weixin-inner h3 {
  font-size: 18px;
  font-weight: 500;
  color: #222;
  line-height: 25px;
  margin-bottom: 12px;
}

.subscribe-weixin-inner p {
  font-size: 14px;
  font-weight: 400;
  color: #666;
  line-height: 20px;
  max-width: 650px;
  word-break: break-all;
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

li {
  list-style: none;
}

.search-job-result {
  margin-bottom: 44px;
}

.job-card-wrapper {
  position: relative;
  width: 884px;
  height: 136px;
  background: #ffffff;
  border-radius: 12px;
  transition: all .2s linear;
  cursor: pointer;
}

.job-card-left {
  float: left;
  width: 484px;
  padding: 16px 0 16px 24px;
  height: 88px;
}

.job-title {
  display: inline-block;
  vertical-align: middle;
  font-size: 16px;
  font-weight: 500;
  color: #222;
  line-height: 22px;
  transition: all .2s linear;
  max-width: 460px;
}

.job-name {
  float: left;
  max-width: 172px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.job-area-wrapper {
  float: left;
  margin-left: 12px;
  line-height: 22px;
}

.job-area-wrapper:before {
  content: "[";
  float: left;
  font-size: 16px;
}

.job-area-wrapper:after {
  content: "]";
  float: left;
  font-size: 16px;
}

.job-area {
  float: left;
  max-width: 214px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.job-info {
  margin-top: 12px;
  height: 22px;
  overflow: hidden;
}

.salary {
  font-size: 16px;
  font-weight: 500;
  color: #fe574a;
  line-height: 22px;
  float: left;
}

.experience {
  float: left;
  margin-left: 12px;
  max-width: 172px;
  height: 22px;
  overflow: hidden;
  padding: 2px 8px;
  background: #f8f8f8;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 400;
  color: #666;
  line-height: 18px;
}

.degree {
  float: left;
  margin-left: 12px;
  max-width: 172px;
  height: 22px;
  overflow: hidden;
  padding: 2px 8px;
  background: #f8f8f8;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 400;
  color: #666;
  line-height: 18px;
}

.clearfix:after {
  display: block;
  visibility: hidden;
  clear: both;
  content: "";
  overflow: hidden;
}

.job-card-right {
  float: left;
  width: 400px;
  padding: 16px 24px 16px 10px;
}

.company-logo {
  float: left;
  width: 56px;
  height: 56px;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #f8f8f8;
}

.company-logo img {
  width: 100%;
  height: 100%;
  border: none;
}

.company-info {
  margin-left: 12px;
  float: left;
}

.company-name {
  font-size: 16px;
  font-weight: 500;
  line-height: 22px;
  height: 22px;
  display: inline-block;
  color: #222;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 196px;
  vertical-align: middle;
  transition: all .2s linear;
}

.job-card-footer {
  padding: 15px 24px;
  background: linear-gradient(90deg, #f5fcfc, #fcfbfa);
  border-radius: 0 0 12px 12px;
  cursor: pointer;
}

.tag-list {
  float: left;
  width: 390px;
  overflow: hidden;
  margin-right: 84px;
  margin-left: -8px;
  height: 18px;
  display: inline-block;
  position: relative;
  padding: 0 8px;
  font-size: 13px;
  font-weight: 400;
  color: #666;
  line-height: 18px;
  white-space: nowrap;
}
.welfare{
  float: left;
  width: 362px;
  font-size: 13px;
  font-weight: 400;
  color: #666;
  line-height: 18px;
  word-break: break-word;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.job-side-wrapper{
  float: right;
  width: 284px;
}
.user-info-wrapper{
  position: relative;
  width: 284px;
  background: linear-gradient(180deg, #00bebd, #00a6a7) hsla(0, 0%, 100%, .21);
  border-radius: 14px;
  margin-bottom: 16px;
  padding-left: 12px;
  padding-right: 12px;
}
.user-info-wrapper .title {
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  color: #fff;
  line-height: 25px;
  padding-top: 20px;
  padding-bottom: 2px;
}
.info-card-body{
  position: relative;
  z-index: 1;
  background: linear-gradient(180deg, rgba(245, 252, 252, .8), #fcfbfa);
  border-radius: 12px 12px 0 0;
  border: 1px solid #f7fdfd;
  backdrop-filter: blur(7px);
  padding: 20px 18px;
}

.user-img img {
  width: 48px;
  height: 60px;
  margin-right: 16px;
  border: 1px solid #f3f5fb;
  border-radius: 8px;
  float: left;
}

.info-card-body p {
  padding: 0 24px;
  display: flex;
  align-items: center;
  font-size: 15px;
  color: #333;
  line-height: 21px;
  margin-bottom: 8px;
}
</style>