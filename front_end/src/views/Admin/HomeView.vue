<!--管理员:系统首页-->
<!--问候语和公告列表-->

<template>
  <div>

    <!--    问候语-->
    <el-card class="box-card">
      <div class="text">
        {{ user.username }}，您好！欢迎使用缺一人才招聘网站后台管理系统~
      </div>
    </el-card>

    <!--公告列表-->
    <el-card class="box-card">
      <span style="font-size: 18px">公告列表</span>
      <el-timeline style="margin-top: 20px">
        <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :timestamp="activity.date">
          <template>
            <el-popover
                placement="top-start"
                width="500"
                trigger="hover"
                :content="activity.content">
              <el-button slot="reference" type="text" style="color: black">{{ activity.title }}</el-button>
            </el-popover>
          </template>
        </el-timeline-item>
      </el-timeline>

      <div>
        <el-pagination
            small
            layout="prev, pager, next"
            :current-page="params.pageNum"
            @current-change="handleCurrentChange"
            :page-size="params.pageSize"
            :total="total">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>
<script>

import request from "@/utils/request";
import Cookies from "js-cookie";
export default {
  name: "HomeView",
  data() {
    return {
      activities: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 3,
        id: '',
        date: '',
        name: '',
        title: '',
        content: ''
      },
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
    }
  },
  created() {
    this.load()
    request.get('/admin/' + this.user.id).then(res => {
      this.user = res.data
    })
  },
  methods: {
    load() {
      request.get('/announcement/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.activities = res.data.list
          this.total = res.data.total
        }

      })
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮，出发分页
      this.params.pageNum = pageNum
      this.load()
    },

  }
}
</script>

<style scoped>
.text {
  font-size: 18px;
}

.box-card {
  width: 800px;
  display: flex;
  align-items: center;
  justify-content: left;
  margin-top: 30px;
  margin-left: 30px;
}

</style>