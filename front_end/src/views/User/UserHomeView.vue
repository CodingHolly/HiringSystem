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
              <div style="margin-left: 10px;font-size: 14px;">{{ item.category }}</div>
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
            <img src="@/assets/css/imgs/login_background3.jpg" alt="" style="width: 100%; margin-left: 5px;margin-top: 15px; border-radius: 5px">
          </div>
        </div>
        <div style="margin: 40px 0 0 15px; height: 40px;color: #355476;font-size: 20px; font-weight: bold">热门职位</div>
        <div style="margin-left: 20px;margin-top: 10px">
          <el-row>
            <el-col :span="5">
              <img src="@/assets/css/imgs/logo.png" alt="" style="width: 100%; height: 150px; border-radius: 10px;border: #cccccc 1px">
              <div style="margin-top: 10px;font-weight: 500; font-size: 16px; width: 180px; color: black; text-overflow: ellipsis;overflow: hidden; white-space: nowrap">
                山东省青岛市俩打开咖喱宫颈癌歌曲爱二哥
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="right"></div>
    </div>
  </div>
</template>
import request from "@/utils/request";
<script>
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
      ]
    }
  },
  created() {
    this.loadCategory()
  },
  mounted() {
    this.loadCategory()
  },
  methods: {
    loadCategory() {
      this.request.get('/position_type/list_category').then(res => {
        if (res.code === '200') {
          this.categoryData = res.data
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
.el-col-5 {
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}
</style>