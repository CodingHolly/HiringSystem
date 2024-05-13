<!--用户信息-->
<template>
  <div style="padding: 20px 5px">
    <!--    搜索表单-->
    <div class="search">
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入姓名" size="small"
                v-model="params.username"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入手机号" size="small"
                v-model="params.phone"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入邮箱" size="small"
                v-model="params.email"></el-input>
      <el-select style="width: 150px;margin-left: 15px" placeholder="请选择性别" size="small" v-model="params.sex">
        <el-option v-for="item in sex_option" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-select style="width: 180px;margin-left: 15px" placeholder="请选择用户类型" size="small"
                 v-model="params.isVip">
        <el-option v-for="item in vip_option" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <el-button style="margin-left: 15px" plain type="primary" icon="el-icon-search" size="small" @click="load">搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>

    <div class="table" style="margin-top: 20px">
      <el-table :data="tableData"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
                :default-sort="{prop: 'id', order:'descending'}"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>
        <el-table-column label="序号" prop="id" width=75 sortable></el-table-column>
        <el-table-column label="头像" width=80>
          <template v-slot="scope">
            <div>
              <el-image style="width: 40px;height: 56px" v-if="scope.row.img" :src="scope.row.img"
                        :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="性别" prop="sex" width=80></el-table-column>
        <el-table-column label="电话号码" prop="phone"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="求职状态" prop="jobSearchStatus"></el-table-column>
        <el-table-column label="用户类型" prop="isVip"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="small" @click="handleUpgrade(scope.row)" style="margin-right: 10px">升级</el-button>
          </template>
        </el-table-column>

      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserManageView",
  data() {
    return {
      params: {},
      sex_option: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
      vip_option: [{
        value: '普通用户',
        label: '普通用户'
      }, {
        value: 'VIP用户',
        label: 'VIP用户'
      }],
      tableData: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/user/page', {
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
    handleUpgrade(id) {
      this.$confirm('您确定要升级该用户为VIP吗？', '确认升级', {type: "warning"}).then(() => {
        request.post('/admin/upgrade/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      })
    },
  },
}
</script>

<style scoped>

</style>