<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C6E2FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width:70%;background-color: white;height: 2000px">
        <div style="display: flex; justify-content: center">
          <div style="margin-top: 40px">
            <el-table :data="tableData"
                      :cell-style="{'text-align':'center'}"
                      :header-cell-style="{'text-align':'center'}"
                      :default-sort="{prop: 'date', order:'descending'}"
                      ref="multipleTable"
                      tooltip-effect="dark"
                      style="width: 100%"
                      stripe>
              <el-table-column label="序号" prop="id"></el-table-column>
              <el-table-column label="职位名称" prop="positionName" width="300px"></el-table-column>
              <el-table-column label="企业名称" prop="companyName" width="100px"></el-table-column>
              <el-table-column label="投递时间" prop="submitTime" width="200px"></el-table-column>
              <el-table-column label="回复信息">
                <template slot-scope="scope">
                  <el-button size="small" style="color: #333333" type="text"
                             @click="showComment(scope.row.comment)">查看
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--    分页-->
            <div style="margin-top: 20px ">
              <el-pagination
                  class="el-pagination"
                  background
                  :current-page="params.pageNum"
                  @current-change="handleCurrentChange"
                  :page-size="params.pageSize"
                  layout="prev,pager,next"
                  :total="total">
              </el-pagination>
            </div>
            <el-dialog title="回复信息" :visible.sync="commentFormVisible" width="40%" :close-on-click-modal="false"
                       destroy-on-close>
              <div style="margin-left: 10px; margin-bottom: 10px; font-size: 14px"> {{ this.commentDetails }}</div>
              <div slot="footer" class="dialog-footer">
                <el-button @click="commentFormVisible = false">关 闭</el-button>
              </div>
            </el-dialog>
          </div>
        </div>
      </div>
      <div class="right"></div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "SubmitMessageView",
  data() {
    return {
      tableData: [],
      params: {},
      total: 0,
      commentFormVisible: false,
      commentDetails: {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/submit_resume/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    //点击分页按钮，出发分页
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    showComment(details) {
      this.commentFormVisible = true
      this.commentDetails = details
      console.log(this.commentDetails)
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

.el-pagination {
  text-align: right;
  margin-top: 50px;
}
</style>