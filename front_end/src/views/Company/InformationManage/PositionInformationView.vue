<template>
  <div>

  </div>
</template>

<script>
import E from 'wangeditor'

let editor

// eslint-disable-next-line no-unused-vars
function initWangEditor(content) {
  setTimeout(() => {
    if (!editor) {
      editor = new E('#editor')
      editor.config.placeholder = '请输入内容'
      editor.config.uploadFileName = 'file'
      editor.config.uploadImgServer = 'http://localhost:9090/api/file/wang/upload'
      editor.create()
    }
    editor.txt.html(content)
  }, 0)
}

export default {
  name: "PositionInformationView"
}
</script>

<style scoped>

</style>