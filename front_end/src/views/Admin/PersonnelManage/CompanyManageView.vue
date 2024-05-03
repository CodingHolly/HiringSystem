<!--  企业用户管理页面-->
<template>
  <div style="padding: 30px 5px">
    <!--    搜索表单-->
    <div class="search">
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入姓名" size="small"
                v-model="params.username"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入手机号" size="small"
                v-model="params.phone"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入企业名称" size="small"
                v-model="params.companyName"></el-input>
      <el-select v-model="params.positionCategory" placeholder="请选择一级分类" style="width: 200px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="category in categories"
            :key="category.category"
            :label="category.category"
            :value="category.category">
        </el-option>
      </el-select>
      <el-select v-model="params.positionType" placeholder="请选择二级分类" style="width: 200px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="type in types"
            :key="type.type"
            :label="type.type"
            :value="type.type">
        </el-option>
      </el-select>
      <el-button style="margin-left: 15px" plain type="primary" icon="el-icon-search" size="small" @click="load">搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>

    <!--    企业用户成员表格-->
    <div class="table" style="margin-top: 20px">
      <el-table
          :data="tableData"
          :cell-style="{'text-align':'center'}"
          :header-cell-style="{'text-align':'center'}"
          :default-sort="{prop: 'id', order:'descending'}"
          tooltip-effect="dark"
          style="width: 100%"
          stripe>
        <el-table-column
            label="序号"
            prop="id"
            width=60
            sortable>
        </el-table-column>
        <el-table-column
            label="姓名"
            prop="username">
        </el-table-column>
        <el-table-column
            label="电话号码"
            prop="phone">
        </el-table-column>
        <el-table-column
            label="邮箱"
            prop="email">
        </el-table-column>
        <el-table-column
            label="企业名称"
            prop="companyName">
        </el-table-column>
        <el-table-column
            label="职位一级分类"
            prop="positionCategory">
        </el-table-column>
        <el-table-column
            label="职位二级分类"
            prop="positionType">
        </el-table-column>
      </el-table>
    </div>

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
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "CompanyManageView",
  data() {
    return {
      tableData: [],
      total: 0,
      title: null,
      form: {},
      admin: JSON.parse(localStorage.getItem('admin') || '{}'),
      params: {},
      //根据Cookie值中的信息，判断是否具有编辑当前企业管理员信息的权限
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      categories: [],
      types: [],
    }
  },
  created() {
    request.get('/admin/' + this.user.id).then(res => {
      this.user = res.data
    })
    this.load()
    request.get('/position_type/list_category').then(res => {
      this.categories = res.data
    })
    request.get('/position_type/list_type').then(res => {
      this.types = res.data
    })

  },
  methods: {
    //加载页面
    load() {
      request.get('/company_admin/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    //重置
    reset() {
      this.params = {}
      this.load()
    },
    //点击分页按钮，出发分页
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    //点击新增按钮，弹出弹框
    handleAdd() {
      this.form = {}
      this.formVisible = true
    },
    //点击编辑按钮，弹出弹框
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
    },
  }
}
</script>

<style scoped>

.el-pagination {
  text-align: right;
}
</style>