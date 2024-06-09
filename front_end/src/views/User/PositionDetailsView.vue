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
            <div class="job-tags"><span>{{ positionData.welfare }}</span></div>
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
      positionData: {}
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
  padding: 0;
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
  text-align: right;
  margin-top: 36px;
  margin-left: 700px;
  box-sizing: border-box;
  width: 480px;
}

.job-op {
  margin-top: 20px;
}

.job-tags {
  padding-top: 8px;
  overflow: hidden;
  zoom: 1;
  color: #ffffff;
}
</style>