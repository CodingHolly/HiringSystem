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

      <el-cascader
          clearable
          placeholder="请选择公司行业"
          style="width: 240px; margin-left: 10px"
          size="small"
          v-model="params.companyIndustry"
          :props="{ checkStrictly: true }"
          :options="industries"
          @change="handleChangeIndustry"></el-cascader>
      <el-button style="margin-left: 15px" plain type="primary" icon="el-icon-search" size="small" @click="load">搜索
      </el-button>
      <el-button style="margin-left: 15px" size="small" @click="reset">重置</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "CompanyView.vue",
  data() {
    return {
      params: {
        pageNum: 1,
        pageSize: 8,
        companyName: '',
        companyAddress: '',
        companyIndustry: [],
        managementStatus: ''
      },
      //经营状态选择
      statuses: [{
        id: '1',
        label: '在营'
      }, {
        id: '0',
        label: '停业'
      }],
      //公司行业级联选择
      industries: [{
        value: '互联网/AI',
        label: '互联网/AI',
        children: [{
          value: '互联网',
          label: '互联网'
        }, {
          value: 'sketch',
          label: '电子商务'
        }, {
          value: 'jiaohu',
          label: '游戏'
        }, {
          value: 'jiaohu',
          label: '在线教育'
        }, {
          value: 'jiaohu',
          label: '人工智能'
        }, {
          value: 'jiaohu',
          label: '云计算'
        }, {
          value: 'jiaohu',
          label: '大数据'
        }, {
          value: 'jiaohu',
          label: '信息安全'
        }]
      }, {
        value: '2',
        label: '电子通信/半导体',
        children: [{
          value: 'axure',
          label: '半导体/芯片'
        }, {
          value: 'sketch',
          label: '电子/硬件开发'
        }, {
          value: 'jiaohu',
          label: '计算机硬件'
        }]
      }, {
        value: '3',
        label: '服务业',
        children: [{
          value: 'axure',
          label: '餐饮'
        }, {
          value: 'sketch',
          label: '美容/美发'
        }, {
          value: 'jiaohu',
          label: '家政服务'
        }, {
          value: '.',
          label: '酒店/民宿'
        }, {
          value: '',
          label: '运动/健身'
        },]
      }, {
        value: '4',
        label: '消费品/批发/零售',
        children: [{
          value: 'axure',
          label: '食品/饮料/酒水'
        }, {
          value: 'sketch',
          label: '服装/纺织'
        }, {
          value: 'jiaohu',
          label: '家具/家居'
        }, {
          value: '.',
          label: '珠宝/首饰'
        }, {
          value: '',
          label: '家用电器'
        },]
      },],
    }
  },
  methods: {
    handleChangeStatus() {
      console.log(this.params.managementStatus)
    },
    handleChangeIndustry() {
      console.log(this.params.companyIndustry)
    },
    load() {

    },
    reset() {

    }
  }
}
</script>

<style scoped>

</style>