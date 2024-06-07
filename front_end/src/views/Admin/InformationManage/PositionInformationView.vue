<template>
  <div style="padding: 0 10px">
    <!--    搜索表单-->
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
      <el-select v-model="params.isReleased" placeholder="请选择是否发布" style="width: 200px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="opt in options"
            :key="opt.value"
            :label="opt.value"
            :value="opt.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 15px" plain type="primary" size="small" @click="load">
        <i class="el-icon-search"></i>搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>

    <!--    职位信息表格-->
    <div>
      <el-table :data="tableData"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
                :default-sort="{prop: 'date', order:'descending'}"
                ref="multipleTable"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>
        <el-table-column label="序号" prop="id" width=60></el-table-column>
        <el-table-column label="职位名称" prop="positionName"></el-table-column>
        <el-table-column label="公司名称" prop="companyName" width="100px"></el-table-column>
        <el-table-column label="一级分类" prop="category" width="100px"></el-table-column>
        <el-table-column label="二级分类" prop="type" width="100px"></el-table-column>
        <el-table-column label="负责人" prop="principal" width="100px"></el-table-column>
        <el-table-column label="发布者" prop="releasePerson" width="100px"></el-table-column>
        <el-table-column label="发布时间" prop="releaseTime" width="120px"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button
                size="small"
                type="info"
                @click="showDetails(scope.row)">详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--    职位详情弹框-->
    <el-dialog title="职位详情" :visible.sync="detailsFormVisible" width="40%" :close-on-click-modal="false"
               destroy-on-close>
      <el-form label-width="100px" :model="detailsForm">
        <el-form-item label="薪资" prop="salary" style="margin-left: 20px">
          <el-input v-model="detailsForm.salary" style="width: 200px" disabled></el-input>
        </el-form-item>
        <el-form-item label="工作地点" style="margin-left: 20px">
          <el-input v-model="detailsForm.workAddress" style="width: 400px" disabled></el-input>
        </el-form-item>
        <el-form-item label="职位描述" prop="profile" style="margin-left: 20px">
          <el-input v-model="detailsForm.profile" type="textarea" :rows="3" style="width: 400px" disabled></el-input>
        </el-form-item>
        <el-form-item label="职位要求" prop="requirement" style="margin-left: 20px">
          <el-input v-model="detailsForm.requirement" type="textarea" :rows="3" style="width: 400px" disabled></el-input>
        </el-form-item>
        <el-form-item label="福利" prop="welfare" style="margin-left: 20px">
          <el-input v-model="detailsForm.welfare" type="textarea" :rows="3" style="width: 400px" disabled></el-input>
        </el-form-item>
        <el-form-item label="最后编辑者" prop="lastEditor" style="margin-left: 20px">
          <el-input v-model="detailsForm.lastEditor" style="width: 200px" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否发布" prop="isReleased" style="margin-left: 20px">
          <el-input v-model="detailsForm.isReleased" style="width: 200px" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailsFormVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

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
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "PositionInformationView",
  data() {
    return {
      total: 0,
      //根据Cookie值中的信息，判断是否具有编辑当前企业管理员信息的权限
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      params: {},
      form: {},
      categories:[],
      types:[],
      detailsForm: {},
      detailsFormVisible: false,
      tableData: [],
      options:[{
        label:'未发布',
        value:'未发布'
      }, {
        label:'已发布',
        value:'已发布'
      }]
    }
  },
  created() {
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
      request.get('/position_info/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
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
    showDetails(row) {
      this.detailsFormVisible = true
      this.detailsForm = JSON.parse(JSON.stringify(row))
    },
  }
}
</script>

<style scoped>
.el-pagination {
  text-align: right;
}
</style>