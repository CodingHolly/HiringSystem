<!--  企业用户管理页面-->
<template>
  <div style="padding: 30px 10px">
    <!--    搜索表单-->
    <div class="search">
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入姓名" size="small"
                v-model="params.username"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入手机号" size="small"
                v-model="params.phone"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入邮箱" size="small"
                v-model="params.email"></el-input>
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
    <!--    新增按钮-->
    <div class="operation">
      <el-button
          plain
          class="add-button"
          style="margin-left: 10px;margin-top: 20px"
          type="primary"
          icon="el-icon-edit"
          size="small"
          @click="handleAddAdmin">
        新增
      </el-button>
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
            width=80
            sortable>
        </el-table-column>
        <el-table-column
            label="姓名"
            prop="username">
        </el-table-column>
        <el-table-column
            label="头像">
          <template v-slot="scope">
            <div>
              <el-image style="width: 35px;height: 35px" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
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
            label="职位一级分类"
            prop="positionCategory">
        </el-table-column>
        <el-table-column
            label="职位二级分类"
            prop="positionType">
        </el-table-column>
        <!--        :disabled 仅限本人编辑和删除-->
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button
                size="small"
                :disabled="scope.row.id !== user.id"
                @click="handleEdit(scope.row)">编辑
            </el-button>
            <el-button
                size="small"
                type="danger"
                plain
                :disabled="scope.row.id !== user.id"
                @click="del(scope.row.id)">注销
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--    弹框-->
    <el-dialog title="信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" :model="form" :rules="rules" ref="formRef" style="margin-left: 50px">
        <el-form-item prop="role" label="角色标识">
          <el-input v-model="form.role" disabled style="width: auto"></el-input>
        </el-form-item>
        <el-form-item prop="username" label="姓名">
          <el-input v-model="form.username" style="width: auto"></el-input>
        </el-form-item>
        <el-form-item prop="avatar" label="头像">
          <div>
            <el-image v-if="form.avatar" :src="form.avatar" style="width: 60px; height: 60px"></el-image>
            <el-upload
                :show-file-list="false"
                action="http://localhost:9090/api/file/upload"
                :headers="{token: companyAdmin.token}"
                :file-list="fileList"
                list-type="picture"
                :on-success="(res,file,fileList) => handleIconUpload(res,file,fileList)">
              <el-button size="small" type="primary" plain>上传头像</el-button>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item prop="phone" label="电话号码">
          <el-input v-model="form.phone" autocomplete="off" style="width: auto"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="form.email" autocomplete="off" style="width: auto"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="form.password" style="width: auto"></el-input>
        </el-form-item>
        <el-form-item prop="category" label="一级分类">
          <el-select v-model="form.positionCategory" placeholder="请选择一级分类" style="width: 200px">
            <el-option
                v-for="category in categories"
                :key="category.category"
                :label="category.category"
                :value="category.category">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="type" label="二级分类">
          <el-select v-model="form.positionType" placeholder="请选择二级分类" style="width: 200px">
            <el-option
                v-for="type in types"
                :key="type.type"
                :label="type.type"
                :value="type.type">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
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
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "CompanyManageView",
  data() {
    return {
      tableData: [],
      fileList:[],
      total: 0,
      title: null,
      formVisible: false, //弹框是否可见
      form: {},
      companyAdmin: JSON.parse(localStorage.getItem('companyAdmin') || '{}'),
      params: {},
      //表单校验规则
      rules: {
        username: [
          {required: true, username: '请输入管理员姓名', trigger: 'blur'},
        ],
        phone: [
          {required: true, phone: '请输入手机号', trigger: 'blur'}
        ],
        email: [
          {required: true, email: '请输入邮箱地址', trigger: 'blur'},
        ],
      },
      //根据Cookie值中的信息，判断是否具有编辑当前企业管理员信息的权限
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      categories: [],
      types: [],
    }
  },
  created() {
    request.get('/company_admin/' + this.user.id).then(res => {
      this.user = res.data
    })
    this.load()
    request.get('/position_type/list_category').then(res => {
      this.categories = res.data
    })
    request.get('/position_type/list_type').then(res => {
      this.types = res.data
    })
    this.load()
  },
  methods: {
    //加载页面
    load() {
      this.params.companyName = this.user.companyName
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
      this.params.companyName = this.user.companyName
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
    //点击保存按钮，触发新增或更新
    save() {
      this.form.companyName = this.user.companyName
      this.request.post('/company_admin/save', this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.load()
          this.formVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    //注销账号
    del(id) {
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定要注销账号吗？', '确认注销', {type: "warning"}).then(() => {
        this.request.delete('/company_admin/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        }).catch(() => {
          console.log('失败')
        })
      })
    },
    //点击新增按钮，弹出弹框
    handleAddAdmin() {
      this.form = {}
      this.formVisible = true
    },
    //上传头像
    handleIconUpload(response, file, fileList) {
      this.form.avatar = response.data
      this.fileList = fileList
    },
  }
}
</script>

<style scoped>

.el-pagination {
  text-align: right;
}
</style>