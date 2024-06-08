<template>
  <div class="main-content">

  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "PositionDetailsView",
  data() {
    let positionId = this.$route.query.id
    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      positionId: positionId,
      positionData: {}
    }
  },
  mounted() {
    this.loadPosition()
  },
  methods: {
    loadPosition() {
      request.get('/position_info/selectById?id=' + this.positionId).then(res => {
        if (res.code === '200') {
          this.positionData = res.data
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