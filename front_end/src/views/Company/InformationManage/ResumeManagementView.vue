<template>
  <div style="padding: 20px 10px">
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
      <el-button style="margin-left: 15px" plain type="primary" size="small" @click="load">
        <i class="el-icon-search"></i>搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
                :default-sort="{prop: 'date', order:'descending'}"
                ref="multipleTable"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>
        <el-table-column label="序号" prop="id" width=60></el-table-column>
        <el-table-column label="投递人" prop="username"></el-table-column>
        <el-table-column label="联系方式" prop="userPhone"></el-table-column>
        <el-table-column label="职位名称" prop="positionName"></el-table-column>
        <el-table-column label="投递时间" prop="submitTime"></el-table-column>
        <el-table-column label="简历信息">
          <template slot-scope="scope">
            <el-button plain size="small" @click="showResume(scope.row.userPhone)">详情</el-button>
          </template>
        </el-table-column>
        <el-table-column label="回复信息">
          <template slot-scope="scope">
            <el-button plain size="small" @click="showComment(scope.row.id)">详情</el-button>
          </template>
        </el-table-column>
        <el-table-column label="回复时间" prop="commentTime"></el-table-column>
      </el-table>
    </div>

    <!--    简历地址弹窗-->
    <el-dialog title="个人简历" :visible.sync="resumeFormVisible" width="60%" :close-on-click-modal="false"
               destroy-on-close>
      <div>
        <el-row :gutter="0" class="person-info" style="display: flex; margin-bottom: 30px">
          <el-col :sm="18" :xs="24">
            <el-descriptions :column="2" border v-model="submitPersonInfo" title="基本信息">
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  姓名
                </template>
                {{ submitPersonInfo.username }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone"></i>
                  手机号
                </template>
                {{ submitPersonInfo.phone }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-search"></i>
                  性别
                </template>
                {{ submitPersonInfo.sex }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-tickets"></i>
                  邮箱
                </template>
                {{ submitPersonInfo.email }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-suitcase"></i>
                  求职状态
                </template>
                {{ submitPersonInfo.jobSearchStatus }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-help"></i>
                  出生年月
                </template>
                {{ submitPersonInfo.birthday }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-postcard"></i>
                  身份
                </template>
                {{ submitPersonInfo.identity }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-postcard"></i>
                  出生地
                </template>
                {{ submitPersonInfo.birthplace }}
              </el-descriptions-item>
            </el-descriptions>
          </el-col>
          <el-col :sm="6" :xs="24" class="person-info-img" style="align-items: stretch">
            <div class="imgBox"
                 style="border: 1px solid #ebeef5; border-left: none; width: 100%; height: 100%;position: relative">
              <img class="my-img" fit="fill" :src=submitPersonInfo.img alt=""
                   style="position: absolute;width: 70%; height: 98%">
            </div>
          </el-col>
        </el-row>

        <el-descriptions title="其他信息" :column="1" border v-model="userResume">
          <el-descriptions-item>
            <template slot="label">期望职位</template>
            {{ userResume.expectedPosition }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">个人优势</template>
            {{ userResume.personalAdvantage }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">教育经历</template>
            {{ userResume.educationExperience }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">项目经历</template>
            {{ userResume.projectExperience }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">工作经历</template>
            {{ userResume.workExperience }}
          </el-descriptions-item>
        </el-descriptions>

      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resumeFormVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="回复信息" :visible.sync="commentFormVisible" width="40%" :close-on-click-modal="false">
      <el-input v-model="comment" style="width: 400px; margin-left: 70px"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveComment" size="medium">提 交</el-button>
        <el-button @click="commentFormVisible = false" size="medium">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "ResumeManagement",
  data() {
    return {
      tableData: [],
      param: {},
      total: 0,
      //根据Cookie值中的信息，判断是否具有编辑当前企业管理员信息的权限
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      params: {},
      categories: [],
      types: [],
      userResume: {},
      resumeFormVisible: false,
      resumeForm: {},
      submitPersonInfo: {},
      commentFormVisible: false,
      comment: '',
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
    this.load()
  },
  methods: {
    load() {
      this.params.companyName = this.user.companyName
      request.get('/submit_resume/company_page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
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
    showResume(userPhone) {
      request.get('/resume/' + userPhone).then(res => {
        if (res.code === '200') {
          this.userResume = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
      request.get('/user/phone/' + userPhone).then(res => {
        if (res.code === '200') {
          this.submitPersonInfo = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
      this.resumeFormVisible = true
    },
    showComment(id) {
      request.get('/submit_resume/user_resume/' + id).then(res => {
        if (res.code === '200') {
          this.userResume = res.data
          this.comment = this.userResume.comment
        } else {
          this.$message.error(res.msg)
        }
      })
      this.commentFormVisible = true
    },
    saveComment() {
      this.userResume.comment = this.comment
      request.post('/submit_resume/save_comment', this.userResume).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功！')
          this.commentFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  },

}
</script>

<style scoped>
.el-pagination {
  text-align: right;
}

.dialog-footer {
  margin-right: 10px;
}
</style>