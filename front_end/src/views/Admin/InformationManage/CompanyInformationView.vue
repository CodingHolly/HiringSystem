<!--企业信息-->
<template>
  <div style="padding: 0 10px">
    <!--    搜索表单-->
    <div class="search">
      <el-input style="width: 200px; margin-left: 10px" placeholder="请输入公司名称" size="small"
                v-model="params.companyName"></el-input>
      <el-input style="width: 200px; margin-left: 15px" placeholder="请输入公司地址" size="small"
                v-model="params.companyAddress"></el-input>
      <el-select v-model="params.managementStatus" placeholder="请选择经营状态"
                 style="width: 150px; margin-left: 15px"
                 size="small"
                 @change="handleChangeStatus">
        <el-option
            v-for="item in statuses"
            :key="item.id"
            :label="item.label"
            :value="item.id">
        </el-option>
      </el-select>

      <el-button style="margin-left: 15px" plain type="primary" icon="el-icon-search" size="small" @click="load">搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>
    <!--    企业信息表格-->
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
            label="id"
            prop="id"
            width=60
            sortable>
        </el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="公司描述">
                <span>{{ props.row.companyProfile }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
            label="企业名称"
            prop="companyName">
        </el-table-column>
        <el-table-column
            label="法定代表人"
            prop="legalRepresentative"
            width=120>
        </el-table-column>
        <el-table-column
            label="成立时间"
            prop="foundingTime">
        </el-table-column>
        <el-table-column
            label="注册地址"
            prop="registerAddress">
        </el-table-column>
        <el-table-column
            label="公司地址"
            prop="companyAddress.companyAddress">
          <template slot-scope="scope">
            <el-button plain size="small" @click="showCompanyAddress(scope.row.companyName)">详情</el-button>
          </template>
        </el-table-column>
        <el-table-column
            label="企业类别"
            prop="companyCategory">
        </el-table-column>
        <el-table-column
            label="注册资金"
            prop="registeredCapital">
        </el-table-column>
      </el-table>
    </div>

    <!--    公司地址弹框-->
    <el-dialog title="公司地址" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <div v-for="(item, index) in companyAddresses" :key="index">
        <div style="margin-left: 10px;font-size: 14px;">{{ item.companyAddress }}</div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">关 闭</el-button>
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

export default {
  name: "CompanyInformationView",
  data() {
    return {
      tableData: [],
      companyAddresses: [],
      total: 0,
      formVisible: false,
      companyInfo: JSON.parse(localStorage.getItem('companyInfo') || '{}'),
      params: {
        pageNum: 1,
        pageSize: 8,
        companyName: '',
        legalRepresentative: '',
        foundingTime: '',
        companyCategory: '',
        registeredCapital: '',
        managementStatue: '',
        companyProfile: '',
        registerTime: '',
        companyAddresses: [],
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/company_info/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 8,
        companyName: '',
        legalRepresentative: '',
        foundingTime: '',
        companyCategory: '',
        registeredCapital: ''
      }
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    showCompanyAddress(companyName) {
      this.formVisible = true
      request.get('/company_info/address/' + companyName).then(res => {
        if (res.code === '200') {
          this.companyAddresses = res.data
        }
      })
    }
  }
}
</script>

<style scoped>

</style>