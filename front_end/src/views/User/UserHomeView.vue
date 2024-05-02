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
                <el-image v-if="item.icon" :src="item.icon" style="width: 20px; height: 20px;align-items: center"></el-image>
              </div>
              <div style="margin-left: 10px;font-size: 14px;">{{item.category}}</div>
            </div>
          </div>
          <div style="flex: 5"></div>
          <div style="flex: 3"></div>
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
      categoryData:[],
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
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
</style>