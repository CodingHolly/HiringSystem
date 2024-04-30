<template>
  <div>
    <el-card style="margin: 50px 100px 0 100px">
      <div slot="header" style="font-size: 23px">
        <span>企业信息</span>
      </div>
      <div>
        <el-form ref="form" :model="companyInfo" :inline="true">
          <el-form-item label="企业名称" prop="companyName" style="margin-left: 20px">
            <el-input v-model="companyInfo.companyName"></el-input>
          </el-form-item>
          <el-form-item label="法定代表人" prop="legalRepresentative" style="margin-left: 20px">
            <el-input v-model="companyInfo.legalRepresentative"></el-input>
          </el-form-item>
          <el-form-item label="成立时间" prop="foundingTime" style="margin-left: 20px">
            <el-date-picker
                v-model="companyInfo.foundingTime"
                type="date"
                placeholder="选择日期"
                style="width: 210px"
                value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="企业类别" prop="companyCategory" style="margin-left: 20px">
            <el-select v-model="companyInfo.companyCategory" style="width: 200px">
              <el-option
                  v-for="category in categories"
                  :key="category.value"
                  :label="category.label"
                  :value="category.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="经营状态" prop="managementStatus" style="margin-left: 30px">
            <el-select v-model="companyInfo.managementStatus" style="width: 100px">
              <el-option
                  v-for="status in status"
                  :key="status.value"
                  :label="status.label"
                  :value="status.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="注册资本" prop="registeredCapital" style="margin-left: 30px">
            <el-input v-model="companyInfo.registeredCapital" style="width: 233px"></el-input>
          </el-form-item>
          <el-form-item label="公司简介" prop="companyProfile" style="margin-left: 20px">
            <el-input v-model="companyInfo.companyProfile" type="textarea" :rows="3" style="width: 830px"></el-input>
          </el-form-item>
          <el-form-item label="注册地点" prop="registerAddress" style="margin-left: 20px">
            <el-input v-model="companyInfo.registerAddress" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="人数规模" prop="companyProfile" style="margin-left: 20px">
            <el-input v-model="companyInfo.companyScale" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="公司logo" prop="logo" style="margin-left: 20px">
            <div>
              <el-image v-if="companyInfo.logo" :src="companyInfo.logo" style="width: 70px; height: 70px"></el-image>
              <el-upload
                  action="http://localhost:9090/api/file/upload"
                  :headers="{token: user.token}"
                  :file-list="fileList"
                  list-type="picture"
                  :on-success="(res,file,fileList) => handleLogoUpload(res,file,fileList)">
                <el-button size="small" type="primary" plain>上传Logo</el-button>
              </el-upload>
            </div>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center">
          <el-button type="primary" plain @click="save">保 存</el-button>
        </div>
      </div>
    </el-card>

    <el-card style="margin: 20px 100px 40px 100px">
      <div slot="header" style="font-size: 23px">
        <span>地址信息</span>
        <el-button
            plain
            class="add-button"
            style="margin-left: 10px"
            type="primary"
            size="small"
            @click="handleAddAddress">
          新增
        </el-button>
      </div>
      <div>
        <el-table
            :data="companyAddresses"
            :cell-style="{'text-align':'center'}"
            :header-cell-style="{'text-align':'center'}"
            style="width:80%; margin: auto">
          <el-table-column label="地址" prop="companyAddress"></el-table-column>
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

    </el-card>

    <!--    弹框-->
    <el-dialog title="公司地址" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" :model="form">
        <el-form-item prop="companyAddress" label="地址">
          <el-input v-model="form.companyAddress" style="width: 400px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAddress">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "CompanyInformationView",
  data() {
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      companyInfo: {},
      companyAddresses: [{
        id: '',
        companyName: '',
        companyAddress: ''
      }],
      form: {
        id: '',
        companyName: '',
        companyAddress: ''
      },
      formVisible: false,
      status: [{
        value: '存续',
        label: '存续',
      }, {
        value: '在业',
        label: '在业',
      }, {
        value: '吊销',
        label: '吊销'
      }, {
        value: '注销',
        label: '注销'
      }],
      categories: [{
        value: '电子商务',
        label: '电子商务',
      }, {
        value: '游戏',
        label: '游戏',
      }, {
        value: '社交网络与媒体',
        label: '社交网络与媒体'
      }, {
        value: '广告营销',
        label: '广告营销'
      }, {
        value: '大数据',
        label: '大数据'
      }, {
        value: '医疗健康',
        label: '医疗健康'
      }, {
        value: '生活服务',
        label: '生活服务'
      }, {
        value: 'O2O',
        label: 'O2O'
      }, {
        value: '旅游',
        label: '旅游'
      }, {
        value: '分类信息',
        label: '分类信息'
      }, {
        value: '音乐/视频/阅读',
        label: '音乐/视频/阅读'
      }, {
        value: '在线教育',
        label: '在线教育'
      }, {
        value: '社交网络',
        label: '社交网络'
      }, {
        value: '人力资源服务',
        label: '人力资源服务'
      }, {
        value: '企业服务',
        label: '企业服务'
      }, {
        value: '信息安全',
        label: '信息安全'
      }, {
        value: '智能硬件',
        label: '智能硬件'
      }, {
        value: '移动互联网',
        label: '移动互联网'
      }, {
        value: '互联网',
        label: '互联网'
      }, {
        value: '计算机软件',
        label: '计算机软件'
      }, {
        value: '通信/网络设备',
        label: '通信/网络设备'
      }, {
        value: '广告/公关/会展',
        label: '广告/公关/会展'
      }, {
        value: '互联网金融',
        label: '互联网金融'
      }, {
        value: '物流/仓储',
        label: '物流/仓储'
      }, {
        value: '进出口贸易',
        label: '进出口贸易'
      }, {
        value: '咨询',
        label: '咨询'
      }, {
        value: '工程施工',
        label: '工程施工'
      }, {
        value: '汽车研发/制造',
        label: '汽车研发/制造'
      }, {
        value: '其他行业',
        label: '其他行业'
      }],
      fileList: [],

    }
  },
  created() {
    request.get('/company_admin/' + this.user.id).then(res => {
      this.user = res.data
    })
    this.load()
    console.log(this.companyAddresses)
  },
  methods: {
    load() {
      request.get('/company_info/' + this.user.companyName).then(res => {
        this.companyInfo = res.data
      })
      request.get('/company_info/address/' + this.user.companyName).then(res => {
        this.companyAddresses = res.data
      })
    },
    handleLogoUpload(response, file, fileList) {
      this.companyInfo.logo = response.data
      this.fileList = fileList
    },
    save() {
      request.post('/company_info/save', this.companyInfo).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAddAddress() {
      this.form = {}
      this.formVisible = true
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
    },
    del(id) {
      // eslint-disable-next-line no-unused-vars
      this.$confirm('您确定要删除吗？', '确认删除', {type: "warning"}).then(() => {
        this.request.delete('/company_info/delete_address/' + id).then(res => {
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
    saveAddress() {
      this.form.companyName = this.companyInfo.companyName
      request.post('/company_info/save_address', this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.load()
          this.formVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>