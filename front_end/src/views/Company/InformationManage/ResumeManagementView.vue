<template>
  <div style="padding: 20px 10px">
    <div class="search">
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入职位名称" size="small"
                v-model="params.positionName"></el-input>
      <el-select v-model="params.category" placeholder="请选择一级分类" style="width: 200px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="category in categories"
            :key="category.category"
            :label="category.category"
            :value="category.category">
        </el-option>
      </el-select>
      <el-select v-model="params.type" placeholder="请选择二级分类" style="width: 200px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="type in types"
            :key="type.type"
            :label="type.type"
            :value="type.type">
        </el-option>
      </el-select>
      <el-button style="margin-left: 15px" plain type="primary" size="small" @click="load">
        <i class="el-icon-search"></i>搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
                :default-sort="{prop: 'date', order:'descending'}"
                ref="multipleTable"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>
        <el-table-column label="序号" prop="id" width=60></el-table-column>
        <el-table-column label="投递人" prop="username"></el-table-column>
        <el-table-column label="联系方式" prop="userPhone"></el-table-column>
        <el-table-column label="职位名称" prop="positionName"></el-table-column>
        <el-table-column label="投递时间" prop="submitTime"></el-table-column>
        <el-table-column label="二级分类" prop="type"></el-table-column>
        <el-table-column label="简历信息"></el-table-column>
        <el-table-column label="回复信息" prop="comment"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "ResumeManagement",
  data() {
    return {
      tableData: [],
      param: {},
      total: 0,
      //根据Cookie值中的信息，判断是否具有编辑当前企业管理员信息的权限
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      params: {},
      categories: [],
      types: [],
    }
  },
  created() {
    request.get('/company_admin/' + this.user.id).then(res => {
      this.user = res.data
    })
    request.get('/position_type/list_category').then(res => {
      this.categories = res.data
    })
    request.get('/position_type/list_type').then(res => {
      this.types = res.data
    })
    this.load()
  },
  methods: {
    load() {
      this.params.companyName = this.user.companyName
      request.get('/submit_resume/company_page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.params = {}
      this.load()
    },
    //点击分页按钮，出发分页
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
  },

}
</script>

<style scoped>
.el-pagination {
  text-align: right;
}
</style>