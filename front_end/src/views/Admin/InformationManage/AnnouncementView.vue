<!--公告信息管理页面-->
<template>
  <div style="padding: 0 10px">
    <!--    搜索表单-->
    <div class="search">
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入标题" size="small"
                v-model="params.title"></el-input>
      <el-button style="margin-left: 15px" plain type="primary" size="small" @click="load">
        <i class="el-icon-search"></i>搜索
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
      <el-button
          class="add-button"
          style="margin-left: 10px;margin-top: 20px"
          type="danger"
          plain
          icon="el-icon-delete"
          size="small"
          @click="delBatch"
      >批量删除
      </el-button>
    </div>
    <!--    公告表格-->
    <div class="table">
      <el-table
          :data="tableData"
          :date="params.date"
          :cell-style="{'text-align':'center'}"
          :header-cell-style="{'text-align':'center'}"
          :default-sort="{prop: 'date', order:'descending'}"
          ref="multipleTable"
          tooltip-effect="dark"
          @selection-change="handleSelectionChange"
          style="width: 100%"
          stripe>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="内容">
                <br>
                <span>{{ props.row.content }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
            label="序号"
            prop="id"
            width=60>
        </el-table-column>
        <el-table-column
            label="发布时间"
            prop="date"
            sortable>
        </el-table-column>
        <el-table-column
            label="标题"
            prop="title">
        </el-table-column>
        <el-table-column
            label="发布者"
            prop="name"
            width=120>
        </el-table-column>

        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button
                size="small"
                @click.native.prevent="handleEdit(scope.row)"
                style="margin-right: 10px">编辑
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


    <!--    弹框-->
    <el-dialog title="信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="发布者姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item prop="title" label="公告标题">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="公告内容">
          <el-input type="textarea" :rows="4" v-model="form.content" autocomplete="off"></el-input>
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
  name: "AnnouncementView",
  data() {
    return {
      tableData: [],
      total: 0,
      title: null,
      formVisible: false,
      form: {},
      ids: [],
      announcement: JSON.parse(localStorage.getItem('announcement') || '{}'),
      params: {
        pageNum: 1,
        pageSize: 10,
        id: '',
        date: '',
        name: '',
        title: '',
        content: ''
      },
      rules: {
        name: [
          {required: true, name: '请输入发布者姓名', trigger: 'blur'},
        ],
        title: [
          {required: true, title: '请输入公告名称', trigger: 'blur'},
        ],
        content: [
          {required: true, content: '请输入公告内容', trigger: 'blur'},
        ]
      },
      //根据Cookie值中的信息，更新最新公告发布者名字
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
      request.get('/announcement/page', {
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
      //通过Cookie修改发布人名字
      this.form.name = this.user.username
    },
    //点击保存按钮，触发新增或更新
    save() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          this.request.post('/announcement/save', this.form).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功')
              this.load()
              this.formVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    //删除单个
    del(id) {
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定要删除吗？', '确认删除', {type: "warning"}).then(() => {
        this.request.delete('/announcement/delete/' + id).then(res => {
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
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(() => {
        this.request.delete('/announcement/delete/batch', {data: this.ids}).then(res => {
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