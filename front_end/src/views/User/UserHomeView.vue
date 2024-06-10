<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C6E2FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width:70%;background-color: white;height: 1000px">
        <div style="color: #355476; margin: 15px 0 15px 18px;font-size: 18px; font-weight: bold">招聘市场</div>
        <div style="display: flex; margin: 0 25px">
          <div style="flex: 2">
            <div style="display: flex; color: black; margin: 14px 0" v-for="(item, index) in categoryData" :key="index">
              <div>
                <el-image v-if="item.icon" :src="item.icon"
                          style="width: 20px; height: 20px;align-items: center"></el-image>
              </div>
              <div style="margin-left: 10px;font-size: 14px;">
                <a href="#" @click="navTo('/user/position_category?id='+item.id)">{{ item.category }}</a>
<!--                {{ item.category }}-->
              </div>
            </div>
          </div>
          <div style="flex: 5; margin-top: 15px">
            <div>
              <el-carousel height="300px" style="border-radius: 10px">
                <el-carousel-item v-for="item in carousel_top" :key="item.key">
                  <img :src="item" alt="" style="width: 100%;height: 300px;border-radius: 10px">
                </el-carousel-item>
              </el-carousel>
            </div>
            <div style="margin-top: 5px;display: flex">
              <div style="flex: 1; margin-right: 2px">
                <el-carousel height="200px" style="border-radius: 10px">
                  <el-carousel-item v-for="item in carousel_left" :key="item.key">
                    <img :src="item" alt="" style="width: 100%;height: 200px;border-radius: 10px">
                  </el-carousel-item>
                </el-carousel>
              </div>
              <div style="flex: 1;margin-left: 2px">
                <el-carousel height="200px" style="border-radius: 10px">
                  <el-carousel-item v-for="item in carousel_right" :key="item.key">
                    <img :src="item" alt="" style="width: 100%;height: 200px;border-radius: 10px">
                  </el-carousel-item>
                </el-carousel>
              </div>
            </div>
          </div>
          <div style="flex: 3">
            <img src="@/assets/css/imgs/login_background3.jpg" alt=""
                 style="width: 100%; margin-left: 5px;margin-top: 15px; border-radius: 5px">
          </div>
        </div>
        <div style="margin: 40px 0 0 15px; height: 40px;color: #355476;font-size: 20px; font-weight: bold">热门职位
        </div>
        <div style="margin: 10px 50px 20px 30px">
          <el-row>
            <el-col :span="2" v-for="(item, index) in positionData" :key="index">
              <el-card shadow="hover" :body-style="{height:'100%', width:'100%', boxSizing:'border-box'}">
                <div class="header">
                    <span class="name" style="flex: 5"
                          @click="navTo('/user/position_details?id=' + item.id)">{{ item.positionName }}</span>
                  <span class="salary" style="flex: 5">{{ item.salary }}</span>
                </div>
                <div class="job-text">
                  <span>{{ item.city }}</span>
                  <span>{{ item.experience }}</span>
                  <span>{{ item.degree }}</span>
                </div>
                <div class="bottom">
                  <span class="companyName" style="flex: 5;margin-top: 5px">{{ item.companyName }}</span>
                  <span class="position-info" style="flex: 5;margin-top: 5px">{{ item.jobStatus }}</span>
                </div>
              </el-card>
            </el-col>
          </el-row>
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
  name: "UserHomeView",
  data() {
    return {
      categoryData: [],
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      carousel_top: [
        require('@/assets/css/imgs/userHome1.jpg'),
        require('@/assets/css/imgs/userHome2.jpg'),
        require('@/assets/css/imgs/userHome3.jpg'),
        require('@/assets/css/imgs/userHome4.jpg')
      ],
      carousel_left: [
        require('@/assets/css/imgs/userHome5.jpg'),
        require('@/assets/css/imgs/userHome6.jpg'),
        require('@/assets/css/imgs/userHome7.jpg'),
      ],
      carousel_right: [
        require('@/assets/css/imgs/userHome8.jpg'),
        require('@/assets/css/imgs/userHome9.jpg'),
        require('@/assets/css/imgs/userHome10.jpg'),
      ],
      positionData: [],
    }
  },
  created() {
    this.loadCategory()
  },
  mounted() {
    this.loadCategory()
    this.loadPosition()
  },
  methods: {
    loadCategory() {
      request.get('/position_type/list_category').then(res => {
        if (res.code === '200') {
          this.categoryData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadPosition() {
      request.get('/position_info/selectTop6').then(res => {
        if (res.code === '200') {
          this.positionData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    navTo(url) {
      location.href = url
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

.header {
  display: flex;
  align-items: center;
  width: 100%;
  padding-top: -2px;
}

.el-col-2 {
  width: 50%;
  padding: 20px 20px;
}

.salary {
  font-size: 16px;
  font-weight: 500;
  color: #fe574a;
  line-height: 22px;
  text-align: right;
  max-width: none;
}

.bottom {
  display: flex;
  font-size: 15px;
  color: #666;
  padding-right: 0;
  height: 22px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-top: 12px;
}

.position-info {
  text-align: right;
  color: #8d92a1;
  font-size: 13px;
}

.job-text span {
  margin-right: 10px;
  display: inline-block;
  font-size: 13px;
  font-weight: 400;
  color: #666;
  line-height: 20px;
  padding: 2px 10px;
  border-radius: 4px;
  background: #f8f8f8;
}

.job-text {
  margin-top: 15px;
  white-space: normal;
  padding-right: 0;
  height: 25px;
  line-height: 25px;
  overflow: hidden;
  word-break: break-all;
  max-width: none;
}
</style>