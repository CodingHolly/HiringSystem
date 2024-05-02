<!--公告信息管理页面-->
<template>
  <div style="padding: 0 10px">

    <div class="operation">
      <!--    搜索表单-->
      <el-input style="width: 240px; margin-left: 10px" placeholder="请输入一级分类名称" size="small"
                v-model="params.category"></el-input>
      <el-input style="width: 240px; margin-left: 15px" placeholder="请输入二级分类名称" size="small"
                v-model="params.type"></el-input>
      <el-button style="margin-left: 15px" plain type="primary" size="small" @click="load">
        <i class="el-icon-search"></i>搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
      <!--    添加按钮-->
      <el-button
          plain
          class="add-button"
          style="margin-left: 410px"
          type="primary"
          icon="el-icon-edit"
          size="small"
          @click="handleAdd">
        新增
      </el-button>
    </div>

    <!--    分类数据表格-->
    <div class="table" style="margin-top: 20px">
      <el-table
          :data="tableData"
          :cell-style="{'text-align':'center'}"
          :header-cell-style="{'text-align':'center'}"
          ref="multipleTable"
          tooltip-effect="dark"
          @selection-change="handleSelectionChange"
          style="width: 100%; margin-top: 20px"
          stripe>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="分类描述">
                <br>
                <span>{{ props.row.description }}</span>
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
            label="一级分类"
            prop="category">
        </el-table-column>
        <el-table-column
            label="二级分类"
            prop="type">
        </el-table-column>
        <el-table-column
            label="分类图标">
          <template v-slot="scope">
            <div>
              <el-image style="width: 40px;height: 40px" v-if="scope.row.icon"
                        :src="scope.row.icon" :preview-src-list="[scope.row.icon]"></el-image>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="300">
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
    <el-dialog title="分类信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="category" label="一级分类">
          <el-input v-model="form.category" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="type" label="二级分类">
          <el-input v-model="form.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="description" label="分类描述">
          <el-input v-model="form.description" type="textarea" :rows="4"></el-input>
        </el-form-item>
        <el-form-item prop="icon" label="分类图标">
          <div>
            <el-image v-if="form.icon" :src="form.icon" style="width: 70px; height: 70px"></el-image>
            <el-upload
                :show-file-list="false"
                action="http://localhost:9090/api/file/upload"
                :headers="{token: user.token}"
                :file-list="fileList"
                list-type="picture"
                :on-success="(res,file,fileList) => handleIconUpload(res,file,fileList)">
              <el-button size="small" type="primary" plain>上传图标</el-button>
            </el-upload>
          </div>
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
  name: "PositionTypeView",
  data() {
    return {
      fileList:[],
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      tableData: [],
      total: 0,
      formVisible: false,
      form: {},
      ids: [],
      positionType: JSON.parse(localStorage.getItem('positionType') || '{}'),
      params: {
        pageNum: 1,
        pageSize: 8,
        id: '',
        type: '',
        category: '',
        description: '',
        icon: ''
      },
      rules: {
        category: [
          {required: true, category: '请输入一级分类名称', trigger: 'blur'},
        ],
      },
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
      request.get('/position_type/page', {
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
        category: '', // 一级分类
        type: '',  // 二级分类
        description: '',
        icon: '',
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
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          this.request.post('/position_type/save', this.form).then(res => {
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
        this.request.delete('/position_type/delete/' + id).then(res => {
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
    handleIconUpload(response, file, fileList) {
      this.form.icon = response.data
      this.fileList = fileList
    },
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