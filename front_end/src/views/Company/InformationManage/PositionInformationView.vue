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
      <el-select v-model="params.isReleased" placeholder="请选择是否发布" style="width: 150px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="opt in options"
            :key="opt.value"
            :label="opt.value"
            :value="opt.value">
        </el-option>
      </el-select>
      <el-select v-model="params.isFull" placeholder="请选择是否招满" style="width: 150px;margin-left: 15px"
                 size="small">
        <el-option
            v-for="opt in full"
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

    <!--    添加删除按钮-->
    <div class="operation">
      <el-button
          plain
          class="add-button"
          style="margin-left: 10px;margin-top: 20px"
          type="primary"
          icon="el-icon-edit"
          size="small"
          @click="handleAdd">
        新增
      </el-button>
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
            <el-button
                size="small"
                @click.native.prevent="handleEdit(scope.row)">编辑
            </el-button>
            <el-button
                size="small"
                type="primary"
                @click="release(scope.row.id)">发布
            </el-button>
            <el-button
                size="small"
                type="danger"
                @click="del(scope.row.id)">删除
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
          <el-input v-model="detailsForm.salary" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="工作地点" style="margin-left: 20px">
          <el-input v-model="detailsForm.workAddress" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="职位描述" prop="profile" style="margin-left: 20px">
          <el-input v-model="detailsForm.profile" type="textarea" :rows="3" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="职位要求" prop="requirement" style="margin-left: 20px">
          <el-input v-model="detailsForm.requirement" type="textarea" :rows="3" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="福利" prop="welfare" style="margin-left: 20px">
          <el-input v-model="detailsForm.welfare" type="textarea" :rows="3" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="最后编辑者" prop="lastEditor" style="margin-left: 20px">
          <el-input v-model="detailsForm.lastEditor" style="width: 200px" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否发布" prop="isReleased" style="margin-left: 20px">
          <el-input v-model="detailsForm.isReleased" style="width: 200px" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否招满" prop="isFull" style="margin-left: 20px">
          <el-input v-model="detailsForm.isFull" style="width: 200px" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailsFormVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!--    职位新增弹框-->
    <el-dialog title="职位信息" :visible.sync="addFormVisible" width="40%" :close-on-click-modal="false"
               destroy-on-close>
      <el-form label-width="100px" :model="form">
        <el-form-item label="职位名称" prop="positionName" style="margin-left: 20px">
          <el-input v-model="form.positionName" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="principal" style="margin-left: 20px">
          <el-select v-model="form.principal">
            <el-option
                v-for="admin in admins"
                :key="admin.username"
                :label="admin.username"
                :value="admin.username">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级分类" prop="category" style="margin-left: 20px">
          <el-select v-model="form.category">
            <el-option
                v-for="category in categories"
                :key="category.category"
                :label="category.category"
                :value="category.category">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" style="margin-left: 20px">
          <el-select v-model="form.type">
            <el-option
                v-for="type in types"
                :key="type.type"
                :label="type.type"
                :value="type.type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="薪资" prop="salary" style="margin-left: 20px">
          <el-input v-model="form.salary" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="工作地点" style="margin-left: 20px">
          <el-select v-model="form.workAddress" style="width: 400px">
            <el-option
                v-for="address in companyAddresses"
                :key="address.companyAddress"
                :label="address.companyAddress"
                :value="address.companyAddress">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位描述" prop="profile" style="margin-left: 20px">
          <el-input v-model="form.profile" type="textarea" :rows="3" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="职位要求" prop="requirement" style="margin-left: 20px">
          <el-input v-model="form.requirement" type="textarea" :rows="3" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="需要人数" prop="number" style="margin-left: 20px">
          <el-input v-model="form.number" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="福利" prop="welfare" style="margin-left: 20px">
          <el-input v-model="form.welfare" type="textarea" :rows="3" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="最后编辑者" prop="lastEditor" style="margin-left: 20px">
          <el-input v-model="form.lastEditor" style="width: 200px" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否发布" prop="isReleased" style="margin-left: 20px">
          <el-input v-model="form.isReleased" style="width: 200px" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
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
      categories: [],
      types: [],
      admins: [],
      companyInfo: {},
      companyAddresses: [],
      form: {},
      detailsForm: {},
      addFormVisible: false,
      detailsFormVisible: false,
      tableData: [],
      options: [{
        label: '未发布',
        value: '未发布'
      }, {
        label: '已发布',
        value: '已发布'
      }],
      full: [{
        label: '未招满',
        value: '未招满'
      }, {
        label: '已招满',
        value: '已招满'
      }]
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
    request.get('/company_admin/admin/' + this.user.companyName).then(res => {
      this.admins = res.data
    })
    this.load()
  },
  methods: {
    load() {
      this.params.companyName = this.user.companyName
      request.get('/position_info/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
      request.get('/company_info/' + this.user.companyName).then(res => {
        this.companyInfo = res.data
      })
      request.get('/company_info/address/' + this.user.companyName).then(res => {
        this.companyAddresses = res.data
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
    handleAdd() {
      this.form = {}
      this.addFormVisible = true
    },
    showDetails(row) {
      this.detailsFormVisible = true
      this.detailsForm = JSON.parse(JSON.stringify(row))
    },
    save() {
      this.form.companyName = this.user.companyName
      this.form.lastEditor = this.user.username
      this.request.post('/position_info/save', this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.load()
          this.addFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.addFormVisible = true
    },
    del(id) {
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定要删除吗？', '确认删除', {type: "warning"}).then(() => {
        this.request.delete('/position_info/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        }).catch(() => {
        })
      })
    },
    release(id) {
      this.$confirm('您确定要发布该职位吗？', '确认发布', {type: "warning"}).then(() => {
        this.request.delete('/position_info/release/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        }).catch(() => {
        })
      })
    }
  }
}
</script>

<style scoped>
.el-pagination {
  text-align: right;
}
</style>