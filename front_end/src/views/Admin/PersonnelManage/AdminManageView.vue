<!--公告信息-->
<template>
  <div style="padding: 0 10px">
    <!--    搜索表单-->
    <div class="search">
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入用户id" size="small"
                v-model="params.uid"></el-input>
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入姓名" size="small"
                v-model="params.username"></el-input>
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入电话号码" size="small"
                v-model="params.phone"></el-input>
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入邮箱" size="small"
                v-model="params.email"></el-input>

      <el-button style="margin-left: 15px" plain type="primary" icon="el-icon-search" size="small" @click="load">搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>

    <!--    添加批量删除按钮-->
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
<!--      <el-button-->
<!--          class="add-button"-->
<!--          style="margin-left: 10px;margin-top: 20px"-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="small"-->
<!--          @click="delBatch"-->
<!--      >批量删除-->
<!--      </el-button>-->
    </div>

    <!--    公告表格-->
    <div class="table" style="margin-top: 20px">
      <el-table
          :data="tableData"
          :cell-style="{'text-align':'center'}"
          :header-cell-style="{'text-align':'center'}"
          :default-sort="{prop: 'id', order:'descending'}"
          ref="multipleTable"
          tooltip-effect="dark"
          @selection-change="handleSelectionChange">
        style="width: 100%"
        stripe>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            label="序号"
            prop="id"
            width=60
            sortable>
        </el-table-column>
        <el-table-column
            label="用户id"
            prop="uid">
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
            label="创建时间"
            prop="createtime">
        </el-table-column>
        <el-table-column
            label="最近更新时间"
            prop="updatetime">
        </el-table-column>

        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button
                size="small"
                :disabled="scope.row.id !== user.id"
                @click="handleEdit(scope.row)">编辑
            </el-button>
<!--            <el-button-->
<!--                size="small"-->
<!--                type="danger"-->
<!--                @click="del(scope.row.id)">删除-->
<!--            </el-button>-->
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--    弹框-->
    <el-dialog title="信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="uid" label="用户id">
          <el-input v-model="form.uid" disabled></el-input>
        </el-form-item>
        <el-form-item prop="username" label="姓名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话号码">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <span style="margin-left: 100px; font-size: small; color: brown">若要修改密码，输入初始密码</span>
        <el-form-item prop="password" label="密码">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <span style="margin-left: 100px; font-size: small; color: brown">注意：修改密码后系统会自动加密，并在此处展示加密密码</span>
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
  name: "AdminManageView",
  data() {
    return {
      tableData: [],
      total: 0,
      title: null,
      formVisible: false,
      form: {},
      ids: [],
      admin: JSON.parse(localStorage.getItem('admin') || '{}'),
      params: {
        pageNum: 1,
        pageSize: 10,
        id: '',
        uid: '',
        username: '',
        phone: '',
        email: '',
        createtime: '',
        updatetime: ''
      },
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
      //根据Cookie值中的信息，判断是否具有编辑当前管理员信息的权限
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
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },
    //加载页面
    load() {
      request.get('/admin/page', {
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
      this.params = {
        pageNum: 1,
        pageSize: 10,
        id: '',
        date: '',
        name: '',
        title: '',
        content: ''
      }
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
      this.request.post('/admin/save', this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.load()
          this.formVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    //删除单个
    del(id) {
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定要删除吗？', '确认删除', {type: "warning"}).then(() => {
        this.request.delete('/admin/delete/' + id).then(res => {
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
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(() => {
        this.request.delete('/admin/delete/batch', {data: this.ids}).then(res => {
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
.demo-table-expand {
  font-size: 0;
  margin-left: 20px;
  width: fit-content;
  height: fit-content;
}

.el-pagination {
  text-align: right;
}
</style>